package com.github.tsuoihito.tamagorpg.util

import com.github.tsuoihito.tamagorpg.TamagoRPG
import org.bukkit.Location
import org.bukkit.World
import java.io.File

private const val OVERWORLD_NAME = "world"

fun resetWorld(plugin: TamagoRPG, worldName: String) {
    plugin.multiverseCore.mvWorldManager.unloadWorld(worldName, true)

    val originalWorldFolder = getOriginalWorldFolder(plugin, worldName)
    val usedWorldFolder = getUsedWorldFolder(plugin, worldName)
    val backupWorldFolder = getBackupWorldFolder(plugin, worldName)
    if (originalWorldFolder.exists()) {
        usedWorldFolder.copyRecursively(backupWorldFolder, overwrite = true)
        originalWorldFolder.copyRecursively(usedWorldFolder, overwrite = true)
    }

    if (!plugin.multiverseCore.mvWorldManager.loadWorld(worldName)) {
        backupWorldFolder.copyRecursively(usedWorldFolder, overwrite = true)
        if (plugin.multiverseCore.mvWorldManager.loadWorld(worldName)) {
            backupWorldFolder.deleteRecursively()
        }
        return
    }
    backupWorldFolder.deleteRecursively()
}

fun saveOriginalWorld(plugin: TamagoRPG, worldName: String) {
    if (getUsedWorldFolder(plugin, worldName).exists()) {
        plugin.server.getWorld(worldName)?.save()
        getWorldsFolder(plugin).mkdir()
        getUsedWorldFolder(plugin, worldName).copyRecursively(
            getOriginalWorldFolder(plugin, worldName),
            overwrite = true
        )
    }
}

private fun getUsedWorldFolder(plugin: TamagoRPG, worldName: String): File {
    return File(plugin.server.worldContainer, worldName)
}

private fun getOriginalWorldFolder(plugin: TamagoRPG, worldName: String): File {
    return File(getWorldsFolder(plugin), worldName)
}

private fun getWorldsFolder(plugin: TamagoRPG): File {
    return File(plugin.dataFolder, "worlds")
}

private fun getBackupWorldFolder(plugin: TamagoRPG, worldName: String): File {
    return File(plugin.server.worldContainer, "${worldName}.backup")
}

fun getWorldNameList(plugin: TamagoRPG): List<String> {
    return plugin.multiverseCore.mvWorldManager.mvWorlds.map { it.name }
}

fun getOverworldSpawnLocation(plugin: TamagoRPG): Location {
    return plugin.multiverseCore.mvWorldManager.getMVWorld(OVERWORLD_NAME).spawnLocation
}

fun isOverworld(world: World?): Boolean {
    return world?.name == OVERWORLD_NAME
}
