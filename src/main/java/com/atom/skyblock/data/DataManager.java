package com.atom.skyblock.data;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {

    private final static ConcurrentHashMap<UUID, PlayerData> c = new ConcurrentHashMap<>();

    public static void add(final Player player, final PlayerData data) {
        c.putIfAbsent(player.getUniqueId(), data);
    }

    public static void remove(final Player player) {
        c.remove(player.getUniqueId());
    }

    public static PlayerData get(final Player player) {
        return c.get(player.getUniqueId());
    }

}
