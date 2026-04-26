package com.mashood;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class QueuePlugin extends JavaPlugin implements Listener {

    private final Set<UUID> queue = new LinkedHashSet<>();
    private final Set<UUID> inMatch = new HashSet<>();

    private boolean matchStarting = false;

    private static final int MIN_PLAYERS = 2;
    private static final int COUNTDOWN_SECONDS = 5;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {

            if (command.getName().equalsIgnoreCase("queue")
                    && args.length > 0
                    && args[0].equalsIgnoreCase("force")) {
                startMatch();
                return true;
            }

            return true;
        }

        if (!command.getName().equalsIgnoreCase("queue")) return false;

        if (args.length == 0) return true;

        UUID id = player.getUniqueId();

        switch (args[0].toLowerCase()) {

            case "join" -> join(player, id);
            case "leave" -> leave(player, id);
            case "status" -> status(player, id);
            case "force" -> startMatch();
        }

        return true;
    }

    private void join(Player player, UUID id) {

        if (queue.contains(id) || inMatch.contains(id)) return;

        queue.add(id);

        checkStart();

    }

    private void leave(Player player, UUID id) {
        queue.remove(id);
    }

    private void status(Player player, UUID id) {

        int pos = new ArrayList<>(queue).indexOf(id) + 1;

        player.sendMessage(String.valueOf(queue.size()));
        player.sendMessage(String.valueOf(pos));
    }

    private void checkStart() {
        if (!matchStarting && queue.size() >= MIN_PLAYERS) {
            countdown();
        }
    }

    private void countdown() {

        matchStarting = true;

        new BukkitRunnable() {

            int t = COUNTDOWN_SECONDS;

            @Override
            public void run() {

                if (queue.size() < MIN_PLAYERS) {
                    matchStarting = false;
                    cancel();
                    return;
                }

                if (t <= 0) {
                    startMatch();
                    cancel();
                    return;
                }

                t--;
            }

        }.runTaskTimer(this, 0L, 20L);
    }

    private void startMatch() {

        if (queue.size() < MIN_PLAYERS) {
            matchStarting = false;
            return;
        }

        Location loc = Bukkit.getWorlds().get(0).getSpawnLocation();

        List<UUID> players = new ArrayList<>(queue);

        for (UUID id : players) {
            Player p = Bukkit.getPlayer(id);

            if (p != null && p.isOnline()) {
                p.teleport(loc);
                inMatch.add(id);
            }
        }

        queue.clear();
        matchStarting = false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        queue.remove(id);
        inMatch.remove(id);
    }
}