package com.github.tsuoihito.tamagorpg.message

import com.github.tsuoihito.tamagorpg.dungeon.Dungeon
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GREEN

fun getDungeonListMessage(strings: List<String>): String {
    return prefix + "${AQUA}ダンジョン一覧:${GREEN} " + strings.joinToString("${AQUA},${GREEN} ")
}

fun getDungeonCreateMessage(name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」を作成しました"
}

fun getDungeonDeleteMessage(name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」を削除しました"
}

fun getDungeonInfoMessage(dungeon: Dungeon): String {
    return prefix + "\n${AQUA}displayName: ${GREEN}${dungeon.displayName}\n${AQUA}requiredDungeonNames: ${GREEN}${
        dungeon.requiredDungeonNames.joinToString(
            ", "
        )
    }\n${AQUA}maxPlayerNumber: ${GREEN}${dungeon.maxPlayerNumber}\n${AQUA}requiredPlayerLevel: ${GREEN}${dungeon.requiredPlayerLevel}\n${AQUA}isLocked: ${GREEN}${dungeon.locked}"
}

fun getDungeonSetDisplayNameMessage(displayName: String, name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」の表示名を${GREEN}${displayName}${AQUA}に設定しました"
}

fun getDungeonAddRequiredMessage(required: String, name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」の必須ダンジョンに「${GREEN}${required}${AQUA}」を追加しました"
}

fun getDungeonRemoveRequiredMessage(required: String, name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」の必須ダンジョンから「${GREEN}${required}${AQUA}」を削除しました"
}

fun getDungeonSetMaxMessage(max: Int, name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」の最大人数を${GREEN}${max}${AQUA}に設定しました"
}

fun getDungeonSetPlayerLevelMessage(level: Int, name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」の必要プレイヤーレベルを${GREEN}${level}${AQUA}に設定しました"
}

fun getDungeonLockMessage(name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」をロックしました"
}

fun getDungeonUnlockMessage(name: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${name}${AQUA}」をロックを解除しました"
}
