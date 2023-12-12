package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.configuration.SBConfig;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FunnyAchievementIThinkProbablyLikely extends Achievement {
    @Override
    public String name() {
        return "Traidor!";
    }

    @Override
    public String description() {
        return "Tente bater em um amigo com fogo amigo desativado.";
    }

    @Override
    public String reward() {
        return "25 Blocos Globais";
    }

    @Override
    public String id() {
        return "asb_a_2";
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
        return Material.REDSTONE;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 25;
        this.conclude(player);
    }

    @EventHandler
    public void onBeATraitor(final EntityDamageByEntityEvent ev) {
        if (!(ev.getEntity() instanceof Player && ev.getDamager() instanceof Player)) return;
        final Player player = (Player) ev.getDamager();
        if (!AchievementAPI.hasCompleted(player, this)) {
            if (!SBConfig.ff) {
                onComplete(player);
            }
        }
    }
}
