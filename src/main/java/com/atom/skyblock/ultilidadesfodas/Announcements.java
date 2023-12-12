package com.atom.skyblock.ultilidadesfodas;

import com.atom.skyblock.SBMain;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Announcements {

    private static String[] ann = new String[] {
            " §7\n §3§lAtomSB §8» &fDica: Use (se fores admin) /asb rm para remover todos os items do chão.\n §7",
            " §7\n §3§lAtomSB §8» §fDica: Se achar que não precisa de items de um loot chest da Cobblestone, apenas quebre-o com shift e os items não serão dropados.\n §7",
            " §7\n §3§lAtomSB §8» §fLembre-se de dar uma pausa! Sim, o Atom SkyBlock é um ótimo plugin, mas priorize sua saúde.\n §7",
            " §7\n §3§lAtomSB §8» §fDica: Não apenas a Cobblestone consegue te dar drops raros como boosters, também use farms para conseguir drops legais.\n §7"
    };

    public static void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                final String announcement = ann[MathAndRNG.generateInteger(ann.length - 1)];
                Bukkit.broadcastMessage(announcement);
            }
        }.runTaskTimerAsynchronously(SBMain.INSTANCE, 5000L, 12000L);
    }
}
