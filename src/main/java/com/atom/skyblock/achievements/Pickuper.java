package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Pickuper extends Achievement {
    @Override
    public String name() {
        return "Pegador";
    }

    @Override
    public String description() {
        return "Ganha-se quando o player pega 2.000 items do chão.";
    }

    @Override
    public String reward() {
        return "6x Diamantes";
    }

    @Override
    public String id() {
        return "asb_a_pg1";
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.UNCOMMON;
    }

    @Override
    public Material itemDisplay() {
        return Material.FEATHER;
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.DIAMOND, 6));
        this.conclude(player);
    }

    @EventHandler
    public void onPick(final PlayerPickupItemEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress+= (double) ev.getItem().getItemStack().getAmount() / 2000;
            if (AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress >= 1) {
                onComplete(ev.getPlayer());
            }
        }
    }
}
