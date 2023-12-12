package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.powerups.impl.BoosterItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Milestone4 extends Achievement {
    @Override
    public String name() {
        return "Connoisseur de Cobblestone";
    }

    @Override
    public String description() {
        return "Ganha-se quando o player quebra 25000 blocos.";
    }

    @Override
    public String reward() {
        return "1x Bloco de Netherite e 2 Boosters de Haste (3x)";
    }

    @Override
    public String id() {
        return "asb_a_m4";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(3.f));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(3.f));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.NETHERITE_BLOCK));
        this.conclude(player);
    }

    @Override
    public Material itemDisplay() {
        return Material.NETHERITE_PICKAXE;
    }

    @EventHandler
    public void onBlockBreak(final GlobalCobblestoneMineEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).blocksBroken / 25000;
            if (DataManager.get(ev.getPlayer()).blocksBroken >= 25000) {
                onComplete(ev.getPlayer());
            }
        }
    }
}
