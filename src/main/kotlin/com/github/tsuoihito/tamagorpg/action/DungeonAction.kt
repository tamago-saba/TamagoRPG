package com.github.tsuoihito.tamagorpg.action

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.dungeon.Dungeon
import com.github.tsuoihito.tamagorpg.dungeon.setDungeonLocked
import com.github.tsuoihito.tamagorpg.message.*
import com.github.tsuoihito.tamagorpg.player.RPGPlayer
import com.github.tsuoihito.tamagorpg.player.addRPGPlayerCompletedDungeonName
import com.github.tsuoihito.tamagorpg.player.setRPGPlayerSpawnLocation
import com.github.tsuoihito.tamagorpg.scoreboard.clearScoreboard
import com.github.tsuoihito.tamagorpg.util.Task
import com.github.tsuoihito.tamagorpg.util.getOverworldSpawnLocation
import com.github.tsuoihito.tamagorpg.util.resetWorld
import org.bukkit.entity.Player
import java.util.*

fun movePlayerToDungeon(plugin: TamagoRPG, player: Player, dungeonName: String) {
    val gamePlayer = plugin.rpgPlayers[player.uniqueId] ?: return
    val dungeon = plugin.dungeons[dungeonName] ?: return
    if (dungeon.locked) {
        player.sendMessage(getDungeonIsNowLockedMessage())
        return
    }
    if (!isEnoughCompletedDungeon(dungeon, gamePlayer)) {
        player.sendMessage(
            getYouNeedCompleteMoreDungeonsMessage(
                dungeon.requiredDungeonNames.minus(gamePlayer.completedDungeonNames.toSet())
                    .map { plugin.dungeons[it]?.displayName ?: it })
        )
        return
    }
    if (!isEnoughLevel(dungeon, gamePlayer)) {
        player.sendMessage(getYouNeedGetHigherLevelMessage(dungeon.requiredPlayerLevel))
        return
    }
    if (getPlayerNumberInDungeon(plugin, dungeonName) >= dungeon.maxPlayerNumber) {
        player.sendMessage(getDungeonHasMaxPlayersMessage())
        return
    }
    plugin.server.getWorld(dungeonName)?.let { world ->
        clearScoreboard(plugin, player)
        setRPGPlayerSpawnLocation(plugin, player.uniqueId, player.location)
        player.teleport(world.spawnLocation)
    }
}

private fun isEnoughCompletedDungeon(dungeon: Dungeon, RPGPlayer: RPGPlayer): Boolean {
    return RPGPlayer.completedDungeonNames.containsAll(dungeon.requiredDungeonNames)
}

private fun isEnoughLevel(dungeon: Dungeon, RPGPlayer: RPGPlayer): Boolean {
    return RPGPlayer.level >= dungeon.requiredPlayerLevel
}

fun isEnoughStatus(plugin: TamagoRPG, dungeon: Dungeon, uuid: UUID): Boolean {
    val gamePlayer = plugin.rpgPlayers[uuid] ?: return false
    return isEnoughCompletedDungeon(dungeon, gamePlayer) && isEnoughLevel(dungeon, gamePlayer)
}

fun getPlayerNumberInDungeon(plugin: TamagoRPG, dungeonName: String): Int {
    return plugin.server.getWorld(dungeonName)?.players?.size ?: 0
}

fun makeDungeonCompleted(plugin: TamagoRPG, name: String, delay: Long) {
    val dungeon = plugin.dungeons[name] ?: return
    if (dungeon.locked) return
    setDungeonLocked(plugin, name, true)
    getPlayersInDungeon(plugin, name).forEach { player ->
        addRPGPlayerCompletedDungeonName(plugin, player.uniqueId, name)
        player.sendMessage(getDungeonCompleteMessage(dungeon.displayName))
        player.sendMessage(getReturningToOverWorldMessage(delay))
        Task { movePlayerToOverWorld(plugin, player) }.runTaskLater(plugin, delay * 20)
    }
    Task {
        resetWorld(plugin, name)
        setDungeonLocked(plugin, name, false)
    }.runTaskLater(plugin, delay * 20)
}

fun makeDungeonOver(plugin: TamagoRPG, name: String, delay: Long) {
    val dungeon = plugin.dungeons[name] ?: return
    if (dungeon.locked) return
    setDungeonLocked(plugin, name, true)
    getPlayersInDungeon(plugin, name).forEach { player ->
        player.sendMessage(getDungeonOverMessage(dungeon.displayName))
        player.sendMessage(getReturningToOverWorldMessage(delay))
        Task { movePlayerToOverWorld(plugin, player) }.runTaskLater(plugin, delay * 20)
    }
    Task {
        resetWorld(plugin, name)
        setDungeonLocked(plugin, name, false)
    }.runTaskLater(plugin, delay * 20)
}

fun getPlayersInDungeon(plugin: TamagoRPG, dungeonName: String): List<Player> {
    return plugin.server.getWorld(dungeonName)?.players ?: emptyList()
}

fun movePlayerToOverWorld(plugin: TamagoRPG, player: Player) {
    plugin.rpgPlayers[player.uniqueId]?.let {
        player.teleport(it.spawnLocation)
    } ?: player.teleport(getOverworldSpawnLocation(plugin))
}

fun isAlreadyCompleted(plugin: TamagoRPG, dungeonName: String, uuid: UUID): Boolean {
    return plugin.rpgPlayers[uuid]?.completedDungeonNames?.contains(dungeonName) ?: false
}

