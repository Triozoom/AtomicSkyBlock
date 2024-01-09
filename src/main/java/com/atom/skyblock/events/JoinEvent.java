package com.atom.skyblock.events;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.ultilidadesfodas.IndividualDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class JoinEvent
implements Listener {

    private final String PATCH_NOTES =
            String.format("\n §9[AtomicSkyBlock] §6Patch Notes for v%s available: \n", SBMain.getPlugin(SBMain.class).getDescription().getVersion()) +
            " §8- §aFIRST STABLE VERSION!\n" +
            " §8- §fAdded Farming System;\n" +
            " §8- §fAdded 5 Farms;\n" +
            " §8- §fAdded possibility of farm drops;\n" +
            " §8- §fAdded complete support for AtomAchievements;\n" +
            " §8- §fAdded 52 Achievements;\n" +
            " §8- §fMostly translated everything to Portuguese;\n" +
            " §8- §fImproved performance (unlikely to decrease TPS unless server starts dying);\n" +
            " §8- §fAdded Chatting System;\n" +
            " §8- §fAdded announcements;\n" +
            " §8- §fBugfixes and enhancements;\n" +
            " §8- §fQoL changes;\n" +
            " §8- §fMade drops more likely to happen;\n" +
            " §8- §fAnd more I don't remember!";

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        SBMain.INSTANCE.idb.createData(e.getPlayer());
        e.getPlayer().sendMessage(PATCH_NOTES);
        if (SBMain.aAc) e.getPlayer().sendMessage("§eEsse servidor é compativel com Atom Achievements e terá conquistas. Todas as recompensas por completar as conquistas, se forem items, serão dropadas EM CIMA DA PEDRA. Outras recompensas (blocos quebrados) serão automaticamente adicionadas.");
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent e) {
        SBMain.INSTANCE.idb.store(DataManager.get(e.getPlayer()));
        DataManager.remove(e.getPlayer());
    }

    @EventHandler
    public void a(final ServerCommandEvent e) {
        if (e.getCommand().startsWith("say")) {
            e.setCancelled(true);
        }
    }
}
