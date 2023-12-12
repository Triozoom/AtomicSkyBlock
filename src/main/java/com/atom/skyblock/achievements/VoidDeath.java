package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.api.VoidDeathEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidDeath extends Achievement {
    @Override
    public String name() {
        return "\"Eu prefiro morrer do que perder a vida\"";
    }

    @Override
    public String description() {
        return "Seja consumido pelo infinito void.";
    }

    @Override
    public String reward() {
        return "1x Olho do Fim";
    }

    @Override
    public String id() {
        return "asb_a_5";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.ENDER_EYE, 1));
        this.conclude(player);
    }

    @EventHandler
    public void onDumbassDie(final VoidDeathEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
