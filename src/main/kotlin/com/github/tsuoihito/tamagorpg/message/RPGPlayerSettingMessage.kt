package com.github.tsuoihito.tamagorpg.message

import com.github.tsuoihito.tamagorpg.player.RPGPlayer
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GREEN

fun getPlayerCreateMessage(player: String): String {
    return prefix + "${AQUA}プレイヤー「${GREEN}${player}${AQUA}」のデータを作成しました"
}

fun getPlayerSetRoleMessage(role: String, player: String): String {
    return prefix + "${AQUA}プレイヤー「${GREEN}${player}${AQUA}」の役職を${GREEN}${role}${AQUA}に設定しました"
}

fun getPlayerAddDungeonMessage(dungeon: String, player: String): String {
    return prefix + "${AQUA}プレイヤー「${GREEN}${player}${AQUA}」のクリアしたダンジョンに${GREEN}${dungeon}${AQUA}を追加しました"
}

fun getPlayerRemoveDungeonMessage(dungeon: String, player: String): String {
    return prefix + "${AQUA}プレイヤー「${GREEN}${player}${AQUA}」のクリアしたダンジョンから${GREEN}${dungeon}${AQUA}を削除しました"
}

fun getPlayerSetLevelMessage(level: Int, player: String): String {
    return prefix + "${AQUA}プレイヤー「${GREEN}${player}${AQUA}」のレベルを${GREEN}${level}${AQUA}に設定しました"
}

fun getPlayerInfoMessage(RPGPlayer: RPGPlayer): String {
    return prefix + "\n${AQUA}name: ${GREEN}${RPGPlayer.name}\n${AQUA}role: ${GREEN}${RPGPlayer.role}\n${AQUA}completedDungeonNames: ${GREEN}${
        RPGPlayer.completedDungeonNames.joinToString(
            "${AQUA},${GREEN} "
        )
    }\n${AQUA}level: ${GREEN}${RPGPlayer.level}"
}
