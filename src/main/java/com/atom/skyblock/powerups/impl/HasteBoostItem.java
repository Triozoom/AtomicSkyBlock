package com.atom.skyblock.powerups.impl;

import com.atom.skyblock.powerups.PowerUpItemWithValue;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HasteBoostItem implements PowerUpItemWithValue {

    public static ItemStack returnItemStack(final float boost) {
        final ItemStack is = new ItemStack(Material.GOLDEN_PICKAXE);
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(String.format("§6§lACTIVATE %.1fx MINING SPEED BOOSTER", boost));
        im.setLore(Arrays.asList(" ", String.format(" §7Right Click to activate a %.1fx boost on mining speed!", boost), " "));
        is.setItemMeta(im);
        return is;
    }

    public static float returnBoostAmountFromIs(final ItemStack is) {
        if (is == null) return 0.f;
        final ItemMeta im = is.getItemMeta();
        if (im == null) return 0.f;
        if (im.getDisplayName() == null) return 0.f;
        final String filtered = im.getDisplayName().replaceAll("§6§lACTIVATE ", "").replaceAll("x MINING SPEED BOOSTER", "");
        return Float.parseFloat(filtered);
    }

    public static boolean verify(final ItemStack is) {
        if (is == null) return false;
        final ItemMeta im = is.getItemMeta();
        if (im == null) return false;
        if (im.getDisplayName() == null) return false;
        return im.getDisplayName().startsWith("§6§lACTIVATE") && im.getDisplayName().contains("MINING SPEED BOOSTER");
    }

}
