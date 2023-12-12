package com.atom.skyblock.powerups;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface PowerUpItemWithValue {

    public static ItemStack returnItemStack(final float value) {
        return new ItemStack(Material.COAL);
    }

    public static float returnBoostAmountFromIs(final ItemStack is) {
        return 0.f;
    }

    public static boolean verify(final ItemStack is) {
        return false;
    }

}
