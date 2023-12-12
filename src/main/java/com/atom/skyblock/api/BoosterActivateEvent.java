package com.atom.skyblock.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoosterActivateEvent extends Event
{
    private static final HandlerList handlers;

    @Getter
    private final Player player;

    @Getter
    private final BoosterType type;

    public enum BoosterType {
        COMMON_STONE,
        HASTE,
        FLIGHT
    }

    public BoosterActivateEvent(final Player player, BoosterType type) {
        this.player = player;
        this.type = type;
    }
    
    public HandlerList getHandlers() {
        return BoosterActivateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BoosterActivateEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
