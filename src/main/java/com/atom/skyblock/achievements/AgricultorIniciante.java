package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.farms.items.FarmItemManager;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AgricultorIniciante extends Achievement {
    @Override
    public String name() {
        return "Agricultor Iniciante";
    }

    @Override
    public String description() {
        return "Tenha 3 farms colocadas.";
    }

    @Override
    public String reward() {
        return "220 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_farms1";
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
        SBMain.totalGlobalCobblestoneBroken+= 220;
        this.conclude(player);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent ev) {
        if (!FarmItemManager.isValidFarmMaterial(ev.getBlock().getType())) return;
        double progress;
        progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).ownedFarms.size() / 3;
        if (progress >= 1 && DataManager.get(ev.getPlayer()).ownedFarms.size() >= 3 && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
