package com.github.tsuoihito.tamagorpg;

import com.github.tsuoihito.tamagorpg.model.CommandBlockScoreResult;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;

public class RPGMessage {
    public static String getSavingWorldDataMessage(String world) {
        return "Saving data for world '%s'".replace("%s", world);
    }

    public static String getCompletedSavingMessage() {
        return "Completed saving";
    }

    public static String getFailedToSaveMessage() {
        return "Failed to save";
    }

    public static String getRestoringWorldDataMessage(String world) {
        return "Restoring data for world '%s'".replace("%s", world);
    }

    public static String getCompletedRestoringMessage() {
        return "Completed restoring";
    }

    public static String getFailedToRestoreMessage() {
        return "Failed to restore";
    }

    public static String getCheckingScoresMessage() {
        return "Checking scores...";
    }

    public static TextComponent getFoundScoreMessage(
        CommandBlockScoreResult result
    ) {
        TextComponent first = new TextComponent(
                getReplacedText("Found score '%s' at CommandBlock ", result)
            );
        TextComponent second = new TextComponent(
                getReplacedText("&n%w (%x, %y, %z)", result)
            );
        String cmd = getReplacedText(
                "/execute as @s in %w run teleport %x %y %z",
                result
            );
        second.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        first.addExtra(second);
        return first;
    }

    private static String getReplacedText(
            String original,
            CommandBlockScoreResult result
        ) {
        return ChatColor.translateAlternateColorCodes('&', original)
                .replace("%s", result.getName())
                .replace("%w", getWorldName(result.getWorld()))
                .replace("%x", Integer.toString(result.getX()))
                .replace("%y", Integer.toString(result.getY()))
                .replace("%z", Integer.toString(result.getZ()));
    }

    public static String getFoundNoScoreMessage() {
        return "Found no score";
    }

    private static String getWorldName(String world) {
        switch (world) {
            case "world":
                return "minecraft:overworld";
            case "world_nether":
                return "minecraft:the_nether";
            case "world_the_end":
                return "minecraft:the_end";
            default:
                return "minecraft:" + world;
        }
    }
}
