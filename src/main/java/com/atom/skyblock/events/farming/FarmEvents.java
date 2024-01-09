package com.atom.skyblock.events.farming;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.CraftFarmEvent;
import com.atom.skyblock.api.KillFarmMobEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.data.PlayerData;
import com.atom.skyblock.farms.Farm;
import com.atom.skyblock.farms.FarmManager;
import com.atom.skyblock.farms.items.FarmItemManager;
import com.atom.skyblock.powerups.impl.BoosterItem;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import dev.joel.bukkitutil.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FarmEvents implements Listener {

    private final ArrayList<UUID> dumbasses = new ArrayList<>();

    @EventHandler
    public void onBreak(final BlockBreakEvent ev) {
        if (FarmItemManager.isValidFarmMaterial(ev.getBlock().getType())) {
            if (dumbasses.contains(ev.getPlayer().getUniqueId())) {
                ev.setCancelled(true);
                ev.getPlayer().sendMessage("§cVocê está em cooldown. Tente novamente depois.");
                return;
            }
            // BukkitUtil.send("pass0");
            final PlayerData data = DataManager.get(ev.getPlayer());
            for (final Farm farmIn : FarmManager.allFarms) {
                // BukkitUtil.send("loop");
                // BukkitUtil.send("fowuuid=" + farmIn.owner.getUniqueId() + "; puuid=" + ev.getPlayer().getUniqueId());
                if (isFarm(ev.getBlock(), farmIn)) {
                    // BukkitUtil.send("pass1");
                    final boolean equal = farmIn.owner.getUniqueId().toString().equals(ev.getPlayer().getUniqueId().toString());
                    // BukkitUtil.send("equal=" + equal);
                    if (equal || data.ownedFarms.contains(farmIn)) {
                        FarmManager.removeFarm(ev.getPlayer(), farmIn);
                        FarmItemManager.give(farmIn, ev.getPlayer());
                        ev.setCancelled(true);
                        ev.getBlock().setType(Material.AIR);
                        ev.getPlayer().sendMessage("§cVocê tirou uma farm sua do chão.");
                        ev.getPlayer().playSound(ev.getBlock().getLocation(), Sound.BLOCK_BAMBOO_BREAK, 1F, 1F);
                    } else {
                        ev.getPlayer().sendMessage("§cEssa farm não é sua. Apenas o dono pode a retirar.");
                        ev.setCancelled(true);
                        dumbasses.add(ev.getPlayer().getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                dumbasses.remove(ev.getPlayer().getUniqueId());
                            }
                        }.runTaskLaterAsynchronously(SBMain.INSTANCE, 100L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent ev) {
        if (FarmItemManager.isValidFarmMaterial(ev.getBlock().getType())) {
            if (ev.getItemInHand().getItemMeta() != null &&
                    ev.getItemInHand().getItemMeta().getDisplayName().startsWith("§c§lFARM")) {
                ev.getBlock().setType(ev.getBlock().getType());

                final Farm.EntitiesType type = FarmItemManager.fromItem(ev.getItemInHand());

                FarmManager.addFarm(ev.getPlayer(), new Farm(ev.getBlock().getLocation(), type, ev.getPlayer()));
                ev.getItemInHand().setAmount(ev.getItemInHand().getAmount() - 1);
                ev.getPlayer().sendMessage("§aVocê colocou uma Farm no chão.");
                ev.getPlayer().playSound(ev.getBlock().getLocation(), Sound.BLOCK_BAMBOO_PLACE, 1F, 1F);
            }
        }
    }

    @EventHandler
    public void onKillEntity(final EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            final LivingEntity le = event.getEntity();
            if (le.getCustomName() != null && le.getCustomName().startsWith("_farmmob")) {
                for (final Farm farmIn : FarmManager.allFarms) {
                    if (farmIn.lastEntity != null && farmIn.lastEntity == le) {
                        Bukkit.getPluginManager().callEvent(new KillFarmMobEvent(le.getKiller(), le, farmIn));
                        farmIn.mobSpawned = false;
                    }
                }
            }
        }
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e)
    {
        if(!e.getRightClicked().isVisible())
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItems(final PlayerDropItemEvent ev) {
        final List<Entity> entities = ev.getItemDrop().getNearbyEntities(3, 3, 3)
                .stream().filter(entity -> entity instanceof Item).collect(Collectors.toList());
        entities.addAll(ev.getPlayer().getNearbyEntities(3, 3, 3).stream().filter(entity -> entity instanceof Item).collect(Collectors.toList()));
        entities.add(ev.getItemDrop());
        final boolean valid_1 = isOwndValid(entities, 1);
        final boolean valid_2 = isOwndValid(entities, 2);
        final boolean valid_3 = isOwndValid(entities, 3);
        final boolean valid_4 = isOwndValid(entities, 4);
        final boolean valid_5 = isOwndValid(entities, 5);
        if (valid_1) {
            final CraftFarmEvent event = new CraftFarmEvent(ev.getPlayer(), Farm.EntitiesType.OVERWORLD_NON_DANGER);
            Bukkit.getPluginManager().callEvent(event);
            entities.forEach(Entity::remove);
            FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.OVERWORLD_NON_DANGER);
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F);
            ev.getPlayer().sendMessage("§aVocê craftou uma farm de animais do Overworld!");
        }else if (valid_2) {
            final CraftFarmEvent event = new CraftFarmEvent(ev.getPlayer(), Farm.EntitiesType.OVERWORLD);
            Bukkit.getPluginManager().callEvent(event);
            entities.forEach(Entity::remove);
            FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.OVERWORLD);
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F);
            ev.getPlayer().sendMessage("§aVocê craftou uma farm de mobs do Overworld!");
        }else if (valid_3) {
            final CraftFarmEvent event = new CraftFarmEvent(ev.getPlayer(), Farm.EntitiesType.NETHER);
            Bukkit.getPluginManager().callEvent(event);
            entities.forEach(Entity::remove);
            FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.NETHER);
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F);
            ev.getPlayer().sendMessage("§aVocê craftou uma farm de mobs do Nether!");
        }else if (valid_4) {
            final CraftFarmEvent event = new CraftFarmEvent(ev.getPlayer(), Farm.EntitiesType.ALL);
            Bukkit.getPluginManager().callEvent(event);
            entities.forEach(Entity::remove);
            FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.ALL);
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F);
            ev.getPlayer().sendMessage("§aVocê craftou uma farm de TODOS OS MOBS!");
            Bukkit.getOnlinePlayers().forEach(pl -> pl.sendTitle("§a§lFARM ÉPICA CRIADA", "§fO jogador " + ev.getPlayer().getName() + " criou uma farm rara!"));
        }else if (valid_5) {
            final CraftFarmEvent event = new CraftFarmEvent(ev.getPlayer(), Farm.EntitiesType.ALL_NON_DANGER);
            Bukkit.getPluginManager().callEvent(event);
            entities.forEach(Entity::remove);
            FarmItemManager.give(ev.getPlayer(), Farm.EntitiesType.ALL_NON_DANGER);
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F);
            ev.getPlayer().sendMessage("§aVocê craftou uma farm de TODOS OS MOBS *E ANIMAIS*!");
            Bukkit.getOnlinePlayers().forEach(pl -> pl.sendTitle("§6§lFARM MÍTICA CRIADA", "§fO jogador " + ev.getPlayer().getName() + " criou uma farm MUITO RARA!"));
        }
    }

    private static boolean isOwndValid(List<Entity> entities, final int type) {
        switch (type) {
            case 1:
                boolean flint = false, iron = false, sword = false; // 1 iron sword, 30 iron ingots, 12 flint
                for (final Entity ient : entities) {
                    final Item item = (Item) ient;
                    if (item.getItemStack().getType() == Material.FLINT && item.getItemStack().getAmount() >= 12)
                        flint = true;
                    if (item.getItemStack().getType() == Material.IRON_INGOT && item.getItemStack().getAmount() >= 30)
                        iron = true;
                    if (item.getItemStack().getType() == Material.IRON_SWORD) sword = true;
                }

                // BukkitUtil.send("f=" + flint + "; i=" + iron + "; s=" + sword);

                return flint && iron && sword;
            case 2:
                boolean copper = false, irons = false, diamond = false, swords = false; // 1 iron sword, 30 iron ingots, 12 flint
                for (final Entity ient : entities) {
                    final Item item = (Item) ient;
                    if (item.getItemStack().getType() == Material.COPPER_INGOT && item.getItemStack().getAmount() >= 12)
                        copper = true;
                    if (item.getItemStack().getType() == Material.IRON_INGOT && item.getItemStack().getAmount() >= 42)
                        irons = true;
                    if (item.getItemStack().getType() == Material.DIAMOND && item.getItemStack().getAmount() >= 5)
                        diamond = true;
                    if (item.getItemStack().getType() == Material.IRON_SWORD) swords = true;
                }

                // BukkitUtil.send("f=" + flint + "; i=" + iron + "; s=" + sword);

                return copper && irons && swords && diamond;
            case 3:
                boolean rods = false, skeleton_head = false, golds = false;
                for (final Entity ient : entities) {
                    final Item item = (Item) ient;
                    if (item.getItemStack().getType() == Material.GOLD_INGOT && item.getItemStack().getAmount() >= 12)
                        golds = true;
                    if (item.getItemStack().getType() == Material.OBSIDIAN && item.getItemStack().getAmount() >= 18)
                        rods = true;
                    if (item.getItemStack().getType() == Material.WITHER_SKELETON_SKULL) skeleton_head = true;
                }
                return golds && skeleton_head && rods;
            case 4:
                boolean netherite = false, skel_head = false, diamonde = false;
                for (final Entity ient : entities) {
                    final Item item = (Item) ient;
                    if (item.getItemStack().getType() == Material.NETHERITE_INGOT && item.getItemStack().getAmount() >= 10)
                        netherite = true;
                    if (item.getItemStack().getType() == Material.DIAMOND && item.getItemStack().getAmount() >= 24)
                        diamonde = true;
                    if (item.getItemStack().getType() == Material.WITHER_SKELETON_SKULL && item.getItemStack().getAmount() >= 2)
                        skel_head = true;
                }
                return netherite && skel_head && diamonde;
            case 5:
                boolean nblock = false, dragon_head = false, dblock = false;
                for (final Entity ient : entities) {
                    final Item item = (Item) ient;
                    if (item.getItemStack().getType() == Material.NETHERITE_BLOCK && item.getItemStack().getAmount() >= 7)
                        nblock = true;
                    if (item.getItemStack().getType() == Material.DIAMOND_BLOCK && item.getItemStack().getAmount() >= 10)
                        dblock = true;
                    if (item.getItemStack().getType() == Material.DRAGON_HEAD && item.getItemStack().getAmount() >= 2)
                        dragon_head = true;
                }
                return nblock && dblock && dragon_head;
        }
        return false;
    }

    private static boolean isFarm(final Block supposedFarm, final Farm farm) {
        final double X = supposedFarm.getX(), Y = supposedFarm.getY(), Z = supposedFarm.getZ();
        if (supposedFarm.getWorld().equals(farm.location.getWorld())) {
            //BukkitUtil.send("a");
            if (farm.location.getBlockX() == X && farm.location.getBlockY() == Y && farm.location.getBlockZ() == Z) {
                // BukkitUtil.send("b");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onKill(final KillFarmMobEvent ev) {
        final int importance = importanceOf(ev.getDead());

        // ev.getPlayer().sendMessage("i: " + importance);
        if (importance == 20) {
            rollBooster(ev);
            return;
        }

        if (importance == 10) {
            final int newRandom = MathAndRNG.generateInteger(10);
            if (newRandom == 4) {
                rollBooster(ev);
            } else if (newRandom == 5) {
                rollOre(ev);
            }
            return;
        }

        /*
            This later since if importance is too big it will go out of bounds.
            Which is reasonable, right?
         */

        final int random = MathAndRNG.generateInteger(290 - (importance * (importance > 3 ? 30 : 18)));

        if (random == 5) {
            rollBooster(ev);
        } else {
            if (random < 35 && random > 20) {
                rollOre(ev);
            }
        }
    }

    private void rollBooster(final KillFarmMobEvent ev) {
        final int which = MathAndRNG.generateInteger(3);
        final boolean isGood = MathAndRNG.generateInteger(24) == 10;
        switch (which) {
            case 1:
                ev.getFarm().location.getWorld().dropItemNaturally(ev.getFarm().location.clone().add(0, 1.5, 0), BoosterItem.returnItemStack(isGood ? 3.F : 2.F));
                break;
            case 2:
                ev.getFarm().location.getWorld().dropItemNaturally(ev.getFarm().location.clone().add(0, 1.5, 0), HasteBoostItem.returnItemStack(isGood ? 3.F : 2.F));
                break;
            case 3:
                ev.getFarm().location.getWorld().dropItemNaturally(ev.getFarm().location.clone().add(0, 1.5, 0), FlightBoostItem.returnItemStack());
                if (isGood) {
                    ev.getFarm().location.getWorld().dropItemNaturally(ev.getFarm().location.clone().add(0, 1.5, 0), BoosterItem.returnItemStack(1.5F));
                }
                break;
        }

        if (which < 3) {
            ev.getPlayer().sendTitle("§2§lDROP DE FARM", "§fVocê ganhou um booster de " + (which == 1 ? "Cobblestone" : "Haste") + " de " + (isGood ? "3x" : "2x"));
        }else ev.getPlayer().sendTitle("§2§lDROP DE FARM", "§fVocê ganhou um booster de voar" + (isGood ? " e de cobblestone! (1.5x)" : "!"));
        ev.getPlayer().sendMessage("§e(O drop está em cima da farm!)");
        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
    }

    private void rollOre(final KillFarmMobEvent ev) {
        final List<Material> materials = new ArrayList<>(Arrays.asList(Material.values())).stream().filter(material -> {
            switch (material) {
                case DIAMOND:
                case DIAMOND_BLOCK:
                case GOLD_BLOCK:
                case COAL:
                case IRON_BLOCK:
                case NETHERITE_BLOCK:
                    return true;
            }
            return material.name().contains("INGOT");
        }).collect(Collectors.toList());
        final Material m = materials.get(MathAndRNG.generateInteger(materials.size() - 1));
        ev.getFarm().location.getWorld().dropItemNaturally(ev.getFarm().location.clone().add(0, 1.5, 0), new ItemStack(m, MathAndRNG.generateInteger(m.name().contains("BLOCK") ? 2 : 10)));
        ev.getPlayer().sendTitle("§2§lDROP DE FARM", "§fAlguns minérios droparam na farm!");
        ev.getPlayer().sendMessage("§e(Drop! Um minério dropou na sua farm!)");
        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
    }

    public int importanceOf(final LivingEntity entity) {
        switch (entity.getType()) {
            case GIANT:
            case ELDER_GUARDIAN:
            case GHAST:
                return 10;
            case WITHER:
                return 20;
            default:
                if (entity.getMaxHealth() < 20) {
                    return 1;
                }else if (entity.getMaxHealth() < 24) {
                    return 2;
                }else if (entity.getMaxHealth() < 30) {
                    return 4;
                }else {
                    return 5;
                }
        }
    }
}
