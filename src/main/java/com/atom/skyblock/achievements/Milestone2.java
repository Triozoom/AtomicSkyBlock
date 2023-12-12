package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Milestone2 extends Achievement {
    @Override
    public String name() {
        return "Minerados 1000 Blocos!";
    }

    @Override
    public String description() {
        return "Ganha-se quando o player quebra 1000 blocos.";
    }

    @Override
    public String reward() {
        return "1x Booster de Haste (2x Velocidade)";
    }

    @Override
    public String id() {
        return "asb_a_m2";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(2.f));
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.IRON_PICKAXE;
    }

    @EventHandler
    public void onBlockBreak(final GlobalCobblestoneMineEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).blocksBroken / 1000;
            if (DataManager.get(ev.getPlayer()).blocksBroken >= 1000) {
                onComplete(ev.getPlayer());
            }
        }
    }
}
