🎮 QueuePlugin
A lightweight matchmaking queue system for Minecraft servers using the Paper API.

This plugin was developed as Plugin 2 (matchmaking system) for learning server-side player queue handling and basic match flow control.

---

⚙️ Built With
Java 21  
Paper API (Minecraft Server API)  
Maven  
Git & GitHub  

---

🚀 Features

🎯 Queue System  
Players can join a matchmaking queue and wait for a match to start.

⏳ Auto Match Start  
Automatically starts a match when minimum players are reached.

⏱ Countdown System  
Displays a countdown before teleporting players into the match.

👥 Player Management  
Handles join/leave actions and removes players on disconnect.

⚡ Force Start  
Admins can manually start a match instantly.

---

🧪 Commands

| Command | Description |
|--------|-------------|
| `/queue join` | Join the matchmaking queue |
| `/queue leave` | Leave the queue |
| `/queue status` | View queue information |
| `/queue force` | Force start a match |

---
mvn clean install


Copy the jar file from:

target/queueplugin-1.0.jar


Paste it into your Minecraft server:

plugins/


Restart your server.

---

🧠 Notes

- Requires Java 17+
- Compatible with Paper / Spigot servers
- Designed for simple matchmaking systems

---
📦 Installation

Build the plugin using Maven:
