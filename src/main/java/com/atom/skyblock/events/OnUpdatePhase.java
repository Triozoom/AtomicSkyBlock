package com.atom.skyblock.events;

import com.atom.skyblock.api.PhaseUpdateEvent;
import io.puharesource.mc.titlemanager.api.v2.*;
import com.atom.skyblock.api.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class OnUpdatePhase implements Listener
{
    protected TitleManagerAPI api;
    
    public OnUpdatePhase() {
        this.api = (TitleManagerAPI)Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
    }
    
    @EventHandler
    public void onUpdatePhase(final PhaseUpdateEvent ev) {
        for (final Player afklnslka : Bukkit.getOnlinePlayers()) {
            this.api.sendTitles(afklnslka, "§3§lVOCÊ CHEGOU NA", "§f§lPHASE " + ev.getNewPhase());
            afklnslka.getWorld().spawn(afklnslka.getLocation(), Firework.class);
            afklnslka.playSound(afklnslka.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
        switch (ev.getNewPhase()) {
            case 1: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! Você começou a jornada no Atom SkyBlock! Tema: Planícies");
                break;
            }
            case 2: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! Agora estás se preparando para sua nova jornada no Nether! Tema: Preparação");
                break;
            }
            case 3: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! O Nether chegou! Agora que te preparaste, irás lutar contra blazes! Dica: pegue as blaze rods.");
                break;
            }
            case 4: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! Você agora que lutou contra blazes e o Nether estás se preparando para o End! Tema: Pós Nether.");
                break;
            }
            case 5: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! THE END: Estás na phase que te dará os portais para o The End! Cria os olhos do fim para continuar na sua jornada.");
                break;
            }
            case 6: {
                Bukkit.broadcastMessage("§3§lASB §8» §aParabéns! Você chegou no nível extra, onde os deuses do SkyBlock ficam. Aqui terás itens, baús e mobs de todos os outros níveis (phases)!");
                break;
            }
        }
    }
}
