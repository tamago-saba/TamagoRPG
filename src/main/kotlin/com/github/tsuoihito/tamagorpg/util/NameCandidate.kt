package com.github.tsuoihito.tamagorpg.util

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.dungeon.getAnotherRequiredDungeonNames
import com.github.tsuoihito.tamagorpg.dungeon.getRequiredDungeonNames
import com.github.tsuoihito.tamagorpg.player.getCompletedDungeonNames
import com.github.tsuoihito.tamagorpg.player.getRPGPlayerNames
import com.github.tsuoihito.tamagorpg.player.getUncompletedDungeonNames
import com.github.tsuoihito.tamagorpg.role.Role

fun getCandidate(list: List<String>, typing: String): List<String> {
    return list.filter { it.startsWith(typing) }
}

fun getOnlinePlayerNameCandidate(plugin: TamagoRPG, typing: String): List<String> {
    return getCandidate(plugin.server.onlinePlayers.map { it.name }, typing)
}

fun getRoleCandidate(typing: String): List<String> {
    return getCandidate(Role.values().map { it.toString().lowercase() }, typing)
}

fun getRPGPlayerNameCandidate(plugin: TamagoRPG, typing: String): List<String> {
    return getCandidate(getRPGPlayerNames(plugin), typing)
}

fun getUnregisteredPlayerNameCandidate(plugin: TamagoRPG, typing: String): List<String> {
    return getCandidate(plugin.server.onlinePlayers.map { it.name }
        .filterNot { name -> getRPGPlayerNames(plugin).contains(name) }, typing)
}

fun getUncompletedDungeonNameCandidate(
    plugin: TamagoRPG,
    playerName: String,
    typing: String
): List<String> {
    return getCandidate(getUncompletedDungeonNames(plugin, playerName), typing)
}

fun getCompletedDungeonNameCandidate(plugin: TamagoRPG, playerName: String, typing: String): List<String> {
    return getCandidate(getCompletedDungeonNames(plugin, playerName), typing)
}

fun getDungeonNamesCandidate(plugin: TamagoRPG, typing: String): List<String> {
    return getCandidate(plugin.dungeons.keys.toList(), typing)
}

fun getAnotherRequiredDungeonNameCandidate(plugin: TamagoRPG, dungeonName: String, typing: String): List<String> {
    return getCandidate(getAnotherRequiredDungeonNames(plugin, dungeonName), typing)
}

fun getRequiredDungeonNameCandidate(plugin: TamagoRPG, dungeonName: String, typing: String): List<String> {
    return getCandidate(getRequiredDungeonNames(plugin, dungeonName), typing)
}
