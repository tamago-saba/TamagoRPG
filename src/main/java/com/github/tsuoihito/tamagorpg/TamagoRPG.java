package com.github.tsuoihito.tamagorpg;

import com.github.tsuoihito.tamagorpg.listener.RPGCheckListener;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
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

            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(new RPGCheckListener(), this);
        }
    }

    @Override
    public void onDisable() {
    }

    public MVWorldManager getMVWorldManager() {
        return mvWorldManager;
    }
}
