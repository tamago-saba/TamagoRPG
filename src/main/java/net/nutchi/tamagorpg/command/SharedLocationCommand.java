package net.nutchi.tamagorpg.command;

import net.nutchi.tamagorpg.TamagoRPG;
import net.nutchi.tamagorpg.util.SharedLocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SharedLocationCommand implements TabExecutor {
    private final TamagoRPG plugin;

    public SharedLocationCommand(TamagoRPG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            SharedLocationManager.openGui(plugin, player);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
