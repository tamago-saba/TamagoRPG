package com.github.tsuoihito.tamagorpg.util

import org.bukkit.scheduler.BukkitRunnable

class Task(val f: () -> Unit) : BukkitRunnable() {
    override fun run() {
        f()
    }
}
