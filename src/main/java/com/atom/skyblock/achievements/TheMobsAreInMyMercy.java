package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.farms.items.FarmItemManager;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TheMobsAreInMyMercy extends Achievement {
    @Override
    public String name() {
        return "Os Mobs estão sob minha piedade.";
    }

    @Override
    public String description() {
        return "Tenha 10 farms colocadas.";
    }

    @Override
    public String reward() {
        return "690 Blocos Globais (nice)";
    }

    @Override
    public String id() {
        return "asb_a_farms3";
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
    public Material itemDisplay() {
        return Material.BLAZE_SPAWN_EGG;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 690;
        this.conclude(player);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent ev) {
        if (!FarmItemManager.isValidFarmMaterial(ev.getBlock().getType())) return;
        double progress;
        progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).ownedFarms.size() / 10;
        if (progress >= 1 && DataManager.get(ev.getPlayer()).ownedFarms.size() >= 10 && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
