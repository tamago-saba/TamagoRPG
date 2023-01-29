package com.github.tsuoihito.tamagorpg.placeholder

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.action.getPlayersInDungeon
import com.github.tsuoihito.tamagorpg.action.isAlreadyCompleted
import com.github.tsuoihito.tamagorpg.action.isEnoughStatus
import com.github.tsuoihito.tamagorpg.dungeon.Dungeon
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer

class DungeonExpansion(private val plugin: TamagoRPG) : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "dungeon"
    }

    override fun getAuthor(): String {
        return "tsuoihito"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        val split = params.split("_")
        if (split.size < 2) return params
        val dungeonName = split[0]
        val type = split[1]
        val dungeon = plugin.dungeons[dungeonName] ?: return params
        return when (type) {
            "name" -> dungeon.displayName
            "max" -> dungeon.maxPlayerNumber.toString()
            "count" -> getPlayersInDungeon(plugin, dungeonName).size.toString()
            "level" -> dungeon.requiredPlayerLevel.toString()
            "status" -> if (dungeon.locked) "ロック中" else "解放中"
            "complete" -> getIsCompletedText(player, dungeonName)
            "enough" -> getIsEnoughStatusText(player, dungeon)
            else -> params
        }
    }

    private fun getIsCompletedText(player: OfflinePlayer?, dungeonName: String): String {
        if (player?.let { isAlreadyCompleted(plugin, dungeonName, it.uniqueId) } == true) {
            return "既にクリア済みです"
        }
        return "まだクリアしていません"
    }

    private fun getIsEnoughStatusText(player: OfflinePlayer?, dungeon: Dungeon): String {
        if (player?.let { isEnoughStatus(plugin, dungeon, it.uniqueId) } == true) {
            return "${ChatColor.GREEN}参加要件を満たしています"
        }
        return "${ChatColor.RED}参加要件を満たしていません"
    }
}
