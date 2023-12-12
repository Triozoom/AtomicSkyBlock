package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.CraftFarmEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmProfessional extends Achievement {
    @Override
    public String name() {
        return "Profissional de Farms";
    }

    @Override
    public String description() {
        return "Crie 10 farms.";
    }

    @Override
    public String reward() {
        return "700 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_c_farm3";
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
        SBMain.totalGlobalCobblestoneBroken+= 700;
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.DIAMOND_HOE;
    }

    @EventHandler
    public void onCraft(final CraftFarmEvent ev) {
        double progress;
        progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress+= (double) 1 / 10;
        if (progress >= 1 && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
