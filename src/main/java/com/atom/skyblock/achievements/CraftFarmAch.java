package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.CraftFarmEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftFarmAch extends Achievement {
    @Override
    public String name() {
        return "A farmação começa.";
    }

    @Override
    public String description() {
        return "Crie uma farm pela primeira vez.";
    }

    @Override
    public String reward() {
        return "350 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_c_farm1";
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
        SBMain.totalGlobalCobblestoneBroken+= 350;
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.WOODEN_HOE;
    }

    @EventHandler
    public void onCraft(final CraftFarmEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
