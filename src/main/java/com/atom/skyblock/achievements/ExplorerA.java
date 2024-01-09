package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ExplorerA extends Achievement {
    @Override
    public String name() {
        return "Explorador";
    }

    @Override
    public String description() {
        return "Ande e explore 120 metros de distancia da sua ilha.";
    }

    @Override
    public String reward() {
        return "Booster de Haste (2x)";
    }

    @Override
    public String id() {
        return "asb_a_expl";
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.UNCOMMON;
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
    public Material itemDisplay() {
        return Material.GRASS_BLOCK;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(2.F));
        this.conclude(player);
    }

    @EventHandler
    public void obc(final PlayerMoveEvent ev) {
        final Player player = ev.getPlayer();
        if (AchievementAPI.hasCompleted(player, this)) return;

        final double dist = ev.getTo().distance(SBMain.globalCobblestoneLocation);
        AchievementAPI.getAchievementInfo(this, player).progress= dist/120; // a block = 1M; using this we can define that in blocks distance we can divide it by 1000 to get a KM
        if (AchievementAPI.getAchievementInfo(this, player).progress >= 1) {
            this.onComplete(player);
        }
    }

}
