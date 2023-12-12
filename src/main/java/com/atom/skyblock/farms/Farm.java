package com.atom.skyblock.farms;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;

import java.util.Collection;

@RequiredArgsConstructor
public class Farm {

    public final Location location; // block location (mobs will spawn above)

    public final EntitiesType spawnType;

    public final OfflinePlayer owner;

    /*
        Methods below will be used for updating and shall not be stored.
        Do not spawn mob when there is already one.
        Update "canSpawn" whenever needed (no idea why u'd use it)
     */

    public boolean mobSpawned, canSpawn = true;
    public Entity lastEntity;

    public enum EntitiesType {
        OVERWORLD("ow"),
        OVERWORLD_NON_DANGER("ow_f"),

        NETHER("hell"),

        ALL("all"),
        ALL_NON_DANGER("all_f"); // basically means all PLUS not dangerous

        public final String parseable;
        EntitiesType(final String parseable) {
            this.parseable = parseable;
        }

        public static EntitiesType fromString(final String str) {
            for (final EntitiesType etype : EntitiesType.values()) {
                if (etype.parseable.equalsIgnoreCase(str)) return etype;
            }
            return null;
        }
    }

    public Collection<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return location.getWorld().getNearbyEntities(location, x, y, z);
    }

}
