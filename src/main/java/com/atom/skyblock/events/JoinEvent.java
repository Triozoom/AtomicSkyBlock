package com.atom.skyblock.events;

import com.atom.skyblock.SBMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class JoinEvent
implements Listener {

    private final String PATCH_NOTES =
            String.format("\n §9[AtomicSkyBlock] §6Patch Notes for v%s available: \n", SBMain.getPlugin(SBMain.class).getDescription().getVersion()) +
            " §8- §fBoosters have been added since last dev version;\n" +
            " §8- §fOptimized events;\n" +
            " §8- §fMade items near the cobblestone be teleported to it so they're unlikely to fall;\n"+
            " §8- §fRemoved fake loot chests;\n" +
            " §8- §fPhase 6 has been added;\n" +
            " §8- §fAdded new mobs;\n" +
            " §8- §fFixed first few phases not giving seeds and making it impossible to grow a farm;\n" +
            " §8- §fAdded friendly-fire configuration;\n" +
            " §8- §fRemoved JoelSkyBlock\n";

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.getPlayer().sendMessage(PATCH_NOTES);
    }

    @EventHandler
    public void a(final ServerCommandEvent e) {
        if (e.getCommand().startsWith("say")) {
            e.setCancelled(true);
        }
    }
}
