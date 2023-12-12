package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.KillFarmMobEvent;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobKillSpree extends Achievement {
    @Override
    public String name() {
        return "Avançando em farm";
    }

    @Override
    public String description() {
        return "Mate 300 mobs de farm.";
    }

    @Override
    public String reward() {
        return "1x Booster de Voar";
    }

    @Override
    public String id() {
        return "asb_a_farm_kill_1b";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, FlightBoostItem.returnItemStack());
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.STONE_SWORD;
    }

    @EventHandler
    public void onKill(final KillFarmMobEvent ev) {
        if (ev.getPlayer() != null) {
            final Player player = ev.getPlayer();
            double progress;
            progress = AchievementAPI.getAchievementInfo(this, player).progress+= ((double) 1 / 300);
            if (progress >= 1 && !AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }
}
