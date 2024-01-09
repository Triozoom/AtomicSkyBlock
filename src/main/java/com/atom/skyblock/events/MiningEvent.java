package com.atom.skyblock.events;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.api.PhaseUpdateEvent;
import com.atom.skyblock.api.VoidDeathEvent;
import com.atom.skyblock.configuration.SBConfig;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.farms.Farm;
import com.atom.skyblock.farms.items.FarmItemManager;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import com.atom.skyblock.ultilidadesfodas.LootChests;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import io.puharesource.mc.titlemanager.api.v2.*;
import com.atom.skyblock.powerups.impl.BoosterItem;
import com.atom.skyblock.powerups.PowerupManager;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.Damageable;

public class MiningEvent implements Listener
{
    TitleManagerAPI api;
    private boolean hasChangedLastTime;
    private int failed;
    private int delayIfUnlucky;
    private final Material[] p1Blocks;
    private final EntityType[] p1Entities;
    private final Material[] p2Blocks;
    private final EntityType[] p2Entities;
    private final Material[] p3Blocks;
    private final EntityType[] p3Entities;
    private final Material[] p4Blocks;
    private final EntityType[] p4Entities;
    private final Material[] p5Blocks;
    private final EntityType[] p5Entities;
    LootChests setChests;
    
    public MiningEvent() {
        this.api = (TitleManagerAPI)Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        this.hasChangedLastTime = false;
        this.p1Blocks = new Material[] { Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.OAK_PLANKS, Material.OAK_PLANKS, Material.OAK_PLANKS, Material.OAK_PLANKS, Material.OAK_LOG, Material.OAK_LOG, Material.BRICKS, Material.BRICKS, Material.BRICKS, Material.BRICKS, Material.BRICKS, Material.IRON_ORE, Material.IRON_ORE, Material.COAL_ORE, Material.COAL_ORE, Material.COAL_ORE, Material.COAL_ORE, Material.RAW_IRON_BLOCK, Material.COPPER_ORE, Material.COPPER_ORE, Material.COPPER_ORE, Material.STONE_BRICKS, Material.STONE_BRICKS, Material.STONE_BRICKS, Material.CLAY, Material.CLAY, Material.CLAY, Material.CLAY, Material.GRAVEL, Material.GRAVEL, Material.GRAVEL };
        this.p1Entities = new EntityType[] { EntityType.CAT, EntityType.CAT, EntityType.COW, EntityType.CHICKEN, EntityType.BEE, EntityType.PIG, EntityType.WOLF, EntityType.SHEEP, EntityType.VILLAGER, EntityType.FOX, EntityType.GOAT, EntityType.HORSE, EntityType.CREEPER, EntityType.LLAMA, EntityType.DONKEY, EntityType.GLOW_SQUID, EntityType.SQUID, EntityType.ENDERMAN, EntityType.MULE, EntityType.RABBIT };
        this.p2Blocks = new Material[] { Material.SAND, Material.SAND, Material.SANDSTONE, Material.SANDSTONE, Material.RED_SANDSTONE, Material.ACACIA_LOG, Material.ACACIA_PLANKS, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.CLAY, Material.GRAVEL, Material.GRAVEL, Material.SNOW_BLOCK, Material.POWDER_SNOW, Material.STONE, Material.STONE, Material.STONE, Material.BONE_BLOCK, Material.EMERALD_ORE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.PUMPKIN, Material.PUMPKIN, Material.AMETHYST_BLOCK, Material.BIG_DRIPLEAF, Material.BLUE_ICE, Material.ICE, Material.ICE, Material.BLUE_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER };
        this.p2Entities = new EntityType[] { EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.BAT, EntityType.MINECART, EntityType.MINECART_MOB_SPAWNER, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE_VILLAGER, EntityType.SPIDER, EntityType.SPIDER, EntityType.SQUID, EntityType.SALMON, EntityType.PHANTOM, EntityType.SALMON, EntityType.DONKEY, EntityType.SHEEP, EntityType.SHEEP, EntityType.SHEEP, EntityType.PUFFERFISH, EntityType.POLAR_BEAR, EntityType.POLAR_BEAR, EntityType.MUSHROOM_COW, EntityType.CAVE_SPIDER, EntityType.CAVE_SPIDER, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.STRAY, EntityType.DROWNED, EntityType.DROWNED, EntityType.DROWNED, EntityType.PANDA, EntityType.PARROT, EntityType.PARROT, EntityType.PARROT, EntityType.RABBIT, EntityType.RABBIT, EntityType.RABBIT };
        this.p3Blocks = new Material[] { Material.NETHERRACK, Material.NETHERRACK, Material.NETHERRACK, Material.OAK_LOG, Material.NETHER_BRICKS, Material.NETHER_BRICKS, Material.ANCIENT_DEBRIS, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.NETHER_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_QUARTZ_ORE, Material.CRACKED_NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS, Material.LAVA_CAULDRON, Material.RED_NETHER_BRICKS, Material.GLOWSTONE, Material.GLOWSTONE, Material.GLOWSTONE, Material.BLACKSTONE, Material.OBSIDIAN, Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.CRYING_OBSIDIAN, Material.SOUL_CAMPFIRE, Material.SOUL_LANTERN, Material.SOUL_SAND, Material.BONE_BLOCK, Material.GRAVEL };
        this.p3Entities = new EntityType[] { EntityType.BLAZE, EntityType.BLAZE, EntityType.WITHER_SKELETON, EntityType.WITHER_SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.HOGLIN, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN, EntityType.MAGMA_CUBE, EntityType.CHICKEN, EntityType.STRIDER, EntityType.ENDERMAN, EntityType.GHAST };
        this.p4Blocks = new Material[] { Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.ENDER_CHEST, Material.OAK_LOG, Material.STONE, Material.DIAMOND_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.RAW_GOLD_BLOCK, Material.POWDER_SNOW_CAULDRON, Material.STONE_BRICKS, Material.MOSSY_STONE_BRICKS, Material.GRAVEL };
        this.p4Entities = new EntityType[] { EntityType.ENDERMAN, EntityType.VINDICATOR, EntityType.PILLAGER, EntityType.SKELETON_HORSE, EntityType.SKELETON, EntityType.CREEPER, EntityType.ZOMBIE, EntityType.SPIDER, EntityType.EVOKER, EntityType.GUARDIAN, EntityType.IRON_GOLEM, EntityType.SNOWMAN, EntityType.SILVERFISH, EntityType.GOAT, EntityType.CHICKEN, EntityType.BEE, EntityType.PIG, EntityType.WOLF, EntityType.SHEEP, EntityType.VILLAGER, EntityType.HORSE };
        this.p5Blocks = new Material[] { Material.GRASS_BLOCK, Material.GRASS_BLOCK, Material.ENDER_CHEST, Material.ENDER_CHEST, Material.ENDER_CHEST, Material.END_STONE_BRICKS, Material.END_STONE, Material.ENDER_CHEST, Material.COAL_ORE, Material.DIAMOND_ORE, Material.DIAMOND_ORE, Material.STONE, Material.STONE, Material.STONE, Material.STONE, Material.STONE, Material.STONE, Material.STONE, Material.STONE_BRICKS, Material.STONE_BRICKS, Material.STONE_BRICKS, Material.CLAY, Material.GRAVEL };
        this.p5Entities = new EntityType[] { EntityType.ENDERMAN, EntityType.ENDERMAN, EntityType.ENDERMAN, EntityType.ZOMBIE, EntityType.SLIME, EntityType.SKELETON, EntityType.SKELETON, EntityType.SKELETON, EntityType.SHULKER, EntityType.ZOMBIE, EntityType.SPIDER, EntityType.SILVERFISH, EntityType.ELDER_GUARDIAN, EntityType.GUARDIAN, EntityType.CREEPER, EntityType.ENDERMITE, EntityType.ENDERMITE };
        this.setChests = new LootChests();
    }
    
