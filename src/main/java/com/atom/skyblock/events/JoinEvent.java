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
            " §8- §fAdded POWER UPS;\n" +
            " §8- §f(don't remember)";

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
