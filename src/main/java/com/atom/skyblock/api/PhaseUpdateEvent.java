package com.atom.skyblock.api;

import org.bukkit.event.*;
import org.bukkit.*;

public class PhaseUpdateEvent extends Event
{
    private static final HandlerList handlers;
    private int newPhase;
    private int blocksBroken;
    
    public PhaseUpdateEvent(final int newPhase, final int blocksBroken) {
        this.newPhase = newPhase;
        this.blocksBroken = blocksBroken;
        Bukkit.getConsoleSender().sendMessage("§9[AtomicSkyBlock] §aPHASE " + newPhase + " REACHED!");
    }
    
    public HandlerList getHandlers() {
        return PhaseUpdateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PhaseUpdateEvent.handlers;
    }
    
    public int getNewPhase() {
        return this.newPhase;
    }
    
    public int getBlocksBroken() {
        return this.blocksBroken;
    }
    
    static {
        handlers = new HandlerList();
    }
}
