package com.github.tsuoihito.tamagorpg.player

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.role.Role
import org.bukkit.Location
import java.util.*

private fun changeRPGPlayer(plugin: TamagoRPG, uuid: UUID, f: (RPGPlayer) -> RPGPlayer) {
    plugin.rpgPlayers[uuid]?.let {
        plugin.rpgPlayers = plugin.rpgPlayers.plus(Pair(uuid, f(it)))
    }
}

fun setRPGPlayerName(plugin: TamagoRPG, uuid: UUID, name: String) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            name, it.role, it.completedDungeonNames, it.level, it.spawnLocation
        )
    }
}

fun addRPGPlayerCompletedDungeonName(plugin: TamagoRPG, uuid: UUID, dungeon: String) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            it.name, it.role, it.completedDungeonNames.minus(dungeon).plus(dungeon), it.level, it.spawnLocation
        )
    }
}

fun removeRPGPlayerCompletedDungeonName(plugin: TamagoRPG, uuid: UUID, dungeon: String) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            it.name, it.role, it.completedDungeonNames.minus(dungeon), it.level, it.spawnLocation
        )
    }
}

fun setRPGPlayerRole(plugin: TamagoRPG, uuid: UUID, role: Role) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            it.name, role, it.completedDungeonNames, it.level, it.spawnLocation
        )
    }
}

fun setRPGPlayerLevel(plugin: TamagoRPG, uuid: UUID, level: Int) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            it.name, it.role, it.completedDungeonNames, level, it.spawnLocation
        )
    }
}

fun setRPGPlayerSpawnLocation(plugin: TamagoRPG, uuid: UUID, location: Location) {
    changeRPGPlayer(plugin, uuid) {
        RPGPlayer(
            it.name, it.role, it.completedDungeonNames, it.level, location
        )
    }
}

fun getRPGPlayerNames(plugin: TamagoRPG): List<String> {
    return plugin.rpgPlayers.values.map { it.name }
}

fun addRPGPlayer(plugin: TamagoRPG, uuid: UUID, rpgPlayer: RPGPlayer) {
    plugin.rpgPlayers = plugin.rpgPlayers.plus(Pair(uuid, rpgPlayer))
}

fun removeRPGPlayer(plugin: TamagoRPG, uuid: UUID) {
    plugin.rpgPlayers = plugin.rpgPlayers.minus(uuid)
}

fun getCompletedDungeonNames(plugin: TamagoRPG, uuid: UUID): List<String> {
    return plugin.rpgPlayers[uuid]?.completedDungeonNames ?: return emptyList()
}

fun getCompletedDungeonNames(plugin: TamagoRPG, playerName: String): List<String> {
    val uuid = plugin.server.getPlayer(playerName)?.uniqueId ?: return emptyList()
    return getCompletedDungeonNames(plugin, uuid)
}

fun getUncompletedDungeonNames(plugin: TamagoRPG, uuid: UUID): List<String> {
    val rpgPlayer = plugin.rpgPlayers[uuid] ?: return emptyList()
    return plugin.dungeons.keys.filterNot { rpgPlayer.completedDungeonNames.contains(it) }
}

fun getUncompletedDungeonNames(plugin: TamagoRPG, playerName: String): List<String> {
    val uuid = plugin.server.getPlayer(playerName)?.uniqueId ?: return emptyList()
    return getUncompletedDungeonNames(plugin, uuid)
}
