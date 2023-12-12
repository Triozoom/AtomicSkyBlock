package com.atom.skyblock.ultilidadesfodas;

import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

@SuppressWarnings("all")
public class LootChests
{
    public Chest lootChestP1(final Block setBlock, final int chestNum) {
        switch (chestNum) {
            case 1: {
                final Chest chest = (Chest)setBlock.getState();
                final Inventory inv = chest.getBlockInventory();
                final ItemStack apple = new ItemStack(Material.APPLE, MathAndRNG.generateInteger(4));
                final ItemStack sapling = new ItemStack(Material.OAK_SAPLING, MathAndRNG.generateInteger(7));
                final ItemStack whaseeds = new ItemStack(Material.MELON_SEEDS, MathAndRNG.generateInteger(14));
                final ItemStack pumpseeds = new ItemStack(Material.PUMPKIN_SEEDS, MathAndRNG.generateInteger(12));
                final ItemStack dirt = new ItemStack(Material.DIRT, MathAndRNG.generateInteger(3));
                final ItemStack bucket = new ItemStack(Material.BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, MathAndRNG.generateInteger(34));
                final ItemStack ice = new ItemStack(Material.ICE, MathAndRNG.generateInteger(2));
                final ItemStack[] array;
                final ItemStack[] all = array = new ItemStack[] { apple, sapling, dirt, bucket, cobblestone, ice };
                for (final ItemStack is : array) {
                    final int i = MathAndRNG.generateInteger(26);
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, is);
                    }
                }
                return chest;
            }
            case 2: {
                final Chest chest2 = (Chest)setBlock.getState();
                final Inventory inv2 = chest2.getBlockInventory();
                final ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, MathAndRNG.generateInteger(4));
                final ItemStack seeds = new ItemStack(Material.WHEAT_SEEDS, MathAndRNG.generateInteger(10));
                final ItemStack carrot = new ItemStack(Material.CARROT, MathAndRNG.generateInteger(24));
                final ItemStack grassbl = new ItemStack(Material.GRASS_BLOCK, MathAndRNG.generateInteger(4));
                final ItemStack stonecutter = new ItemStack(Material.STONECUTTER, MathAndRNG.generateInteger(2));
                final ItemStack cowspawnegg = new ItemStack(Material.COW_SPAWN_EGG, MathAndRNG.generateInteger(4));
                final ItemStack potato = new ItemStack(Material.POTATO, MathAndRNG.generateInteger(24));
                final ItemStack[] array2;
                final ItemStack[] all2 = array2 = new ItemStack[] { gapple, carrot, seeds, grassbl, stonecutter, cowspawnegg, potato };
                for (final ItemStack is2 : array2) {
                    final int j = MathAndRNG.generateInteger(26);
                    if (inv2.getItem(j) == null) {
                        inv2.setItem(j, is2);
                    }
                }
                return chest2;
            }
            case 3: {
                final Chest chest3 = (Chest)setBlock.getState();
                final Inventory inv3 = chest3.getBlockInventory();
                final ItemStack villageregg = new ItemStack(Material.VILLAGER_SPAWN_EGG, MathAndRNG.generateInteger(4));
                final ItemStack wood = new ItemStack(Material.OAK_LOG, MathAndRNG.generateInteger(7));
                final ItemStack cerca = new ItemStack(Material.OAK_FENCE, MathAndRNG.generateInteger(6));
                final ItemStack cerca2 = new ItemStack(Material.OAK_FENCE_GATE, MathAndRNG.generateInteger(3));
                final ItemStack cookie = new ItemStack(Material.COOKIE, MathAndRNG.generateInteger(34));
                final ItemStack waterbucket = new ItemStack(Material.WATER_BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack parrotegg = new ItemStack(Material.PARROT_SPAWN_EGG, MathAndRNG.generateInteger(2));
                final ItemStack[] array3;
                final ItemStack[] all3 = array3 = new ItemStack[] { villageregg, wood, cerca, cerca2, cookie, parrotegg };
                for (final ItemStack is3 : array3) {
                    final int k = MathAndRNG.generateInteger(26);
                    if (inv3.getItem(k) == null) {
                        inv3.setItem(k, is3);
                    }
                }
                return chest3;
            }
            default: {
                return null;
            }
        }
    }
    
    public Chest lootChestP2(final Block setBlock, final int chestNum) {
        switch (chestNum) {
            case 1: {
                final Chest chest = (Chest)setBlock.getState();
                final Inventory inv = chest.getBlockInventory();
                final ItemStack haybale = new ItemStack(Material.HAY_BLOCK, MathAndRNG.generateInteger(6));
                final ItemStack haybale2 = new ItemStack(Material.APPLE, MathAndRNG.generateInteger(12));
                final ItemStack igspawn = new ItemStack(Material.OAK_SAPLING, MathAndRNG.generateInteger(2));
                final ItemStack doggy = new ItemStack(Material.DIRT, MathAndRNG.generateInteger(12));
                final ItemStack beehive = new ItemStack(Material.BUCKET, MathAndRNG.generateInteger(5));
                final ItemStack smoker = new ItemStack(Material.APPLE, MathAndRNG.generateInteger(10));
                final ItemStack coal = new ItemStack(Material.COAL_BLOCK, MathAndRNG.generateInteger(3));
                final ItemStack[] array;
                final ItemStack[] all = array = new ItemStack[] { haybale, igspawn, doggy, beehive, smoker, coal };
                for (final ItemStack is : array) {
                    final int i = MathAndRNG.generateInteger(26);
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, is);
                    }
                }
                return chest;
            }
            case 2: {
                final Chest chest2 = (Chest)setBlock.getState();
                final Inventory inv2 = chest2.getBlockInventory();
                final ItemStack bfurnace = new ItemStack(Material.BLAST_FURNACE, MathAndRNG.generateInteger(4));
                final ItemStack iron = new ItemStack(Material.RAW_IRON, MathAndRNG.generateInteger(13));
                final ItemStack tnt = new ItemStack(Material.TNT, MathAndRNG.generateInteger(2));
                final ItemStack emeralds = new ItemStack(Material.EMERALD, MathAndRNG.generateInteger(10));
                final ItemStack diamonds = new ItemStack(Material.DIAMOND, MathAndRNG.generateInteger(2));
                final ItemStack ice = new ItemStack(Material.ICE, MathAndRNG.generateInteger(2));
                final ItemStack[] array2;
                final ItemStack[] all2 = array2 = new ItemStack[] { bfurnace, iron, tnt, emeralds, diamonds, ice };
                for (final ItemStack is2 : array2) {
                    final int j = MathAndRNG.generateInteger(26);
                    if (inv2.getItem(j) == null) {
                        inv2.setItem(j, is2);
                    }
                }
                return chest2;
            }
            case 3: {
                final Chest chest3 = (Chest)setBlock.getState();
                final Inventory inv3 = chest3.getBlockInventory();
                final ItemStack ipick = new ItemStack(Material.IRON_PICKAXE, 1);
                final ItemStack iaxe = new ItemStack(Material.IRON_AXE, 1);
                final ItemStack ihoe = new ItemStack(Material.IRON_HOE, MathAndRNG.generateInteger(2));
                final ItemStack bucket = new ItemStack(Material.BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack isword = new ItemStack(Material.IRON_SWORD, 1);
                final ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, MathAndRNG.generateInteger(8));
                final ItemStack[] array3;
                final ItemStack[] all3 = array3 = new ItemStack[] { ipick, iaxe, ihoe, bucket, isword, gapple };
                for (final ItemStack is3 : array3) {
                    final int k = MathAndRNG.generateInteger(26);
                    if (inv3.getItem(k) == null) {
                        inv3.setItem(k, is3);
                    }
                }
                return chest3;
            }
            default: {
                return null;
            }
        }
    }
    
    public Chest lootChestP3(final Block setBlock, final int chestNum) {
        switch (chestNum) {
            case 1: {
                final Chest chest = (Chest)setBlock.getState();
                final Inventory inv = chest.getBlockInventory();
                final ItemStack netherrack = new ItemStack(Material.NETHERRACK, MathAndRNG.generateInteger(64));
                final ItemStack powder = new ItemStack(Material.BLAZE_POWDER, MathAndRNG.generateInteger(4));
                final ItemStack witherskull = new ItemStack(Material.WITHER_SKELETON_SKULL, MathAndRNG.generateInteger(3));
                final ItemStack bucket = new ItemStack(Material.POWDER_SNOW_BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack soulsand = new ItemStack(Material.SOUL_SAND, MathAndRNG.generateInteger(24));
                final ItemStack[] array;
                final ItemStack[] all = array = new ItemStack[] { netherrack, powder, witherskull, bucket, soulsand };
                for (final ItemStack is : array) {
                    final int i = MathAndRNG.generateInteger(26);
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, is);
                    }
                }
                return chest;
            }
            case 2: {
                final Chest chest2 = (Chest)setBlock.getState();
                final Inventory inv2 = chest2.getBlockInventory();
                final ItemStack gold = new ItemStack(Material.NETHER_GOLD_ORE, MathAndRNG.generateInteger(14));
                final ItemStack quartz = new ItemStack(Material.QUARTZ, MathAndRNG.generateInteger(7));
                final ItemStack lavabucket = new ItemStack(Material.LAVA_BUCKET, MathAndRNG.generateInteger(3));
                final ItemStack sssssss = new ItemStack(Material.CREEPER_SPAWN_EGG, MathAndRNG.generateInteger(2));
                final ItemStack food = new ItemStack(Material.PORKCHOP, MathAndRNG.generateInteger(34));
                final ItemStack[] array2;
                final ItemStack[] all2 = array2 = new ItemStack[] { gold, quartz, lavabucket, sssssss, food };
                for (final ItemStack is2 : array2) {
                    final int j = MathAndRNG.generateInteger(26);
                    if (inv2.getItem(j) == null) {
                        inv2.setItem(j, is2);
                    }
                }
                return chest2;
            }
            case 3: {
                final Chest chest3 = (Chest)setBlock.getState();
                final Inventory inv3 = chest3.getBlockInventory();
                final ItemStack gpick = new ItemStack(Material.GOLDEN_PICKAXE, MathAndRNG.generateInteger(4));
                final ItemStack gsword = new ItemStack(Material.GOLDEN_SWORD, MathAndRNG.generateInteger(4));
                final ItemStack gingot = new ItemStack(Material.GOLD_INGOT, MathAndRNG.generateInteger(25));
                final ItemStack ghoe = new ItemStack(Material.GOLDEN_HOE, MathAndRNG.generateInteger(4));
                final ItemStack gaxe = new ItemStack(Material.GOLDEN_AXE, MathAndRNG.generateInteger(4));
                final ItemStack[] array3;
                final ItemStack[] all3 = array3 = new ItemStack[] { gpick, gsword, gingot, ghoe, gaxe };
                for (final ItemStack is3 : array3) {
                    final int k = MathAndRNG.generateInteger(26);
                    if (inv3.getItem(k) == null) {
                        inv3.setItem(k, is3);
                    }
                }
                return chest3;
            }
            default: {
                return null;
            }
        }
    }
    
    public Chest lootChestP4(final Block setBlock, final int chestNum) {
        switch (chestNum) {
            case 1: {
                final Chest chest = (Chest)setBlock.getState();
                final Inventory inv = chest.getBlockInventory();
                final ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, MathAndRNG.generateInteger(2));
                final ItemStack sb = new ItemStack(Material.STONE_BRICKS, MathAndRNG.generateInteger(7));
                final ItemStack dimond = new ItemStack(Material.DIAMOND, MathAndRNG.generateInteger(5));
                final ItemStack water = new ItemStack(Material.WATER_BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack thingything = new ItemStack(Material.BREWING_STAND, MathAndRNG.generateInteger(4));
                final ItemStack[] array;
                final ItemStack[] all = array = new ItemStack[] { enderpearl, sb, dimond, water, thingything };
                for (final ItemStack is : array) {
                    final int i = MathAndRNG.generateInteger(26);
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, is);
                    }
                }
                return chest;
            }
            case 2: {
                final Chest chest2 = (Chest)setBlock.getState();
                final Inventory inv2 = chest2.getBlockInventory();
                final ItemStack blazepowder = new ItemStack(Material.BLAZE_POWDER, 1);
                final ItemStack endermanse = new ItemStack(Material.ENDERMAN_SPAWN_EGG, MathAndRNG.generateInteger(3));
                final ItemStack stronguiholdi = new ItemStack(Material.ENDER_EYE, 1);
                final ItemStack sgakklgma = new ItemStack(Material.MOSSY_STONE_BRICKS, MathAndRNG.generateInteger(4));
                final ItemStack apple = new ItemStack(Material.APPLE, MathAndRNG.generateInteger(34));
                final ItemStack[] array2;
                final ItemStack[] all2 = array2 = new ItemStack[] { apple, blazepowder, endermanse, stronguiholdi, sgakklgma };
                for (final ItemStack is2 : array2) {
                    final int j = MathAndRNG.generateInteger(26);
                    if (inv2.getItem(j) == null) {
                        inv2.setItem(j, is2);
                    }
                }
                return chest2;
            }
            case 3: {
                final Chest chest3 = (Chest)setBlock.getState();
                final Inventory inv3 = chest3.getBlockInventory();
                final ItemStack dpick = new ItemStack(Material.DIAMOND_PICKAXE, MathAndRNG.generateInteger(4));
                final ItemStack daxe = new ItemStack(Material.OAK_SAPLING, MathAndRNG.generateInteger(4));
                final ItemStack dsword = new ItemStack(Material.DIRT, MathAndRNG.generateInteger(4));
                final ItemStack dhoe = new ItemStack(Material.BUCKET, MathAndRNG.generateInteger(4));
                final ItemStack sussy = new ItemStack(Material.MOSSY_STONE_BRICKS, MathAndRNG.generateInteger(34));
                final ItemStack[] array3;
                final ItemStack[] all3 = array3 = new ItemStack[] { dpick, daxe, dsword, dhoe, sussy };
                for (final ItemStack is3 : array3) {
                    final int k = MathAndRNG.generateInteger(26);
                    if (inv3.getItem(k) == null) {
                        inv3.setItem(k, is3);
                    }
                }
                return chest3;
            }
            default: {
                return null;
            }
        }
    }
    
    public Chest lootChestP5(final Block setBlock, final int chestNum) {
        switch (chestNum) {
            case 1: {
                final Chest chest = (Chest)setBlock.getState();
                final Inventory inv = chest.getBlockInventory();
                final ItemStack end = new ItemStack(Material.END_STONE, MathAndRNG.generateInteger(12));
                final ItemStack dragonbreth = new ItemStack(Material.DRAGON_BREATH, MathAndRNG.generateInteger(7));
                final ItemStack egg = new ItemStack(Material.DRAGON_EGG, MathAndRNG.generateInteger(2));
                final ItemStack hed = new ItemStack(Material.DRAGON_HEAD, MathAndRNG.generateInteger(2));
                final ItemStack obsidian = new ItemStack(Material.OBSIDIAN, MathAndRNG.generateInteger(34));
                final ItemStack[] array;
                final ItemStack[] all = array = new ItemStack[] { end, obsidian, egg, hed, dragonbreth };
                for (final ItemStack is : array) {
                    final int i = MathAndRNG.generateInteger(26);
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, is);
                    }
                }
                return chest;
            }
            case 2: {
                final Chest chest2 = (Chest)setBlock.getState();
                final Inventory inv2 = chest2.getBlockInventory();
                final ItemStack nametag = new ItemStack(Material.NAME_TAG, MathAndRNG.generateInteger(4));
                final ItemStack sapling = new ItemStack(Material.OAK_SAPLING, MathAndRNG.generateInteger(7));
                final ItemStack dirt = new ItemStack(Material.DIRT, MathAndRNG.generateInteger(3));
                final ItemStack bucket = new ItemStack(Material.BUCKET, MathAndRNG.generateInteger(2));
                final ItemStack cobblestone = new ItemStack(Material.APPLE, MathAndRNG.generateInteger(34));
                final ItemStack[] array2;
                final ItemStack[] all2 = array2 = new ItemStack[] { nametag, sapling, dirt, bucket, cobblestone };
                for (final ItemStack is2 : array2) {
                    final int j = MathAndRNG.generateInteger(26);
                    if (inv2.getItem(j) == null) {
                        inv2.setItem(j, is2);
                    }
                }
                return chest2;
            }
            case 3: {
                final Chest chest3 = (Chest)setBlock.getState();
                final Inventory inv3 = chest3.getBlockInventory();
                final ItemStack npick = new ItemStack(Material.NETHERITE_PICKAXE, 1);
                final ItemStack naxe = new ItemStack(Material.NETHERITE_AXE, MathAndRNG.generateInteger(2));
                final ItemStack nsword = new ItemStack(Material.NETHERITE_SWORD, MathAndRNG.generateInteger(2));
                final ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, MathAndRNG.generateInteger(2));
                final ItemStack ENCAHNT = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                final ItemStack[] array3;
                final ItemStack[] all3 = array3 = new ItemStack[] { npick, naxe, nsword, chestplate, ENCAHNT };
                for (final ItemStack is3 : array3) {
                    final int k = MathAndRNG.generateInteger(26);
                    if (inv3.getItem(k) == null) {
                        inv3.setItem(k, is3);
                    }
                }
                return chest3;
            }
            default: {
                return null;
            }
        }
    }
}
