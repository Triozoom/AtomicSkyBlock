package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmerAch extends Achievement {
    @Override
    public String name() {
        return "Fazendeiro";
    }

    @Override
    public String description() {
        return "Começe a colheita de plantações.";
    }

    @Override
    public String reward() {
        return "1x Kit de Fazendeiro";
    }

    @Override
    public String id() {
        return "asb_a_8";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.WATER_BUCKET, 2));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.IRON_HOE, 1));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.WHEAT_SEEDS, 32));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.CARROT, 32));
        this.conclude(player);
    }

    @EventHandler
    public void obc(final BlockBreakEvent ev) {
        if (isCrop(ev.getBlock().getType())) {
            final Player player = ev.getPlayer();
            if (!AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }

    private boolean isCrop(final Material m) {
        switch (m) {
            case WHEAT:
            case TALL_GRASS:
            case GRASS:
            case CARROTS:
            case CARROT:
            case BEETROOTS:
            case BEETROOT:
            case PUMPKIN:
            case PUMPKIN_STEM:
            case CARVED_PUMPKIN:
            case ATTACHED_PUMPKIN_STEM:
            case MELON_STEM:
            case MELON:
            case CACTUS:
                return true;
        }
        return false;
    }
}
