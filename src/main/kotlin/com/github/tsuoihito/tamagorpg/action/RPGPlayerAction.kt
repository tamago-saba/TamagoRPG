package com.github.tsuoihito.tamagorpg.action

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.message.getCreateProfileMessage
import com.github.tsuoihito.tamagorpg.message.getProfileAlreadyExists
import com.github.tsuoihito.tamagorpg.player.RPGPlayer
import com.github.tsuoihito.tamagorpg.player.addRPGPlayer
import com.github.tsuoihito.tamagorpg.role.Role
import com.github.tsuoihito.tamagorpg.util.getOverworldSpawnLocation
import org.bukkit.entity.Player

fun createProfile(plugin: TamagoRPG, player: Player) {
    if (plugin.rpgPlayers[player.uniqueId] != null) {
        player.sendMessage(getProfileAlreadyExists())
        return
    }
    addRPGPlayer(
        plugin,
        player.uniqueId,
        RPGPlayer(player.name, Role.CITIZEN, emptyList(), 0, getOverworldSpawnLocation(plugin))
    )
    player.sendMessage(getCreateProfileMessage())
    return
}
