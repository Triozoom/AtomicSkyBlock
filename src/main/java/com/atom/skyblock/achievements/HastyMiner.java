package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.data.DataManager;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HastyMiner extends Achievement {
    @Override
    public String name() {
        return "Destreza Extrema!";
    }

    @Override
    public String description() {
        return "Ative um booster de haste e quebre 100 blocos em menos de 1 minuto.";
    }

    @Override
    public String reward() {
        return "120 Blocos Globais e 1x Picareta de Diamante";
    }

    @Override
    public String id() {
        return "asb_a_de";
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
        SBMain.totalGlobalCobblestoneBroken+= 120;
        this.conclude(player);
    }

    @EventHandler
    public void onActivateBooster(final BoosterActivateEvent ev) {
        if (ev.getType() == BoosterActivateEvent.BoosterType.HASTE) {
            final Achievement ac = this;
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (final Player player : Bukkit.getOnlinePlayers()) {
                        if (DataManager.get(player).blocksBrokenHaste > 100) {
                            if (!AchievementAPI.hasCompleted(player, ac)) {
                                onComplete(ev.getPlayer());
                            }
                        }
                    }
                }
            }.runTaskLater(SBMain.INSTANCE, 1200L);
        }
    }
}
