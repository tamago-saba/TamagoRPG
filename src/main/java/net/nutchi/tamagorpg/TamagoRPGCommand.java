package net.nutchi.tamagorpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.nutchi.tamagorpg.util.WorldManager;

public class TamagoRPGCommand implements TabExecutor {
    private final TamagoRPG plugin;

    public TamagoRPGCommand(TamagoRPG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "save-world":
                    return onSaveWorldCommand(sender, args);
                case "reset-world":
                    return onResetWorldCommand(sender, args);
                case "list-tags":
                    //
                case "list-scores":
                    //
            }
        }
        return false;
    }

    private boolean onSaveWorldCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String world = args[1];
            sender.sendMessage("Saving data for world '%s'...".replace("%s", world));

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (WorldManager.saveWorld(plugin, world)) {
                    sender.sendMessage("Completed saving!");
                } else {
                    sender.sendMessage("Failed to save!");
                }
            });

            return true;
        }
        return false;
    }

    private boolean onResetWorldCommand(CommandSender sender, String[] args) {
        if (args.length == 2 || args.length == 3) {
            String world = args[1];

            Runnable resetWorld = () -> {
                if (WorldManager.restoreWorld(plugin, world)) {
                    sender.sendMessage("Completed restoring!");
                } else {
                    sender.sendMessage("Failed to restore!");
                }
            };

            sender.sendMessage("Restoring data for world '%s'...".replace("%s", world));

            if (args.length == 2) {
                plugin.getServer().getScheduler().runTask(plugin, resetWorld);
            } else if (args.length == 3) {
                long delay = Long.parseLong(args[2]);
                plugin.getServer().getScheduler().runTaskLater(plugin, resetWorld, delay);
            }

            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return getCandidate(
                    Arrays.asList("save-world", "reset-world", "list-tags", "list-scores"),
                    args[0]);
        } else if (args.length > 1) {
            switch (args[0]) {
                case "save-world":
                case "reset-world":
                    switch (args.length) {
                        case 2:
                            return getWorldNameCandidate(args[1]);
                    }
            }
        }
        return new ArrayList<>();
    }

    private List<String> getWorldNameCandidate(String typing) {
        return getCandidate(plugin.getServer().getWorlds().stream().map(WorldInfo::getName)
                .collect(Collectors.toList()), typing);
    }

    private List<String> getCandidate(List<String> list, String typing) {
        return list.stream().filter(s -> s.startsWith(typing)).collect(Collectors.toList());
    }
}
