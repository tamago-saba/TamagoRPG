package com.github.tsuoihito.tamagorpg.player

import com.github.tsuoihito.tamagorpg.role.Role
import org.bukkit.Location

class RPGPlayer(
    val name: String,
    val role: Role,
    val completedDungeonNames: List<String>,
    val level: Int,
    val spawnLocation: Location
)
