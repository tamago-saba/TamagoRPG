package com.github.tsuoihito.tamagorpg.dungeon

class Dungeon(
    val displayName: String,
    val requiredDungeonNames: List<String>,
    val maxPlayerNumber: Int,
    val requiredPlayerLevel: Int,
    val locked: Boolean
)
