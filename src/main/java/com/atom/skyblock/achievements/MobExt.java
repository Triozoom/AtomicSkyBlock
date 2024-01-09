package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.KillFarmMobEvent;
import com.atom.skyblock.powerups.impl.BoosterItem;
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

public class MobExt extends Achievement {
    @Override
    public String name() {
        return "Extinção de Espécies";
    }

    @Override
    public String description() {
        return "Mate 1.900 mobs de farm.";
    }

    @Override
    public String reward() {
        return "1x Poção de Resistencia e 1x Booster de Cobble (3x)";
    }

    @Override
    public String id() {
        return "asb_a_farm_kill_3";
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.VERY_RARE;
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
        return Material.DIAMOND_SWORD;
    }

    @Override
    public void onComplete(Player player) {
        final ItemStack potion = new ItemStack(Material.POTION);
        final PotionMeta pm = (PotionMeta) potion.getItemMeta();

        pm.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 3), true);
        pm.setColor(Color.LIME);
        pm.setDisplayName("§aPoção de Resistencia");

        potion.setItemMeta(pm);

        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, potion);
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(3.F));
        this.conclude(player);
    }

    @EventHandler
    public void onKill(final KillFarmMobEvent ev) {
        if (ev.getPlayer() != null) {
            final Player player = ev.getPlayer();
            double progress;
            progress = AchievementAPI.getAchievementInfo(this, player).progress+= ((double) 1 / 1900);
            if (progress >= 1 && !AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }
}
