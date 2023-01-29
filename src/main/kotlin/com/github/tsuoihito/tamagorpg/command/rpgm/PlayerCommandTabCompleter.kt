package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.util.*

fun getPlayerCommandCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    return when (args.size) {
        1 -> getFirstCandidate(args[0])
        2 -> getSecondCandidate(plugin, args)
        3 -> getThirdCandidate(plugin, args)
        else -> emptyList()
    }
}

private fun getFirstCandidate(typing: String): List<String> {
    return PlayerCommand.getCommandNameCandidate(typing)
}

private fun getSecondCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    val cmd = PlayerCommand.valueOfCommandName(args[0]) ?: return emptyList()
    return when (cmd) {
        PlayerCommand.CREATE -> getUnregisteredPlayerNameCandidate(plugin, args[1])
        PlayerCommand.SET_ROLE -> getRPGPlayerNameCandidate(plugin, args[1])
        PlayerCommand.ADD_COMPLETED_DUNGEON_NAME -> getRPGPlayerNameCandidate(plugin, args[1])
        PlayerCommand.REMOVE_COMPLETED_DUNGEON_NAME -> getRPGPlayerNameCandidate(plugin, args[1])
        PlayerCommand.INFO -> getRPGPlayerNameCandidate(plugin, args[1])
        PlayerCommand.SET_LEVEL -> getRPGPlayerNameCandidate(plugin, args[1])
        else -> emptyList()
    }
}

private fun getThirdCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    val cmd = PlayerCommand.valueOfCommandName(args[0]) ?: return emptyList()
    return when (cmd) {
        PlayerCommand.SET_ROLE -> getRoleCandidate(args[2])
        PlayerCommand.ADD_COMPLETED_DUNGEON_NAME -> getUncompletedDungeonNameCandidate(plugin, args[1], args[2])
        PlayerCommand.REMOVE_COMPLETED_DUNGEON_NAME -> getCompletedDungeonNameCandidate(plugin, args[1], args[2])
        else -> emptyList()
    }
}
