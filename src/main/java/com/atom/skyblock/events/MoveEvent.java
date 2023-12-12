package com.atom.skyblock.events;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.VoidDeathEvent;
import com.atom.skyblock.configuration.SBConfig;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(final PlayerMoveEvent ev) {
        if (ev.getTo().getY() <= 0) {
            dieVoid(ev.getPlayer());
        }else {
            DataManager.get(ev.getPlayer())
                    .hasMovedSinceVoidDeath = true;
        }
    }

    public static void dieVoid(final Player player) {
        final VoidDeathEvent vde = new VoidDeathEvent(player);
        Bukkit.getPluginManager().callEvent(vde);
        final PlayerData data = DataManager.get(player);
        if (!data.hasMovedSinceVoidDeath) return;
        data.hasMovedSinceVoidDeath = false;
        if (!SBConfig.kivd) player.getInventory().clear();
        player.setFlying(false);
        player.teleport(SBMain.globalCobblestoneLocation.clone().add(0, 1.11, 0));
        player.setInvulnerable(true);
        Bukkit.broadcastMessage("§c§lASB §8» §c" + player.getName() + " morreu caindo no infinito.");
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setInvulnerable(false);
            }
        }.runTaskLaterAsynchronously(SBMain.INSTANCE, 30L);
    }
}
