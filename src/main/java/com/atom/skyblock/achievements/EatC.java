package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EatC extends Achievement {
    @Override
    public String name() {
        return "Faminto";
    }

    @Override
    public String description() {
        return "Coma 5.000 comidas.";
    }

    @Override
    public String reward() {
        return "1x Booster de Cobble (2x)";
    }

    @Override
    public String id() {
        return "asb_a_3_c";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(2.F));
        this.conclude(player);
    }

    @EventHandler
    public void onConsoom(final PlayerItemConsumeEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            final double progress;
            progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress += (double) 1 / 5000;
            if (progress >= 1) {
                this.onComplete(ev.getPlayer());
            }
        }
    }
}
