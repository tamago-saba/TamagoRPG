package com.github.tsuoihito.tamagorpg.scoreboard

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.util.isOverworld
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

fun showScoreboard(plugin: TamagoRPG) {
    plugin.server.onlinePlayers.forEach { player ->
        getRPGScoreboard(plugin, player)?.let {
            player.scoreboard = it
        }
    }
}

fun clearScoreboardAll(plugin: TamagoRPG) {
    plugin.server.onlinePlayers.forEach { clearScoreboard(plugin, it) }
}

fun clearScoreboard(plugin: TamagoRPG, player: Player) {
    plugin.server.scoreboardManager?.let {
        player.scoreboard = it.newScoreboard
    }
}

fun getRPGScoreboard(plugin: TamagoRPG, player: Player): Scoreboard? {
    if (!isOverworld(player.location.world)) return null
    val rpgPlayer = plugin.rpgPlayers[player.uniqueId] ?: return null

    val scoreboardManager = plugin.server.scoreboardManager ?: return null
    val scoreboard = scoreboardManager.newScoreboard
    scoreboard.registerNewObjective("TamagoRPG", Criteria.DUMMY, "TamagoRPG").apply {
        displaySlot = DisplaySlot.SIDEBAR
        displayName = "${AQUA}TamagoRPG"
        getScore("${RED}名前: ${GREEN}${rpgPlayer.name}").score = 15
        getScore("${LIGHT_PURPLE}役職: ${GREEN}${rpgPlayer.role.displayName}").score = 14
        getScore("${YELLOW}レベル: ${GREEN}${rpgPlayer.level}").score = 13
    }
    return scoreboard
}
