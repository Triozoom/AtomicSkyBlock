package com.atom.skyblock.data;

import com.atom.skyblock.farms.Farm;
import com.atom.skyblock.farms.FarmManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerData {

    public final Player player;

    /*
        Farm system
     */

    public CopyOnWriteArrayList<Farm> ownedFarms;

    public boolean hasMovedSinceVoidDeath;

    public boolean openedChestSinceBlock;

    public PlayerData(final Player player, final int blocksBroken) {
        this.player = player;
        this.blocksBroken = blocksBroken;
        ownedFarms = FarmManager.updateFarms(player);
    }

    public int blocksBroken; // Individual

    public int blocksBrokenHaste;

}
