package com.github.tsuoihito.tamagorpg.message

import org.bukkit.ChatColor

fun getCreateProfileMessage(): String {
    return prefix + "${ChatColor.AQUA}プロフィールを作成しました！"
}

fun getProfileAlreadyExists(): String {
    return prefix + "${ChatColor.RED}プロフィールが既に存在しています"
}
