package com.github.tsuoihito.tamagorpg.message

import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GREEN

fun getSavingWorldMessage(world: String): String {
    return prefix + "${AQUA}ワールド「${GREEN}${world}${AQUA}」のデータをセーブしています..."
}

fun getCompleteSavingWorldMessage(world: String): String {
    return prefix + "${AQUA}ワールド「${GREEN}${world}${AQUA}」のデータがセーブされました"
}

fun getResettingWorldMessage(world: String): String {
    return prefix + "${AQUA}ワールド「${GREEN}${world}${AQUA}」のデータをリセットしています..."
}

fun getCompleteResettingWorldMessage(world: String): String {
    return prefix + "${AQUA}ワールド「${GREEN}${world}${AQUA}」のデータがリセットされました"
}
