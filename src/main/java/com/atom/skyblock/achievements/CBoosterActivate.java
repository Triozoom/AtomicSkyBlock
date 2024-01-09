package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CBoosterActivate extends Achievement {
    @Override
    public String name() {
        return "Amante de Poder";
    }

    @Override
    public String description() {
        return "Ative 10 boosters.";
    }

    @Override
    public String reward() {
        return "200 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_4_m1";
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
        return Material.EXPERIENCE_BOTTLE;
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.UNCOMMON;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 200;
        this.conclude(player);
    }

    @EventHandler
    public void onActivateBooster(final BoosterActivateEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            final double progress;
            progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress += (double) 1 / 10;
            if (progress >= 1) {
                this.onComplete(ev.getPlayer());
            }
        }
    }
}