    @EventHandler
    public void shit(final BlockBreakEvent ev) {
        if (ev.getBlock().getLocation().equals(SBMain.globalCobblestoneLocation)) {
            ev.setCancelled(true);
            final GlobalCobblestoneMineEvent vde = new GlobalCobblestoneMineEvent(ev.getPlayer(), ev.getBlock().getType());
            Bukkit.getPluginManager().callEvent(vde);
            SBMain.totalGlobalCobblestoneBroken += (int)(1 * PowerupManager.cobbleBoost);
            DataManager.get(ev.getPlayer()).blocksBroken++;
            DataManager.get(ev.getPlayer()).blocksBrokenHaste = (PowerupManager.hasteBoostTimeLeft > 0 ? DataManager.get(ev.getPlayer()).blocksBrokenHaste + 1 : 0);
            this.api.sendActionbar(ev.getPlayer(), "§3Total Broken: §b" + SBMain.totalGlobalCobblestoneBroken);
            if (this.applyDamage(ev.getPlayer().getItemInHand(), 1, ev.getPlayer())) {
                ev.getPlayer().getInventory().remove(ev.getPlayer().getItemInHand());
            }

            if (ev.getBlock().getType().equals(Material.CHEST) && !ev.getPlayer().isSneaking()) { /* sneaking check because if you don't want the items you can just empty the chest */
                Chest ch = (Chest) ev.getBlock().getState();
                for (ItemStack in : ch.getBlockInventory().getContents()) {
                    if (in == null) continue;
                    ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
                }
            }

            boolean b = false;
            if (ev.getPlayer().getInventory().firstEmpty() == -1) b = true;

            final int gokewasgjkan = MathAndRNG.generateInteger(2000);
            if (gokewasgjkan == 69) {
                FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.OVERWORLD_NON_DANGER);
                ev.getPlayer().sendMessage("§eVocê ganhou uma farm de Animais do Overworld! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
            }

            final int gijgwewyt = MathAndRNG.generateInteger(3000);
            if (gijgwewyt == 69) {
                FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.OVERWORLD);
                ev.getPlayer().sendMessage("§eVocê ganhou uma farm de Mobs do Overworld! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
            }

            // changed so it ain't

            if (SBMain.phase > 2) {
                final int asgewhwh = MathAndRNG.generateInteger(4200);
                if (asgewhwh == 69) {
                    FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.NETHER);
                    ev.getPlayer().sendMessage("§eVocê ganhou uma farm de Mobs do Nether! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
                }

                final int gjnajnnethe = MathAndRNG.generateInteger(5000);
                if (gjnajnnethe == 69) {
                    FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.ALL);
                    ev.getPlayer().sendMessage("§eVocê ganhou uma farm de Todos os Mobs! (Muito Rara) " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
                    Bukkit.getOnlinePlayers().forEach(pl -> this.api.sendTitles(pl, "§a§lDROP RARO", "§fO jogador " + ev.getPlayer().getName() + " pegou uma farm rara!"));
                    ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
                }
            }

            if (SBMain.phase > 3) {
                final int hkjtyk = MathAndRNG.generateInteger(6250);
                if (hkjtyk == 69) {
                    FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.ALL_NON_DANGER);
                    ev.getPlayer().sendMessage("§eVocê ganhou uma farm de Todos os Mobs e Animais! (Extremamente Rara) " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
                    Bukkit.getOnlinePlayers().forEach(pl -> this.api.sendTitles(pl, "§6§lDROP MÍTICO", "§fO jogador " + ev.getPlayer().getName() + " pegou uma farm MUITO RARA!"));
                    ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
                }
            }


            final int cobblePu = MathAndRNG.generateInteger(1000);
            if (cobblePu == 69) {
                if (!b) ev.getPlayer().getInventory().addItem(BoosterItem.returnItemStack(2.f));
                else ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), BoosterItem.returnItemStack(2.f));
                ev.getPlayer().sendMessage("§eVocê pegou um booster de 2x cobble! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
            }else {
                final int pu = MathAndRNG.generateInteger(2000);
                if (pu == 69) {
                    if (!b) ev.getPlayer().getInventory().addItem(BoosterItem.returnItemStack(3.f));
                    else ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), BoosterItem.returnItemStack(3.f));
                    ev.getPlayer().sendMessage("§eVocê pegou um booster de 3x cobble! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
                }
            }

