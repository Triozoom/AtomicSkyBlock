package com.atom.skyblock.farms.items;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.farms.Farm;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FarmItemManager {

    public static boolean isValidFarmMaterial(final Material material) {
        switch (material) {
            case GOLD_BLOCK:
            case DIAMOND_BLOCK:
            case NETHERITE_BLOCK:
                return true;
        }
        return false;
    }

    public static void give(final Farm farm, final Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation().add(0, 0.5, 0), get(farm.spawnType));
            return;
        }

        player.getInventory().addItem(get(farm.spawnType));
    }

    public static void give(final Player player, final Farm.EntitiesType type) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation().add(0, 0.5, 0), get(type));
            return;
        }

        player.getInventory().addItem(get(type));
    }

    private static ItemStack get(final Farm.EntitiesType type) {
        final ItemStack baseItem = new ItemStack(mft(type));

        final ItemMeta im = baseItem.getItemMeta();
        im.setDisplayName("§c§lFARM DE MONSTROS");
        im.setLore(replaceWith(Arrays.asList(
                " §7",
                " §7Esta farm terá chances de spawnar um monstro",
                " §7a cada 2 segundos. Informações: ",
                " §7Tipo: %type%",
                " §7Raridade: %rarity%",
                " §7"
        ), type));
        baseItem.setItemMeta(im);

        return baseItem;
    }

    private static Material mft(final Farm.EntitiesType t) {
        switch (t) {
            case NETHER:
            case ALL:
                return Material.DIAMOND_BLOCK;
            case ALL_NON_DANGER:
                return Material.NETHERITE_BLOCK;
            default:
                return Material.GOLD_BLOCK;
        }
    }

    private static List<String> replaceWith(final List<String> lore, final Farm.EntitiesType etype) {
        switch (etype) {
            case OVERWORLD:
                return lore.stream().map(
                        str -> str.replaceAll("%type%", "Mobs do Overworld").replaceAll("%rarity%", "§eIncomum")
                ).collect(Collectors.toList());
            case OVERWORLD_NON_DANGER:
                return lore.stream().map(
                        str -> str.replaceAll("%type%", "Animais do Overworld").replaceAll("%rarity%", "§aComum")
                ).collect(Collectors.toList());
            case NETHER:
                return lore.stream().map(
                        str -> str.replaceAll("%type%", "Mobs do Nether").replaceAll("%rarity%", "§bRara")
                ).collect(Collectors.toList());
            case ALL:
                return lore.stream().map(
                        str -> str.replaceAll("%type%", "Todos os Mobs").replaceAll("%rarity%", "§6Híper Rara")
                ).collect(Collectors.toList());
            case ALL_NON_DANGER:
                return lore.stream().map(
                        str -> str.replaceAll("%type%", "Todos os Mobs com Animais").replaceAll("%rarity%", "§3Soberano")
                ).collect(Collectors.toList());
            default: // why do I have to include this everything else is done bro
                return lore;
        }
    }



    /*
        Requires NOT NULL item stack.
     */
    public static Farm.EntitiesType fromItem(final ItemStack is) {
        final List<String> s = new ArrayList<>(is.getItemMeta().getLore());
        if (s.stream().anyMatch(str->str.contains("Mobs do Overworld"))) return Farm.EntitiesType.OVERWORLD;
        if (s.stream().anyMatch(str->str.contains("Animais do Overworld"))) return Farm.EntitiesType.OVERWORLD_NON_DANGER;
        if (s.stream().anyMatch(str->str.contains("Mobs do Nether"))) return Farm.EntitiesType.NETHER;
        if (s.stream().anyMatch(str->str.contains("Todos os Mobs com Animais"))) return Farm.EntitiesType.ALL_NON_DANGER;
        if (s.stream().anyMatch(str->str.contains("Todos os Mobs"))) return Farm.EntitiesType.ALL;
        return null;
    }

}
