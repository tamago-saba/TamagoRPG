package com.github.tsuoihito.tamagorpg.role

enum class Role(val displayName: String) {
    CITIZEN("市民");

    companion object {
        fun valueOfOrNull(value: String): Role? {
            return Role.values().find { it.toString().equals(value, ignoreCase = true) }
        }
    }
}
