## DeathRespawn
> "Added cooldown to respawn like Bedwars, Skywars, etc.
I do not know how it could help you, but I hope it adds some special features to your server."
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
respawn:
  enable: false
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
respawn:
  enable: false
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
