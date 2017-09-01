## DeathRespawn
> "Added cooldown to respawn like Bedwars, Skywars, etc.
I do not know how it could help you, but I hope it adds some special features to your server."
## Category
1. [Soft-dependencies](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#soft-dependencies)
2. [Commands](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#commands)
3. [Permissions](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#permissions)
4. [Build With](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#build-with)
5. [Authors](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#authors)
6. [Supporting](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#supporting)
7. [Configuration](https://github.com/TrieuCoding/DeathRespawn/blob/master/README.md#configuration)
## Soft-dependencies
- [Vault](https://dev.bukkit.org/projects/vault) - If Economy is not setup, it will disable Vault feature in config and replace to punish.
```
vault:
  enable: true
  amount: 10000.0
punish:
  enable: false
  command: jail {player} j1
```
- [TitleManager](https://www.spigotmc.org/resources/titlemanager.1049/) - If TitleManager is not setup, it will disable Title feature and enable "Messages" feature
```
message:
  enable: false
  countdown: '&fYou will be respawn in &e{seconds} &fseconds'
  respawn: '&aYou have been respawn!'
title:
  enable: true
  title:
    countdown: '&c&lYOU DIED'
    respawn: '&c&lRESPAWN'
  subtitle:
    countdown: '&fYou will be respawn in &e{seconds} &fseconds'
    respawn: '&fYou have been respawn!'
```
## Commands
1. **/dr reload** - *Reload command*
2. **/dr setlocation [name]** - *Set a respawn location*
3. **/dr tplocation [name]** - *Teleport to a respawn location*
4. **/dr removelocation [name]** - *Remove a respawn location*
5. **/dr location [name]** - *Show info of a respawn location*
6. **/dr locations** - *Show valid locations*
- **Aliases:** /dr
## Permissions
- **dr.bypass** - *Permission to cancel the wait respawn process*
- **dr.help** - *Permission to show display help command*
- **dr.setlocation** - *Permission to set respawn location*
- **dr.removelocation** - *Permission to remove respawn location*
- **dr.tplocation** - *Permission to teleport to respawn location*
- **dr.locationinfo** - *Permission to show info of location*
## Build With
- [Maven](https://maven.apache.org/) - Dependency Management
## Authors
- **TrieuCoding** - *Developer*
- **KingCiC** - *Tester*
## Supporting
- Upload your error to [Issues](https://github.com/TrieuCoding/DeathRespawn/issues), I'll check it everyday and fix it!
## Configuration
##### Default config
```
respawn-cooldown: 5
countdown:
  countdown-gamemode: SPECTATOR
  respawn-gamemode: SURVIVAL
  damage: false
  move: false
  chat: true
  use-command: false
  blindness: true
  pickup: false
  vanish: false
  hunger: false
respawn:
  enable: false
  bed: true
  location:
  - type command like "back" or "spawn"
  - you can set the respawn location with command /dr setlocation
vault:
  enable: true
  amount: 10000.0
punish:
  enable: false
  command: jail {player} j1
disable-in-worlds:
- otherworld
sound:
  enable: true
  countdown: BLOCK_NOTE_PLING
  respawn: ENTITY_PLAYER_LEVELUP
particle:
  enable: false
  type: FIREWORKS_SPARK
  sound: ENTITY_FIREWORK_SHOOT
```
##### Example config
```
respawn-cooldown: 10
countdown:
  countdown-gamemode: SPECTATOR
  respawn-gamemode: SURVIVAL
  damage: false
  move: false
  chat: true
  use-command: false
  blindness: true
  pickup: false
  vanish: true
  hunger: false
respawn:
  enable: false
  bed: false
  location:
  - 'dr tplocation test'
vault:
  enable: true
  amount: 30000.0
punish:
  enable: false
  command: jail {player} j1
disable-in-worlds:
- otherworld
sound:
  enable: true
  countdown: BLOCK_NOTE_PLING
  respawn: ENTITY_PLAYER_LEVELUP
particle:
  enable: false
  type: FIREWORKS_SPARK
  sound: ENTITY_FIREWORK_SHOOT
```
