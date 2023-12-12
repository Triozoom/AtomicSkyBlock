package com.atom.skyblock.events;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.cmd.cmd;
import com.atom.skyblock.configuration.SBConfig;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.event.*;

import java.util.ArrayList;
import java.util.UUID;

public class CombatEvent implements Listener
{
    private final Plugin pl;

    private final ArrayList<UUID> uids = new ArrayList<>();
    
    public CombatEvent() {
        this.pl = SBMain.getPlugin(SBMain.class);
    }
    
    @EventHandler
    public void onAAAAAAAAAASim(final EntityDamageByEntityEvent ev) {
        if (ev.getEntity() instanceof Player && ev.getDamager() instanceof Player && !SBConfig.ff) {
            if (!uids.contains(ev.getDamager().getUniqueId())) {
                ev.getDamager().sendMessage("§cO dano amigo está desativado.");
                uids.add(ev.getDamager().getUniqueId());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        uids.remove(ev.getDamager().getUniqueId());
                    }
                }.runTaskLater(pl, 200L);
            }
            ev.setCancelled(true);
            return;
        }

        if (ev.getEntity() instanceof Player && ((Player)ev.getEntity()).getAllowFlight()) {
            ((Player)ev.getEntity()).setAllowFlight(false);
            ev.getEntity().sendMessage("§4Você está em combate! Fly está desativado por 30 segundos.");
            cmd.canFlyCombat = false;
            new BukkitRunnable() {
                public void run() {
                    cmd.canFlyCombat = true;
                    ev.getEntity().sendMessage("§aVocê saiu de combate!");
                }
            }.runTaskLater(this.pl, 600L);
        }
    }
}
