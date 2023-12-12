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

public class MobGenocide extends Achievement {
    @Override
    public String name() {
        return "Genocidio de Mob de Farm";
    }

    @Override
    public String description() {
        return "Mate 600 mobs de farm.";
    }

    @Override
    public String reward() {
        return "1x Poção de Força, 1x Booster de Voar";
    }

    @Override
    public String id() {
        return "asb_a_farm_kill_2";
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
        return Material.IRON_SWORD;
    }

    @Override
    public void onComplete(Player player) {
        final ItemStack potion = new ItemStack(Material.POTION);
        final PotionMeta pm = (PotionMeta) potion.getItemMeta();

        pm.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000, 4), true);
        pm.setColor(Color.AQUA);
        pm.setDisplayName("§aPoção de Força Especial");

        potion.setItemMeta(pm);

        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, potion);
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, FlightBoostItem.returnItemStack());
        this.conclude(player);
    }

    @EventHandler
    public void onKill(final KillFarmMobEvent ev) {
        if (ev.getPlayer() != null) {
            final Player player = ev.getPlayer();
            double progress;
            progress = AchievementAPI.getAchievementInfo(this, player).progress+= ((double) 1 / 600);
            if (progress >= 1 && !AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }
}
