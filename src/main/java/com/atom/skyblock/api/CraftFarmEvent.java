package com.atom.skyblock.api;

import com.atom.skyblock.farms.Farm;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class CraftFarmEvent extends Event
{

    private final Player player;
    private final Farm.EntitiesType farmType;
    private static final HandlerList handlers;

    public CraftFarmEvent(final Player player, final Farm.EntitiesType farmType) {
        this.player = player;
        this.farmType = farmType;
    }
    
    public HandlerList getHandlers() {
        return CraftFarmEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CraftFarmEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
