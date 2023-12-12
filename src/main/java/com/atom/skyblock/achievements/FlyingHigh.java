package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.PhaseUpdateEvent;
import com.atom.skyblock.api.VoidDeathEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyingHigh extends Achievement {
    @Override
    public String name() {
        return "Voando Alto";
    }

    @Override
    public String description() {
        return "Chegue às After Phases (Fase 6).";
    }

    @Override
    public String reward() {
        return "3x Olho do Fim e 2 Netherite";
    }

    @Override
    public String id() {
        return "asb_a_fh";
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
        return Material.ENDER_CHEST;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.ENDER_EYE, 3));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.NETHERITE_INGOT, 2));
        this.conclude(player);
    }

    @EventHandler
    public void onFlyHigh(final PhaseUpdateEvent ev) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            AchievementAPI.getAchievementInfo(this, player).progress = (double) ev.getNewPhase() / 6;
            if (AchievementAPI.getAchievementInfo(this, player).progress >= 1) {
                if (!AchievementAPI.hasCompleted(player, this)) {
                    this.onComplete(player);
                }
            }
        }
    }
}
