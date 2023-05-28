package com.atom.skyblock.powerups;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface PowerUpItem {

    public static ItemStack returnItemStack() {
        return new ItemStack(Material.COAL);
    }

    public static boolean verify(final ItemStack is) {
        return false;
    }

}
