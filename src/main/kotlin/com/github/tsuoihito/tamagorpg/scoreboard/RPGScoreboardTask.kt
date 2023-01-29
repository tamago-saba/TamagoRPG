package com.github.tsuoihito.tamagorpg.scoreboard

import com.github.tsuoihito.tamagorpg.TamagoRPG
import org.bukkit.scheduler.BukkitRunnable

class RPGScoreboardTask(val plugin: TamagoRPG) : BukkitRunnable() {
    override fun run() {
        showScoreboard(plugin)
    }
}
