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
import org.bukkit.plugin.java.JavaPlugin;

public class RareBlock extends Achievement {
    @Override
    public String name() {
        return "Estou rico, rico!";
    }

    @Override
    public String description() {
        return "Quebre um material raro da Cobblestone.";
    }

    @Override
    public String reward() {
        return "120 Blocos";
    }

    @Override
    public String id() {
        return "asb_a_new_1";
    }

    @Override
    public Material itemDisplay() {
        return Material.DIAMOND_BLOCK;
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.RARE;
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
        SBMain.totalGlobalCobblestoneBroken+= 120;
        this.conclude(player);
    }

    @EventHandler
    public void onBlockBreak(final GlobalCobblestoneMineEvent ev) {
        if (!isRare(ev.getCurrentMaterial())) return;
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }

    private boolean isRare(final Material m) {
        switch (m) {
            case DIAMOND_BLOCK:
            case NETHERITE_BLOCK:
            case ANCIENT_DEBRIS:
                return true;
        }
        return false;
    }
}