            final int hastePu = MathAndRNG.generateInteger(850);
            if (hastePu == 69) {
                if (!b) ev.getPlayer().getInventory().addItem(HasteBoostItem.returnItemStack(2.f));
                else ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), HasteBoostItem.returnItemStack(2.f));
                ev.getPlayer().sendMessage("§eVocê pegou um booster de 2x mining speed! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
            }else {
                final int pu = MathAndRNG.generateInteger(1540);
                if (pu == 69) {
                    if (!b) ev.getPlayer().getInventory().addItem(HasteBoostItem.returnItemStack(3.f));
                    else ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), HasteBoostItem.returnItemStack(3.f));
                    ev.getPlayer().sendMessage("§eVocê pegou um booster de 3x mining speed! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
                }
            }

            final int flightPu = MathAndRNG.generateInteger(520);
            if (flightPu == 69) {
                if (!b) ev.getPlayer().getInventory().addItem(FlightBoostItem.returnItemStack());
                else ev.getPlayer().getWorld().dropItemNaturally(ev.getBlock().getLocation(), FlightBoostItem.returnItemStack());
                ev.getPlayer().sendMessage("§eVocê pegou um booster de voar! " + (b ? "§2Ele foi dropado pois seu inventário está cheio!" : "§aOlhe seu inventário!"));
            }

            if (SBMain.totalGlobalCobblestoneBroken <= SBConfig.blocksBrokenPhase2) {
                runPhase1(ev);
            }
            else if (SBMain.totalGlobalCobblestoneBroken <= SBConfig.blocksBrokenPhase3) {
                if (SBMain.phase == 1) {
                    ++SBMain.phase;
                    Bukkit.getPluginManager().callEvent(new PhaseUpdateEvent(SBMain.phase, SBMain.totalGlobalCobblestoneBroken));
                    ev.getBlock().setType(Material.CHEST);
                    this.hasChangedLastTime = true;
                    this.setChests.lootChestP2(ev.getBlock(), MathAndRNG.generateInteger(3));
                    return;
                }
                runPhase2(ev);
            }
            else if (SBMain.totalGlobalCobblestoneBroken <= SBConfig.blocksBrokenPhase4) {
                if (SBMain.phase == 2) {
                    ++SBMain.phase;
                    Bukkit.getPluginManager().callEvent(new PhaseUpdateEvent(SBMain.phase, SBMain.totalGlobalCobblestoneBroken));
                    ev.getBlock().setType(Material.CHEST);
                    this.hasChangedLastTime = true;
                    this.setChests.lootChestP3(ev.getBlock(), MathAndRNG.generateInteger(3));
                    return;
                }
                runPhase3(ev);
            }
            else if (SBMain.totalGlobalCobblestoneBroken <= SBConfig.blocksBrokenPhase5) {
                if (SBMain.phase == 3) {
                    ++SBMain.phase;
                    Bukkit.getPluginManager().callEvent(new PhaseUpdateEvent(SBMain.phase, SBMain.totalGlobalCobblestoneBroken));
                    ev.getBlock().setType(Material.CHEST);
                    this.hasChangedLastTime = true;
                    this.setChests.lootChestP4(ev.getBlock(), MathAndRNG.generateInteger(3));
                    return;
                }
                runPhase4(ev);
            }
            else if (SBMain.totalGlobalCobblestoneBroken <= SBConfig.blocksBrokenPhase6) {
                if (SBMain.phase == 4) {
                    ++SBMain.phase;
                    Bukkit.getPluginManager().callEvent(new PhaseUpdateEvent(SBMain.phase, SBMain.totalGlobalCobblestoneBroken));
                    ev.getBlock().setType(Material.CHEST);
                    this.hasChangedLastTime = true;
                    this.setChests.lootChestP5(ev.getBlock(), MathAndRNG.generateInteger(3));
                    return;
                }
                runPhase5(ev);
            }
            else {
                final int phaseSelect = MathAndRNG.generateInteger(5);
                if (SBMain.phase == 5) {
                    ++SBMain.phase;
                    Bukkit.getPluginManager().callEvent(new PhaseUpdateEvent(SBMain.phase, SBMain.totalGlobalCobblestoneBroken));
                    ev.getBlock().setType(Material.CHEST);
                    this.hasChangedLastTime = true;
                    switch (phaseSelect) {
                        case 1:
                            this.setChests.lootChestP1(ev.getBlock(), MathAndRNG.generateInteger(3));
                            break;
                        case 2:
                            this.setChests.lootChestP2(ev.getBlock(), MathAndRNG.generateInteger(3));
                            break;
                        case 3:
                            this.setChests.lootChestP3(ev.getBlock(), MathAndRNG.generateInteger(3));
                            break;
                        case 4:
                            this.setChests.lootChestP4(ev.getBlock(), MathAndRNG.generateInteger(3));
                            break;
                        case 5:
                            this.setChests.lootChestP5(ev.getBlock(), MathAndRNG.generateInteger(3));
                            break;
                    }
                    return;
                }
                switch (phaseSelect) {
                    case 1:
                        runPhase1(ev);
                        break;
                    case 2:
                        runPhase2(ev);
                        break;
                    case 3:
                        runPhase3(ev);
                        break;
                    case 4:
                        runPhase4(ev);
                        break;
                    case 5:
                        runPhase5(ev);
                        break;
                }
            }
        }
    }

    private void runPhase1(final BlockBreakEvent ev) {
        for (final ItemStack in : ev.getBlock().getDrops()) {
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
        }
        if (!this.hasChangedLastTime) {
            final int i = MathAndRNG.generateInteger(40);
            if (i == 24) {
                ev.getBlock().setType(Material.CHEST);
                this.hasChangedLastTime = true;
                this.setChests.lootChestP1(ev.getBlock(), MathAndRNG.generateInteger(3));
                return;
            }
        }
        final int I = MathAndRNG.generateInteger(30);
        if (I == 1) {
            ev.getBlock().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), this.p1Entities[MathAndRNG.generateInteger(this.p1Entities.length - 1)]);
        }
        if (!this.hasChangedLastTime) {
            final int j = MathAndRNG.generateInteger(3);
            if (j == 1) {
                this.hasChangedLastTime = true;
                ev.getBlock().setType(this.p1Blocks[MathAndRNG.generateInteger(this.p1Blocks.length - 1)]);
            }
        }
        else {
            boolean b = true;
            if (MathAndRNG.generateInteger(5) < 3 || failed > 3 || delayIfUnlucky > 0 || ev.getBlock().getType().name().toLowerCase().contains("chest") || isOre(ev.getBlock().getType())) {
                ev.getBlock().setType(Material.COBBLESTONE);
                b = false;
                if (delayIfUnlucky > 0) delayIfUnlucky--;
                if (failed > 3) delayIfUnlucky = MathAndRNG.generateInteger(12);
                failed = 0;
            }else failed++;
            if (!b) this.hasChangedLastTime = false;
        }
    }

    public boolean isOre(final Material m) {
        if (m.name().contains("ORE")) return true;
        else {
           switch (m) {
               case IRON_BLOCK:
               case RAW_IRON_BLOCK:
               case DIAMOND_BLOCK:
               case GOLD_BLOCK:
               case RAW_GOLD_BLOCK:
               case LAPIS_BLOCK:
               case REDSTONE_BLOCK:
               case RAW_COPPER_BLOCK:
               case COPPER_BLOCK:
               case WAXED_COPPER_BLOCK:
                   return true;
           }
        }

        return false;
    }

    private void runPhase2(final BlockBreakEvent ev) {
        for (final ItemStack in : ev.getBlock().getDrops()) {
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
        }
        if (!this.hasChangedLastTime) {
            final int i = MathAndRNG.generateInteger(40);
            if (i == 24) {
                ev.getBlock().setType(Material.CHEST);
                this.hasChangedLastTime = true;
                this.setChests.lootChestP2(ev.getBlock(), MathAndRNG.generateInteger(3));
                return;
            }
        }
        final int I = MathAndRNG.generateInteger(25);
        if (I == 1) {
            ev.getBlock().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), this.p2Entities[MathAndRNG.generateInteger(this.p2Entities.length - 1)]);
        }
        if (!this.hasChangedLastTime) {
            final int j = MathAndRNG.generateInteger(3);
            if (j == 1) {
                this.hasChangedLastTime = true;
                ev.getBlock().setType(this.p2Blocks[MathAndRNG.generateInteger(this.p2Blocks.length - 1)]);
            }
        }
        else {
            boolean b = true;
            if (MathAndRNG.generateInteger(5) < 3 || failed > 3 || delayIfUnlucky > 0 || ev.getBlock().getType().name().toLowerCase().contains("chest") || isOre(ev.getBlock().getType())) {
                ev.getBlock().setType(Material.COBBLESTONE);
                b = false;
                if (delayIfUnlucky > 0) delayIfUnlucky--;
                if (failed > 3) delayIfUnlucky = MathAndRNG.generateInteger(12);
                failed = 0;
            }else failed++;
            if (!b) this.hasChangedLastTime = false;
        }
    }

    private void runPhase3(final BlockBreakEvent ev) {
        for (final ItemStack in : ev.getBlock().getDrops()) {
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
        }
        if (!this.hasChangedLastTime) {
            final int i = MathAndRNG.generateInteger(40);
            if (i == 24) {
                ev.getBlock().setType(Material.CHEST);
                this.hasChangedLastTime = true;
                this.setChests.lootChestP3(ev.getBlock(), MathAndRNG.generateInteger(3));
                return;
            }
        }
        final int I = MathAndRNG.generateInteger(20);
        if (I == 1) {
            ev.getBlock().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), this.p3Entities[MathAndRNG.generateInteger(this.p3Entities.length - 1)]);
        }
        if (!this.hasChangedLastTime) {
            final int j = MathAndRNG.generateInteger(3);
            if (j == 1) {
                this.hasChangedLastTime = true;
                ev.getBlock().setType(this.p3Blocks[MathAndRNG.generateInteger(this.p3Blocks.length - 1)]);
            }
        }
        else {
            boolean b = true;
            if (MathAndRNG.generateInteger(5) < 3 || failed > 3 || delayIfUnlucky > 0 || ev.getBlock().getType().name().toLowerCase().contains("chest") || isOre(ev.getBlock().getType())) {
                ev.getBlock().setType(Material.COBBLESTONE);
                b = false;
            }
            if (!b) this.hasChangedLastTime = false;
        }
    }

    private void runPhase4(final BlockBreakEvent ev) {
        for (final ItemStack in : ev.getBlock().getDrops()) {
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
        }
        if (!this.hasChangedLastTime) {
            final int i = MathAndRNG.generateInteger(40);
            if (i == 24) {
                ev.getBlock().setType(Material.CHEST);
                this.hasChangedLastTime = true;
                this.setChests.lootChestP4(ev.getBlock(), MathAndRNG.generateInteger(3));
                return;
            }
        }
        final int I = MathAndRNG.generateInteger(15);
        if (I == 1) {
            ev.getBlock().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), this.p4Entities[MathAndRNG.generateInteger(this.p4Entities.length - 1)]);
        }
        if (!this.hasChangedLastTime) {
            final int j = MathAndRNG.generateInteger(3);
            if (j == 1) {
                this.hasChangedLastTime = true;
                ev.getBlock().setType(this.p4Blocks[MathAndRNG.generateInteger(this.p4Blocks.length - 1)]);
            }
        }
        else {
            boolean b = true;
            if (MathAndRNG.generateInteger(5) < 3 || failed > 3 || delayIfUnlucky > 0 || ev.getBlock().getType().name().toLowerCase().contains("chest") || isOre(ev.getBlock().getType())) {
                ev.getBlock().setType(Material.COBBLESTONE);
                b = false;
                if (delayIfUnlucky > 0) delayIfUnlucky--;
                if (failed > 3) delayIfUnlucky = MathAndRNG.generateInteger(12);
                failed = 0;
            }else failed++;
            if (!b) this.hasChangedLastTime = false;
        }
    }

    private void runPhase5(final BlockBreakEvent ev) {
        for (final ItemStack in : ev.getBlock().getDrops()) {
            ev.getBlock().getWorld().dropItemNaturally(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), in);
        }
        if (!this.hasChangedLastTime) {
            final int i = MathAndRNG.generateInteger(40);
            if (i == 24) {
                ev.getBlock().setType(Material.CHEST);
                this.hasChangedLastTime = true;
                this.setChests.lootChestP5(ev.getBlock(), MathAndRNG.generateInteger(3));
                return;
            }
        }
        final int I = MathAndRNG.generateInteger(35);
        if (I == 1) {
            ev.getBlock().getWorld().spawnEntity(ev.getBlock().getLocation().add(0.0, 1.0, 0.0), this.p5Entities[MathAndRNG.generateInteger(this.p5Entities.length - 1)]);
        }
        if (!this.hasChangedLastTime) {
            final int j = MathAndRNG.generateInteger(3);
            if (j == 1) {
                this.hasChangedLastTime = true;
                ev.getBlock().setType(this.p5Blocks[MathAndRNG.generateInteger(this.p5Blocks.length - 1)]);
            }
        }
        else {
            boolean b = true;
            if (MathAndRNG.generateInteger(5) < 3 || failed > 3 || delayIfUnlucky > 0 || ev.getBlock().getType().name().toLowerCase().contains("chest") || isOre(ev.getBlock().getType())) {
                ev.getBlock().setType(Material.COBBLESTONE);
                b = false;
                if (delayIfUnlucky > 0) delayIfUnlucky--;
                if (failed > 3) delayIfUnlucky = MathAndRNG.generateInteger(12);
                failed = 0;
            }else failed++;
            if (!b) this.hasChangedLastTime = false;
        }
    }
    
    public boolean applyDamage(final ItemStack is, final int damage, final Player soundEffecter) {
        final ItemMeta im = is.getItemMeta();
        if (!(im instanceof Damageable)) {
            return false;
        }

        final int random = MathAndRNG.generateInteger(5);

        final boolean shouldDamage = (is.getEnchantments().containsKey(Enchantment.DURABILITY) ? (random < (4 - (is.getEnchantments().get(Enchantment.DURABILITY) - 1))) : random != 1); // UNBREAKING 3 WILL BE 1/5 DAMAGES; UB 2 2/5, UB 1 3/5, NO UB 4/5.

        if (!shouldDamage) return false;

        final String typeName = is.getType().name().toUpperCase();
        if (!typeName.contains("SWORD") &&
                !typeName.contains("PICKAXE") &&
                !typeName.contains("AXE") &&
                !typeName.contains("SHOVEL") &&
                !typeName.contains("HOE")) return false;

        final Damageable itemdmg = (Damageable)im;
        itemdmg.setDamage(itemdmg.getDamage() + damage);
        is.setItemMeta((ItemMeta)itemdmg);
        if (is.getType().getMaxDurability() <= itemdmg.getDamage()) {
            soundEffecter.playSound(soundEffecter.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            soundEffecter.sendMessage("§7Sua ferramenta quebrou! Recoloque-a se não estiver AFK.");
            is.setAmount(is.getAmount() - 1);
            return true;
        }
        return false;
    }
}
