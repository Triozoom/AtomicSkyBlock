package com.atom.skyblock.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VoidDeathEvent extends Event
{
    private static final HandlerList handlers;

    @Getter
    private final Player player;

    public VoidDeathEvent(final Player player) {
        this.player = player;
    }
    
    public HandlerList getHandlers() {
        return VoidDeathEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VoidDeathEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
