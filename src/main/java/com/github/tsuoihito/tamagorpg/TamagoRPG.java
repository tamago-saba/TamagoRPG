package com.github.tsuoihito.tamagorpg;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TamagoRPG extends JavaPlugin {
    private MVWorldManager mvWorldManager;

    @Override
    public void onEnable() {
        Plugin multiverseCorePlugin =
            getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCorePlugin instanceof MultiverseCore) {
            MultiverseCore core = (MultiverseCore) multiverseCorePlugin;
            mvWorldManager = core.getMVWorldManager();

            getCommand("rpg").setExecutor(new TamagoRPGCommand(this));
        }
    }

    @Override
    public void onDisable() {
    }

    public MVWorldManager getMVWorldManager() {
        return mvWorldManager;
    }
}
