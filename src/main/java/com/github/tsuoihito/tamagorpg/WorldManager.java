package com.github.tsuoihito.tamagorpg;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class WorldManager {
    private static File getLoadedWorld(File worldContainer, String world) {
        return new File(worldContainer, world);
    }

    private static File getSavedWorld(File dataFolder, String world) {
        return new File(getSavedWorldContainer(dataFolder), world);
    }

    private static File getSavedWorldContainer(File dataFolder) {
        return new File(dataFolder, "worlds");
    }

    public static boolean saveWorld(JavaPlugin plugin, String world) {
        try {
            saveWorldWithError(plugin, world);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void saveWorldWithError(
        JavaPlugin plugin,
        String world
    ) throws IOException {
        World bukkitWorld = plugin.getServer().getWorld(world);
        if (
            getLoadedWorld(
                plugin.getServer().getWorldContainer(),
                world
            ).exists() && bukkitWorld != null
        ) {
            bukkitWorld.save();

            getSavedWorldContainer(plugin.getDataFolder()).mkdir();

            File savedWorld = getSavedWorld(plugin.getDataFolder(), world);

            if (savedWorld.exists()) {
                FileUtils.deleteDirectory(savedWorld);
            }

            FileUtils.copyDirectory(
                getLoadedWorld(plugin.getServer().getWorldContainer(), world),
                savedWorld
            );
        }
    }

    public static boolean restoreWorld(
        JavaPlugin plugin,
        MVWorldManager mvWorldManager,
        String world
    ) {
        try {
            return restoreWorldWithError(plugin, mvWorldManager, world);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean restoreWorldWithError(
        JavaPlugin plugin,
        MVWorldManager mvWorldManager,
        String world
    ) throws IOException {
        File loadedWorld =
            getLoadedWorld(plugin.getServer().getWorldContainer(), world);
        File savedWorld = getSavedWorld(plugin.getDataFolder(), world);

        if (savedWorld.exists() && mvWorldManager.unloadWorld(world)) {
            if (loadedWorld.exists()) {
                FileUtils.deleteDirectory(loadedWorld);
            }
            FileUtils.copyDirectory(savedWorld, loadedWorld);
            return mvWorldManager.loadWorld(world);
        }
        return false;
    }
}
