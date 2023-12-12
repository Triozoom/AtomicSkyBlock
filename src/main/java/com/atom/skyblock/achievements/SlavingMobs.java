package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.farms.items.FarmItemManager;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SlavingMobs extends Achievement {
    @Override
    public String name() {
        return "As espécies Mob são meus escravos.";
    }

    @Override
    public String description() {
        return "Tenha 25 farms colocadas.";
    }

    @Override
    public String reward() {
        return "1250 Blocos Globais e 1x Booster de Cobble (6x)";
    }

    @Override
    public String id() {
        return "asb_a_farms4";
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
        SBMain.totalGlobalCobblestoneBroken+= 1250;
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(6.F));
        for (final Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle("§3§lCONQUISTA LENDÁRIA FOI COMPLETA", "§fO jogador" + player.getName() + " completou uma conquista LENDÁRIA!");
            online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 3F, 0.65F);
            online.playSound(online.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3F, 1.3F);
        }
        this.conclude(player);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent ev) {
        if (!FarmItemManager.isValidFarmMaterial(ev.getBlock().getType())) return;
        double progress;
        progress = AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress = (double) DataManager.get(ev.getPlayer()).ownedFarms.size() / 25;
        if (progress >= 1 && DataManager.get(ev.getPlayer()).ownedFarms.size() >= 25 && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
