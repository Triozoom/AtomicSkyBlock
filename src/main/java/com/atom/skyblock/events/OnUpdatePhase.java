package com.atom.skyblock.events;

import com.atom.skyblock.api.PhaseUpdateEvent;
import io.puharesource.mc.titlemanager.api.v2.*;
import com.atom.skyblock.api.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class OnUpdatePhase implements Listener
{
    protected TitleManagerAPI api;
    
    public OnUpdatePhase() {
        this.api = (TitleManagerAPI)Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
    }
    
    @EventHandler
    public void onUpdatePhase(final PhaseUpdateEvent ev) {
        for (final Player afklnslka : Bukkit.getOnlinePlayers()) {
            this.api.sendTitles(afklnslka, "§3§lYOU HAVE REACHED", "§f§lPHASE " + ev.getNewPhase());
            afklnslka.getWorld().spawn(afklnslka.getLocation(), Firework.class);
            afklnslka.playSound(afklnslka.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
        switch (ev.getNewPhase()) {
            case 1: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! You have started the skyblock adventure! Good luck! Phase Theme: Plains");
                break;
            }
            case 2: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! You're Gettin Advanced: Here we go on preparing for the worst, in the other world, THE NETHER. You're up for the challange, like you should be. Let us go for them blaze rods!");
                break;
            }
            case 3: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! In The Nether: The nether has come, trying to get out of the fortress unhurt.");
                break;
            }
            case 4: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! Out to the end: You've gotten the blaze rods. You've come far, now prepare to finish it off!");
                break;
            }
            case 5: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! THE END: Enough is enough, the dragon shall be gone. You've reached it, now get it done.");
                break;
            }
            case 6: {
                Bukkit.broadcastMessage("§3§lASB §8» §aCongrats! AFTER PHASES: You reached the top of the mountain. Where the SKYBLOCK GODS belong.");
                break;
            }
        }
    }
}
