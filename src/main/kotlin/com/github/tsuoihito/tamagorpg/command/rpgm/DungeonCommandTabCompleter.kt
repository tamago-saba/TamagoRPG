package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.util.getAnotherRequiredDungeonNameCandidate
import com.github.tsuoihito.tamagorpg.util.getCandidate
import com.github.tsuoihito.tamagorpg.util.getDungeonNamesCandidate
import com.github.tsuoihito.tamagorpg.util.getRequiredDungeonNameCandidate

fun getDungeonCommandCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    return when (args.size) {
        1 -> getFirstCandidate(args)
        2 -> getSecondCandidate(plugin, args)
        3 -> getThirdCandidate(plugin, args)
        else -> emptyList()
    }
}

private fun getFirstCandidate(args: List<String>): List<String> {
    return getCandidate(DungeonCommand.getCommandNames(), args[0])
}

private fun getSecondCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    val cmd = DungeonCommand.valueOfCommandName(args[0]) ?: return emptyList()
    val dungeonName = args[1]
    return when (cmd) {
        DungeonCommand.DELETE -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.INFO -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.SET_DISPLAY_NAME -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.ADD_REQUIRED_DUNGEON_NAME -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.REMOVE_REQUIRED_DUNGEON_NAME -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.SET_MAX_PLAYER_NUMBER -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.SET_REQUIRED_PLAYER_LEVEL -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.LOCK -> getDungeonNamesCandidate(plugin, dungeonName)
        DungeonCommand.UNLOCK -> getDungeonNamesCandidate(plugin, dungeonName)
        else -> emptyList()
    }
}

private fun getThirdCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    val cmd = DungeonCommand.valueOfCommandName(args[0]) ?: return emptyList()
    return when (cmd) {
        DungeonCommand.ADD_REQUIRED_DUNGEON_NAME -> getAnotherRequiredDungeonNameCandidate(plugin, args[1], args[2])
        DungeonCommand.REMOVE_REQUIRED_DUNGEON_NAME -> getRequiredDungeonNameCandidate(plugin, args[1], args[2])
        else -> emptyList()
    }
}
