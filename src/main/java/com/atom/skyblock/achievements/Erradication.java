package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.KillFarmMobEvent;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Erradication extends Achievement {
    @Override
    public String name() {
        return "Erradicação";
    }

    @Override
    public String description() {
        return "Mate 7.500 mobs de farm.";
    }

    @Override
    public String reward() {
        return "1x Booster de Cobble (5x) e 2x Bloco de Netherite";
    }

    @Override
    public String id() {
        return "asb_a_farm_kill_erad";
    }

    @Override
    public JavaPlugin source() {
        return SBMain.INSTANCE;
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.NETHERITE_BLOCK, 2));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(5.F));
        for (final Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle("§3§lCONQUISTA LENDÁRIA FOI COMPLETA", "§fO jogador" + player.getName() + " completou uma conquista LENDÁRIA!");
            online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 3F, 0.65F);
            online.playSound(online.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3F, 1.3F);
        }
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.NETHERITE_SWORD;
    }

    @EventHandler
    public void onKill(final KillFarmMobEvent ev) {
        if (ev.getPlayer() != null) {
            final Player player = ev.getPlayer();
            double progress;
            progress = AchievementAPI.getAchievementInfo(this, player).progress+= ((double) 1 / 7500);
            if (progress >= 1 && !AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }
}
