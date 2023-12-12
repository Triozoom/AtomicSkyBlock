package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AchievementOne extends Achievement {
    @Override
    public String name() {
        return "Abridor de Baú de Loot!";
    }

    @Override
    public String description() {
        return "Ganha-se quando abre um Baú de Loot gerado pelo Bloco de Cobblestone.";
    }

    @Override
    public String reward() {
        return "2x Iron e 1x Enchada";
    }

    @Override
    public String id() {
        return "asb_a_1";
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
    public Material itemDisplay() {
        return Material.CHEST;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.IRON_ORE, 2));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.IRON_HOE, 1));
        this.conclude(player);
    }

    @EventHandler
    public void onOpenChest(final PlayerInteractEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ev.getClickedBlock() != null &&
                        ev.getClickedBlock().getType().name().contains("CHEST") &&
                        ev.getClickedBlock().getLocation().distance(SBMain.globalCobblestoneLocation) < 0.5D) {
                    this.onComplete(ev.getPlayer());
                }
            }
        }
    }
}
