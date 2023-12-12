package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Lumberjack extends Achievement {
    @Override
    public String name() {
        return "Lenhador";
    }

    @Override
    public String description() {
        return "Quebre uma arvore.";
    }

    @Override
    public String reward() {
        return "10x Sapling de Arvore";
    }

    @Override
    public String id() {
        return "asb_a_9";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.OAK_SAPLING, 2));
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.IRON_AXE;
    }

    @EventHandler
    public void obc(final BlockBreakEvent ev) {
        if (isWood(ev.getBlock().getType())) {
            final Player player = ev.getPlayer();
            if (!AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }

    private boolean isWood(final Material m) {
        return m.name().contains("LOG");
    }
}
