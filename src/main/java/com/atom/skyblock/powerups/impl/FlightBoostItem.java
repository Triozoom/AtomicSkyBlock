package com.atom.skyblock.powerups.impl;

import com.atom.skyblock.powerups.PowerUpItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FlightBoostItem implements PowerUpItem {

    public static ItemStack returnItemStack() {
        final ItemStack is = new ItemStack(Material.FEATHER);
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName("§6§lACTIVATE FLIGHT BOOSTER");
        im.setLore(Arrays.asList(" "," §7Right Click to activate a flight boost for everyone!", " "));
        is.setItemMeta(im);
        return is;
    }

    public static boolean verify(final ItemStack is) {
        if (is == null) return false;
        final ItemMeta im = is.getItemMeta();
        if (im == null) return false;
        if (im.getDisplayName() == null) return false;
        return im.getDisplayName().equalsIgnoreCase("§6§lACTIVATE FLIGHT BOOSTER");
    }

}
