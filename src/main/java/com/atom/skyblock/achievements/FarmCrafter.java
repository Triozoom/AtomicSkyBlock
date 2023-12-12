package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.CraftFarmEvent;
import com.atom.skyblock.data.DataManager;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmCrafter extends Achievement {
    @Override
    public String name() {
        return "Criador de Farms";
    }

    @Override
    public String description() {
        return "Crie 3 farms.";
    }

    @Override
    public String reward() {
        return "300 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_c_farm2";
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
        SBMain.totalGlobalCobblestoneBroken+= 300;
        this.conclude(player);
    }

    @EventHandler
    public void onCraft(final CraftFarmEvent ev) {
        double progress;
        progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress+= 1.001D / 3;
        if (progress >= 1 && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
