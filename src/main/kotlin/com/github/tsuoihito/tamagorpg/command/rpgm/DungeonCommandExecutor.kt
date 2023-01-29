package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.dungeon.*
import com.github.tsuoihito.tamagorpg.message.*
import org.bukkit.command.CommandSender

fun executeDungeonCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.isEmpty()) return false
    val newArgs = args.drop(1)
    val dungeonCommand = DungeonCommand.valueOfCommandName(args[0]) ?: return false
    return when (dungeonCommand) {
        DungeonCommand.CREATE -> executeCreateCommand(plugin, sender, newArgs)
        DungeonCommand.DELETE -> executeDeleteCommand(plugin, sender, newArgs)
        DungeonCommand.LIST -> executeListCommand(plugin, sender)
        DungeonCommand.INFO -> executeInfoCommand(plugin, sender, newArgs)
        DungeonCommand.SET_DISPLAY_NAME -> executeSetDisplayNameCommand(plugin, sender, newArgs)
        DungeonCommand.ADD_REQUIRED_DUNGEON_NAME -> executeAddRequiredDungeonNameCommand(plugin, sender, newArgs)
        DungeonCommand.REMOVE_REQUIRED_DUNGEON_NAME -> executeRemoveRequiredDungeonNameCommand(plugin, sender, newArgs)
        DungeonCommand.SET_MAX_PLAYER_NUMBER -> executeSetMaxPlayerNumberCommand(plugin, sender, newArgs)
        DungeonCommand.SET_REQUIRED_PLAYER_LEVEL -> executeSetPlayerLevel(plugin, sender, newArgs)
        DungeonCommand.LOCK -> executeLockCommand(plugin, sender, newArgs)
        DungeonCommand.UNLOCK -> executeUnlockCommand(plugin, sender, newArgs)
    }
}

private fun executeCreateCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val name = args[0]
    addDungeon(plugin, name, Dungeon(name, emptyList(), 0, 0, true))
    saveDungeons(plugin)
    sender.sendMessage(getDungeonCreateMessage(name))
    return true
}

private fun executeDeleteCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val name = args[0]
    removeDungeon(plugin, name)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonDeleteMessage(name))
    return true
}

private fun executeListCommand(plugin: TamagoRPG, sender: CommandSender): Boolean {
    sender.sendMessage(getDungeonListMessage(plugin.dungeons.keys.toList()))
    return true
}

private fun executeInfoCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    plugin.dungeons[args[0]]?.let { sender.sendMessage(getDungeonInfoMessage(it)) }
    return true
}

private fun executeSetDisplayNameCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 3) return false
    val dungeon = args[0]
    val displayName = args[1]
    setDungeonDisplayName(plugin, dungeon, displayName)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonSetDisplayNameMessage(displayName, dungeon))
    return true
}

private fun executeAddRequiredDungeonNameCommand(
    plugin: TamagoRPG,
    sender: CommandSender,
    args: List<String>
): Boolean {
    if (args.size != 3) return false
    val dungeon = args[0]
    val required = args[1]
    addDungeonRequiredDungeonName(plugin, dungeon, required)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonAddRequiredMessage(required, dungeon))
    return true
}

private fun executeRemoveRequiredDungeonNameCommand(
    plugin: TamagoRPG,
    sender: CommandSender,
    args: List<String>
): Boolean {
    if (args.size != 3) return false
    val dungeon = args[0]
    val required = args[1]
    removeDungeonRequiredDungeonName(plugin, dungeon, required)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonRemoveRequiredMessage(required, dungeon))
    return true
}

private fun executeSetMaxPlayerNumberCommand(
    plugin: TamagoRPG,
    sender: CommandSender,
    args: List<String>
): Boolean {
    if (args.size != 3) return false
    val dungeon = args[0]
    val level = args[1].toIntOrNull() ?: return false
    setDungeonMaxPlayerNumber(plugin, dungeon, level)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonSetMaxMessage(level, dungeon))
    return true
}

private fun executeSetPlayerLevel(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 3) return false
    val dungeon = args[0]
    val level = args[1].toIntOrNull() ?: return false
    setDungeonRequiredPlayerLevel(plugin, dungeon, level)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonSetPlayerLevelMessage(level, dungeon))
    return true
}

private fun executeLockCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val dungeon = args[0]
    setDungeonLocked(plugin, dungeon, true)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonLockMessage(dungeon))
    return true
}

private fun executeUnlockCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val dungeon = args[0]
    setDungeonLocked(plugin, dungeon, false)
    saveDungeons(plugin)
    sender.sendMessage(getDungeonUnlockMessage(dungeon))
    return true
}
