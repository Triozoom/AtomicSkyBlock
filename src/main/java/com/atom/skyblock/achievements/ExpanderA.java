package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpanderA extends Achievement {
    @Override
    public String name() {
        return "Expandidor";
    }

    @Override
    public String description() {
        return "Coloque blocos 120 metros de distancia da sua ilha.";
    }

    @Override
    public String reward() {
        return "1x Booster de Voar";
    }

    @Override
    public String id() {
        return "asb_a_expand";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, FlightBoostItem.returnItemStack());
        this.conclude(player);
    }

    @EventHandler
    public void obc(final BlockPlaceEvent ev) {
        final Player player = ev.getPlayer();
        if (AchievementAPI.hasCompleted(player, this)) return;

        final double dist = ev.getBlock().getLocation().distance(SBMain.globalCobblestoneLocation);
        if (dist >= 120) {
            this.onComplete(player);
        }
    }

}
