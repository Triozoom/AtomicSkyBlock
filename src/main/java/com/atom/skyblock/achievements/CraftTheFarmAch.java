package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.CraftFarmEvent;
import com.atom.skyblock.farms.Farm;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftTheFarmAch extends Achievement {
    @Override
    public String name() {
        return "Imperador dos Mobs.";
    }

    @Override
    public String description() {
        return "Crie uma farm de TODOS os mobs e animais pela primeira vez.";
    }

    @Override
    public String reward() {
        return "3500 Blocos Globais e Booster de Cobble (10x)";
    }

    @Override
    public String id() {
        return "asb_a_c_affarm";
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
        SBMain.totalGlobalCobblestoneBroken+= 3500;
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(10.F));
        this.conclude(player);
    }

    @EventHandler
    public void onCraft(final CraftFarmEvent ev) {
        if (ev.getFarmType() != Farm.EntitiesType.ALL_NON_DANGER) return;
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
