package com.github.tsuoihito.tamagorpg.command.rpgm

enum class DungeonCommand(val commandName: String) {
    CREATE("create"),
    DELETE("delete"),
    LIST("list"),
    INFO("info"),
    SET_DISPLAY_NAME("setdisplayname"),
    ADD_REQUIRED_DUNGEON_NAME("addrequired"),
    REMOVE_REQUIRED_DUNGEON_NAME("removerequired"),
    SET_MAX_PLAYER_NUMBER("setmax"),
    SET_REQUIRED_PLAYER_LEVEL("setlevel"),
    LOCK("lock"),
    UNLOCK("unlock");

    companion object {
        fun valueOfCommandName(commandName: String): DungeonCommand? {
            return DungeonCommand.values().find { it.commandName == commandName }
        }

        fun getCommandNames(): List<String> {
            return DungeonCommand.values().map { it.commandName }
        }
    }
}
