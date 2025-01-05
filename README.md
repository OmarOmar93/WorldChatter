![Logo](https://cdn.modrinth.com/data/cached_images/db19283d184a807a4677411d14457fb5bec24cab.png)

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/O95rzhgVSZI" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

# Security Features

### Anti-Swear
This helps to prevent any sensitive or inappropriate words from your chat to help keep it clean.  
It has the ability to customize words (BlackList / WhiteList).

### Anti-Repeat
This prevents players from repeating the same words or letters over and over again.

### Anti-ADs
It will block any type of IPs or URLs from the player.

### Anti-Caps
This helps to prevent the player from sending large uppercased messages in the chat!

# Configurations!

### Detection Messages!
If anyone triggers one of the security settings, you will be alerted, and they will be alerted as well.

```yaml
DetectedMessage: "&c%player_name%'s message got detected by the following flags &e[%flags%] \n----------> &6%message%" # Change how the detection message is sent
DetectedPlayerMessage: "&cYou have been detected by the following flags &e[%flags%]" # Changes how the message is sent to the player (set it to "" if you don't want to send it)
```

### Switch Messages (For Proxies)
This will notify everyone in the server or the previous and current server when a player switches from one server to another!

```yaml
# This switch message is only available for both BungeeCord & Velocity
SwitchSettings:
  enabled: true
  global: true # false is just the current server & previous server / true all the servers
  premessage: "&a%player_name% &ehas switched from &6%previous_server% &eto &f%current_server%"
  comessage: "&a%player_name% &ehas arrived from &6%previous_server%"
```

### Others

```yaml
SpamMessage: "&cYour message is blocked by the &eAnti-Spam\n&7Remaining time: %duration% seconds" # Changes how the anti-spam message is sent (set it to "" if you don't want to send it)
ChatClearMessage: "&eThe Chat has been cleared by &a%sender%" # Changes how the cleared chat message gets sent
NoPermissionMessage: "&eYou're not allowed to use this command!"
```

# Utilities Features!

### Aliases
This feature enhances WorldChatter's placeholders by allowing customization of player and place names using two new placeholders: `{player_name}` and `{player_place}`.

```yaml
aliases:
  enabled: true
  place:
    world: "&aOverworld&r"
  player:
    OmarOmar93: "<gradient>OmarOmar93</gradient> &o&eTest Omar Yes"
```

### Chat Locking
This allows staff or console to lock or unlock the chat using `wc lock`!

```yaml
ChatLockMessage:
  enabled: true
  public: true # TRUE | Shows the message to everyone or FALSE | just the sender
  locked: "&eThe Chat is now &cLOCKED &eby &a%sender%"
  unlocked: "&eThe Chat is now &aUNLOCKED &eby &a%sender%"
  currently: "&eThe Chat is Currently &clocked"
```

### Clear Chat!
Clear the chat with `wc clear`!

### Custom Join and Quit Messages!
Customize join and quit messages with special permissions for certain players.

```yaml
Join:
  enabled: true
  level: 1 # 1 is just current place / 2 the whole server
  place: "world" # place to send the message
  message: "&a%player_name% &fhas joined the game!"
  permmode: true # Checks to use the permissions mode to send messages / disabled will use default message
  permissions:
    "admin":
      permissions: [ "worldchatter.control", "*" ]
      message: "&a%player_name% &fhas joined the game\n<click:run_command:/tp %player_name%><hover:show_text:'&aClick to teleport to %player_name%'>&e[Teleport to %player_name%]</click>"

Quit:
  enabled: true
  level: 1 # 1 is just current place / 2 the whole server
  place: "world" # place to send the message - type %place% to make it the last player's place
  message: "&a%player_name% &fhas left the game!"
  permmode: false # Checks to use the permissions below to show a different message or no!
  permissions:
    "admin":
      permissions: [ "worldchatter.control", "*" ]
      message: "&a%player_name% &fhas left the game &8You have nothing to do to that player :P"
```

### Notifications
Customize notification sounds for detections.

```yaml
notification:
  enabled: true
  staff: # notification detection for staff
    sound: "BLOCK_NOTE_BLOCK_PLING" # the sound to play as the notification you can find it here by https://helpch.at/docs/SERVER_VERSION_GOES_HERE/index.html?org/bukkit/Sound.html
    volume: 1 # volume of the sound
    pitch: 1 # pitch of the sound
  player: # notification detection to the player
    sound: "BLOCK_NOTE_BLOCK_PLING"
    volume: 1
    pitch: 1
```

![WorldChatterAPI](https://i.ibb.co/PzZBQqM/image.png)

# WorldChatterAPI
Learn more about the API and its capabilities by [Clicking here!](https://github.com/OmarOmar93/WorldChatterAddonChecker)

# Donate!
If you'd like to support the development of WorldChatter and other projects, consider donating!  
[Buy me a Coffee!](https://www.paypal.com/paypalme/AbdelazizHasaneen)  
Don't get confused; yes, it is **@AbdelazizHasaneen**.

# Other Projects!
[WorldCaster](https://modrinth.com/plugin/worldcaster)  
Standalone Broadcast Add-on  

# Supported Plugins!
- PlaceholderAPI  
- Multiverse-Core  
- ItemsAdder (With PlaceholderAPI)  
- Luckperms  
- And other plug-ins that have PlaceholderAPI's placeholder!  

# Massive thank you to:
- **Abdelaziz189356** - For the help with 2.0, 2.1, and 3.0 being possible, 3.0 Tester!  
- **yousife_7aloly** - The tester for 2.0!  
- **FastEverlast** - The Tester for 2.4!  
- **Fabrizio Santana, 4zy** - 3.0 Testers  

There are lots of additional features to explore!  
Thank you for taking the time to read this and use the plugin!
