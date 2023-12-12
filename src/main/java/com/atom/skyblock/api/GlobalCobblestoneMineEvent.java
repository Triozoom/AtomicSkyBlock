package com.atom.skyblock.api;

import com.atom.skyblock.powerups.PowerupManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class GlobalCobblestoneMineEvent extends Event
{
    private static final HandlerList handlers;

    private final Player player;
    private final Material currentMaterial;
    private final boolean boosterActive;
    private final float boosterAmount;

    public GlobalCobblestoneMineEvent(final Player player, final Material currentMaterial) {
        this.player = player;
        this.currentMaterial = currentMaterial;
        this.boosterActive = PowerupManager.cobbleBoostTimeLeft > 0L;
        this.boosterAmount = this.boosterActive ? 1.F : PowerupManager.cobbleBoost;
    }
    
    public HandlerList getHandlers() {
        return GlobalCobblestoneMineEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return GlobalCobblestoneMineEvent.handlers;
    }

    static {
        handlers = new HandlerList();
    }
}
