package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftItemAch extends Achievement {
    @Override
    public String name() {
        return "Criador";
    }

    @Override
    public String description() {
        return "Crie um item pela primeira vez.";
    }

    @Override
    public String reward() {
        return "20 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_11";
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
        SBMain.totalGlobalCobblestoneBroken+= 20;
        this.conclude(player);
    }

    @EventHandler
    public void onCraft(final CraftItemEvent ev) {
        if (!AchievementAPI.hasCompleted((Player) ev.getWhoClicked(), this)) {
            this.onComplete((Player) ev.getWhoClicked());
        }
    }
}
