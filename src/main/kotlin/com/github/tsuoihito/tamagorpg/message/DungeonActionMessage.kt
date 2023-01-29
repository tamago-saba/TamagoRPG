package com.github.tsuoihito.tamagorpg.message

import org.bukkit.ChatColor.*

fun getDungeonCompleteMessage(dungeon: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${dungeon}${AQUA}」をクリアした！"
}

fun getDungeonOverMessage(dungeon: String): String {
    return prefix + "${AQUA}ダンジョン「${GREEN}${dungeon}${AQUA}」の攻略に失敗した。。"
}

fun getReturningToOverWorldMessage(delay: Long): String {
    return prefix + "${GREEN}${delay}${AQUA}秒後にオーバーワールドへテレポートします"
}

fun getDungeonIsNowLockedMessage(): String {
    return prefix + "${RED}ダンジョンは現在ロックされています"
}

fun getYouNeedCompleteMoreDungeonsMessage(dungeons: List<String>): String {
    return prefix + "${RED}このダンジョンに移動するには次のダンジョンをクリアしている必要があります:${GREEN} " + dungeons.joinToString("${AQUA},${GREEN} ")
}

fun getYouNeedGetHigherLevelMessage(level: Int): String {
    return prefix + "${RED}このダンジョンに移動するには${GREEN}Lv.${level}${RED}に達している必要があります"
}

fun getDungeonHasMaxPlayersMessage(): String {
    return prefix + "${RED}このダンジョンは現在、最大参加可能人数に達しています"
}
