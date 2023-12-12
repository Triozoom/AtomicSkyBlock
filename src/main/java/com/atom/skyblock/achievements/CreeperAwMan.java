package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreeperAwMan extends Achievement {
    @Override
    public String name() {
        return "Ufa, minhas coisas!";
    }

    @Override
    public String description() {
        return "Morra para um creeper, de forma que o plugin salve suas coisas.";
    }

    @Override
    public String reward() {
        return "60 Blocos";
    }

    @Override
    public String id() {
        return "asb_a_new_69";
    }

    @Override
    public JavaPlugin source() {
        return SBMain.INSTANCE;
    }

    @Override
    public boolean showTitle() {
        return true;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 60;
        this.conclude(player);
    }

    @EventHandler
    public void abc(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Creeper && event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if (event.getFinalDamage() >= player.getHealth()) {
                final List<ItemStack> isList = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (final ItemStack is : isList) {
                            SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, is);
                        }
                    }
                }.runTaskLater(SBMain.INSTANCE, 20L);
                if (!AchievementAPI.hasCompleted(player, this)) {
                    this.onComplete(player);
                }
            }
        }
    }

    @EventHandler
    public void exp(final EntityExplodeEvent ev) {
        if (ev.getEntity().getType() == EntityType.CREEPER) {
            ev.blockList().clear();
        }
    }
}
