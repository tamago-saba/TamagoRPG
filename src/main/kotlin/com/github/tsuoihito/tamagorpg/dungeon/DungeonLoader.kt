package com.github.tsuoihito.tamagorpg.dungeon

import com.github.tsuoihito.tamagorpg.TamagoRPG
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

fun loadDungeons(plugin: TamagoRPG) {
    YamlConfiguration.loadConfiguration(getDungeonFile(plugin))
        .getConfigurationSection("dungeons")?.let { dungeons ->
            dungeons.getKeys(false).forEach { name ->
                dungeons.getConfigurationSection(name)?.apply {
                    addDungeon(
                        plugin, name, Dungeon(
                            getString("displayName") ?: name,
                            getStringList("requiredDungeonNames"),
                            getInt("maxPlayerNumber"),
                            getInt("requiredPlayerLevel"),
                            getBoolean("locked")
                        )
                    )
                }
            }
        }
}

fun saveDungeons(plugin: TamagoRPG) {
    plugin.dataFolder.mkdir()
    val dungeonsSection = YamlConfiguration()
    plugin.dungeons.forEach { (name, dungeon) ->
        val dungeonSection = YamlConfiguration().apply {
            set("displayName", dungeon.displayName)
            set("requiredDungeonNames", dungeon.requiredDungeonNames)
            set("maxPlayerNumber", dungeon.maxPlayerNumber)
            set("requiredPlayerLevel", dungeon.requiredPlayerLevel)
            set("locked", dungeon.locked)
        }
        dungeonsSection.set(name, dungeonSection)
    }
    YamlConfiguration().apply { set("dungeons", dungeonsSection) }.save(getDungeonFile(plugin))
}

private fun getDungeonFile(plugin: TamagoRPG): File {
    return File(plugin.dataFolder, "dungeons.yml")
}
