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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Milestone1 extends Achievement {
    @Override
    public String name() {
        return "Primeiros 100 Blocos!";
    }

    @Override
    public String description() {
        return "Ganha-se quando o player quebra 100 blocos.";
    }

    @Override
    public String reward() {
        return "1x Booster de Fly";
    }

    @Override
    public String id() {
        return "asb_a_m1";
    }

    @Override
    public Material itemDisplay() {
        return Material.STONE_PICKAXE;
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

    @EventHandler
    public void onBlockBreak(final GlobalCobblestoneMineEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).blocksBroken / 100;
            if (DataManager.get(ev.getPlayer()).blocksBroken >= 100) {
                onComplete(ev.getPlayer());
            }
        }
    }
}
