package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.powerups.impl.BoosterItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Milestone3 extends Achievement {
    @Override
    public String name() {
        return "Minerados 5000 Blocos!";
    }

    @Override
    public String description() {
        return "Ganha-se quando o player quebra 5000 blocos.";
    }

    @Override
    public String reward() {
        return "1x Booster de Cobble (2.25x)";
    }

    @Override
    public String id() {
        return "asb_a_m3";
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
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(2.25f));
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.DIAMOND_PICKAXE;
    }

    @EventHandler
    public void onBlockBreak(final GlobalCobblestoneMineEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).blocksBroken / 5000;
            if (DataManager.get(ev.getPlayer()).blocksBroken >= 5000) {
                onComplete(ev.getPlayer());
            }
        }
    }
}
