package com.atom.skyblock.api;

import com.atom.skyblock.farms.Farm;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class KillFarmMobEvent extends Event
{

    private final Player player;
    private final LivingEntity dead;
    private final Farm farm;
    private static final HandlerList handlers;

    public KillFarmMobEvent(final Player player, final LivingEntity dead, final Farm farm) {
        this.player = player;
        this.dead = dead;
        this.farm = farm;
    }
    
    public HandlerList getHandlers() {
        return KillFarmMobEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return KillFarmMobEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
