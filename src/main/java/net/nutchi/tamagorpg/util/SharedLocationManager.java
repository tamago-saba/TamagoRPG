package net.nutchi.tamagorpg.util;

import net.nutchi.tamagorpg.TamagoRPG;
import net.nutchi.tamagorpg.model.SharedLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SharedLocationManager {
    private static final Material locationMaterial = Material.ENDER_PEARL;
    private static final Material separatorMaterial = Material.GRAY_STAINED_GLASS_PANE;
    private static final Material addLocationMaterial = Material.EMERALD;
    private static final Material deleteLocationMaterial = Material.BARRIER;

    public static void addEntry(TamagoRPG plugin, String name, Location location) {
        plugin.getSharedLocations().add(new SharedLocation(name, location));
    }

    public static void removeEntry(TamagoRPG plugin, int index) {
        plugin.getSharedLocations().remove(index);
    }

    public static void openGui(TamagoRPG plugin, HumanEntity human) {
        Inventory gui = getGui(plugin);
        plugin.getOpeningSharedLocationGuis().add(gui);
        human.openInventory(gui);
    }

    private static Inventory getGui(TamagoRPG plugin) {
        Inventory gui = plugin.getServer().createInventory(null, 54, "地点一覧");
        int index = 0;
        for (SharedLocation sharedLocation : plugin.getSharedLocations()) {
            ItemStack item = new ItemStack(locationMaterial);
            ItemMeta meta = item.getItemMeta();
            if (meta != null && index <= 36) {
                meta.setDisplayName(sharedLocation.getName());
                meta.setLore(getDescriptionFrom(sharedLocation.getLocation()));
                item.setItemMeta(meta);
                gui.setItem(index, item);
            }
            index++;
        }

        for (int i = 36; i <= 44; i++) {
            ItemStack pane = new ItemStack(separatorMaterial);
            ItemMeta meta = pane.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(" ");
            }
            pane.setItemMeta(meta);
            gui.setItem(i, pane);
        }

        gui.setItem(48, getAddItem());
        gui.setItem(50, getDeleteItem());

        return gui;
    }

    public static Optional<Location> getLocation(TamagoRPG plugin, int index) {
        if (plugin.getSharedLocations().size() >= index + 1) {
            return Optional.of(plugin.getSharedLocations().get(index).getLocation());
        } else {
            return Optional.empty();
        }
    }

    private static List<String> getDescriptionFrom(Location loc) {
        String world = Optional.ofNullable(loc.getWorld()).map(WorldInfo::getName).orElse("");
        String coordinate = loc.getBlockX() + " / " + loc.getBlockY() + " / " + loc.getBlockZ();
        return Arrays.asList(world, coordinate);
    }

    public static boolean isDeletingLocationItem(ItemStack item) {
        return isLocationItem(item) && item.getItemMeta() != null && item.getItemMeta().getEnchants().containsKey(Enchantment.MENDING);
    }

    public static boolean isLocationItem(ItemStack item) {
        return item.getType().equals(locationMaterial);
    }

    public static ItemStack getDeleteItem() {
        ItemStack item = new ItemStack(deleteLocationMaterial);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("地点を削除");
        }
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isDeleteItem(ItemStack item) {
        return item.getType().equals(deleteLocationMaterial);
    }

    public static ItemStack getAddItem() {
        ItemStack item = new ItemStack(addLocationMaterial);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("地点を追加");
        }
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isAddItem(ItemStack item) {
        return item.getType().equals(addLocationMaterial);
    }

    public static void setDeletingModeGui(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                if (isLocationItem(item)) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        meta.addEnchant(Enchantment.MENDING, 0, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        if (meta.getLore() != null) {
                            meta.setLore(Stream.of(meta.getLore(), Collections.singletonList(ChatColor.RED + "クリックして削除")).flatMap(Collection::stream).collect(Collectors.toList()));
                        }
                    }
                    item.setItemMeta(meta);
                }
            }
        }
        inventory.remove(addLocationMaterial);
        inventory.remove(deleteLocationMaterial);
    }
}
