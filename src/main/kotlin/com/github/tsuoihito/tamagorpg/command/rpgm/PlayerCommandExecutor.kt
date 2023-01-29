package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.message.*
import com.github.tsuoihito.tamagorpg.player.*
import com.github.tsuoihito.tamagorpg.role.Role
import com.github.tsuoihito.tamagorpg.util.getOverworldSpawnLocation
import org.bukkit.command.CommandSender

fun executePlayerCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.isEmpty()) return false
    val newArgs = args.drop(1)
    val cmd = PlayerCommand.valueOfCommandName(args[0]) ?: return false
    return when (cmd) {
        PlayerCommand.CREATE -> executeCreateCommand(plugin, sender, newArgs)
        PlayerCommand.SET_ROLE -> executeSetRoleCommand(plugin, sender, newArgs)
        PlayerCommand.ADD_COMPLETED_DUNGEON_NAME -> executeAddDungeonCommand(plugin, sender, newArgs)
        PlayerCommand.REMOVE_COMPLETED_DUNGEON_NAME -> executeRemoveDungeonCommand(plugin, sender, newArgs)
        PlayerCommand.SET_LEVEL -> executeSetLevelCommand(plugin, sender, newArgs)
        PlayerCommand.INFO -> executeInfoCommand(plugin, sender, newArgs)
        else -> false
    }
}

private fun executeCreateCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val player = plugin.server.getPlayer(args[0]) ?: return true
    addRPGPlayer(
        plugin,
        player.uniqueId,
        RPGPlayer(player.name, Role.CITIZEN, emptyList(), 0, getOverworldSpawnLocation(plugin))
    )
    sender.sendMessage(getPlayerCreateMessage(args[0]))
    return true
}

private fun executeSetRoleCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 2) return false
    val playerName = args[0]
    val roleString = args[1]
    val player = plugin.server.getPlayer(playerName) ?: return true
    val role = Role.valueOfOrNull(roleString) ?: return true
    setRPGPlayerRole(plugin, player.uniqueId, role)
    sender.sendMessage(getPlayerSetRoleMessage(roleString, playerName))
    return true
}

private fun executeAddDungeonCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 2) return false
    val player = args[0]
    val dungeon = args[1]
    plugin.server.getPlayer(player)?.let { addRPGPlayerCompletedDungeonName(plugin, it.uniqueId, dungeon) }
    sender.sendMessage(getPlayerAddDungeonMessage(dungeon, player))
    return true
}

private fun executeRemoveDungeonCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 2) return false
    val player = args[0]
    val dungeon = args[1]
    plugin.server.getPlayer(player)?.let { removeRPGPlayerCompletedDungeonName(plugin, it.uniqueId, dungeon) }
    sender.sendMessage(getPlayerRemoveDungeonMessage(dungeon, player))
    return true
}

private fun executeSetLevelCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 2) return false
    val playerName = args[0]
    val level = args[1].toIntOrNull() ?: return false
    val player = plugin.server.getPlayer(playerName) ?: return true
    setRPGPlayerLevel(plugin, player.uniqueId, level)
    sender.sendMessage(getPlayerSetLevelMessage(level, playerName))
    return true
}

private fun executeInfoCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.size != 1) return false
    val player = plugin.server.getPlayer(args[0]) ?: return true
    plugin.rpgPlayers[player.uniqueId]?.let {
        sender.sendMessage(getPlayerInfoMessage(it))
    }
    return true
}
