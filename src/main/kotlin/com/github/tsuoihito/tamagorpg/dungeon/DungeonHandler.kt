package com.github.tsuoihito.tamagorpg.dungeon

import com.github.tsuoihito.tamagorpg.TamagoRPG

private fun changeDungeon(plugin: TamagoRPG, name: String, f: (Dungeon) -> Dungeon) {
    plugin.dungeons[name]?.let {
        plugin.dungeons = plugin.dungeons.plus(Pair(name, f(it)))
    }
}

fun setDungeonDisplayName(plugin: TamagoRPG, name: String, displayName: String) {
    changeDungeon(plugin, name) {
        Dungeon(displayName, it.requiredDungeonNames, it.maxPlayerNumber, it.requiredPlayerLevel, it.locked)
    }
}

fun addDungeonRequiredDungeonName(plugin: TamagoRPG, name: String, required: String) {
    changeDungeon(plugin, name) {
        Dungeon(
            it.displayName,
            it.requiredDungeonNames.minus(required).plus(required),
            it.maxPlayerNumber,
            it.requiredPlayerLevel,
            it.locked
        )
    }
}

fun removeDungeonRequiredDungeonName(plugin: TamagoRPG, name: String, required: String) {
    changeDungeon(plugin, name) {
        Dungeon(
            it.displayName,
            it.requiredDungeonNames.minus(required),
            it.maxPlayerNumber,
            it.requiredPlayerLevel,
            it.locked
        )
    }
}

fun setDungeonMaxPlayerNumber(plugin: TamagoRPG, name: String, max: Int) {
    changeDungeon(plugin, name) {
        Dungeon(it.displayName, it.requiredDungeonNames, max, it.requiredPlayerLevel, it.locked)
    }
}

fun setDungeonRequiredPlayerLevel(plugin: TamagoRPG, name: String, level: Int) {
    changeDungeon(plugin, name) {
        Dungeon(it.displayName, it.requiredDungeonNames, it.maxPlayerNumber, level, it.locked)
    }
}

fun setDungeonLocked(plugin: TamagoRPG, name: String, locked: Boolean) {
    changeDungeon(plugin, name) {
        Dungeon(it.displayName, it.requiredDungeonNames, it.maxPlayerNumber, it.requiredPlayerLevel, locked)
    }
}

fun addDungeon(plugin: TamagoRPG, name: String, dungeon: Dungeon) {
    plugin.dungeons = plugin.dungeons.plus(Pair(name, dungeon))
}

fun removeDungeon(plugin: TamagoRPG, name: String) {
    plugin.dungeons = plugin.dungeons.minus(name)
}

fun getRequiredDungeonNames(plugin: TamagoRPG, name: String): List<String> {
    return plugin.dungeons[name]?.requiredDungeonNames ?: emptyList()
}

fun getAnotherRequiredDungeonNames(plugin: TamagoRPG, name: String): List<String> {
    val dungeon = plugin.dungeons[name] ?: return emptyList()
    return plugin.dungeons.keys.filterNot { dungeon.requiredDungeonNames.contains(it) }
}
