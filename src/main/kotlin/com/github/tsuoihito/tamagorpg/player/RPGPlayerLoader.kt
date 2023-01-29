package com.github.tsuoihito.tamagorpg.player

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.role.Role
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.*

fun loadALlPlayers(plugin: TamagoRPG) {
    plugin.server.onlinePlayers.forEach { loadPlayer(plugin, it.uniqueId) }
}

fun loadPlayer(plugin: TamagoRPG, uuid: UUID) {
    loadPlayer(plugin.dataFolder, uuid)?.let { addRPGPlayer(plugin, uuid, it) }
}

fun loadPlayer(pluginFolder: File, uuid: UUID): RPGPlayer? {
    val playerFile = getPlayerFile(getPlayersFolder(pluginFolder), uuid)
    return convertToPlayer(YamlConfiguration.loadConfiguration(playerFile))
}

private fun convertToPlayer(config: YamlConfiguration): RPGPlayer? {
    val name = config.getString("name") ?: return null
    val roleString = config.getString("role") ?: return null
    val completedDungeonNames = config.getStringList("completedDungeonNames")
    val level = config.getInt("level")
    val spawnLocation = config.getLocation("spawnLocation") ?: return null
    return RPGPlayer(
        name, Role.valueOf(roleString), completedDungeonNames, level, spawnLocation
    )
}

fun saveAllPlayer(plugin: TamagoRPG) {
    plugin.server.onlinePlayers.forEach { player ->
        plugin.rpgPlayers[player.uniqueId]?.let {
            savePlayer(plugin, player.uniqueId, it)
        }
    }
}

fun savePlayer(plugin: TamagoRPG, uuid: UUID) {
    plugin.rpgPlayers[uuid]?.let {
        savePlayer(plugin, uuid, it)
    }
}

fun savePlayer(plugin: TamagoRPG, uuid: UUID, RPGPlayer: RPGPlayer) {
    try {
        plugin.dataFolder.mkdir()
        val playersFolder = getPlayersFolder(plugin.dataFolder)
        playersFolder.mkdir()
        val playerFile = getPlayerFile(playersFolder, uuid)
        playerFile.createNewFile()

        convertToYaml(RPGPlayer).save(playerFile)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun convertToYaml(RPGPlayer: RPGPlayer): YamlConfiguration {
    return YamlConfiguration().apply {
        set("name", RPGPlayer.name)
        set("role", RPGPlayer.role.toString())
        set("completedDungeonNames", RPGPlayer.completedDungeonNames)
        set("level", RPGPlayer.level)
        set("spawnLocation", RPGPlayer.spawnLocation)
    }
}

private fun getPlayerFile(playersFolder: File, uuid: UUID): File {
    return File(playersFolder, "${uuid}.yml")
}

private fun getPlayersFolder(pluginFolder: File): File {
    return File(pluginFolder, "players")
}
