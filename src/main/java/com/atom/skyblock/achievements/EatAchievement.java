package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EatAchievement extends Achievement {
    @Override
    public String name() {
        return "Fominha";
    }

    @Override
    public String description() {
        return "Coma algo.";
    }

    @Override
    public String reward() {
        return "12x Bife";
    }

    @Override
    public String id() {
        return "asb_a_3";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.COOKED_BEEF, 12));
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.POTATO;
    }

    @EventHandler
    public void onConsoom(final PlayerItemConsumeEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
