package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BoosterActivate extends Achievement {
    @Override
    public String name() {
        return "Hora do Poder!";
    }

    @Override
    public String description() {
        return "Ative um booster pela primeira vez.";
    }

    @Override
    public String reward() {
        return "120 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_4";
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
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 120;
        this.conclude(player);
    }

    @EventHandler
    public void onActivateBooster(final BoosterActivateEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
