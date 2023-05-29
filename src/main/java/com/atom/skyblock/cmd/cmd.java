package com.atom.skyblock.cmd;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.configuration.SBConfig;
import com.atom.skyblock.powerups.PowerupManager;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;

public class cmd implements CommandExecutor
{
    private final Plugin pl;
    public static boolean canFlyCombat;
    public static ArrayList<UUID> waitingForConf;
    
    public cmd() {
        this.pl = SBMain.getPlugin(SBMain.class);
    }
    
    public boolean onCommand(final CommandSender cs, final Command c, final String l, final String[] args) {
        if (c.getName().equalsIgnoreCase("asb")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    cs.sendMessage(" ");
                    cs.sendMessage(" §eAtomicSkyBlock §8- §eFeito por (joel) AtomDev");
                    cs.sendMessage(" ");

                    if (SBMain.globalCobblestoneLocation == null) {
                        if (!cs.hasPermission("asb.admin")) {
                            cs.sendMessage("§eNenhum comando está disponível pois o plugin não foi configurado.");
                        }else {
                            cs.sendMessage("§c/jsb set §8 - §cEste é o unico comando disponível pois o plugin não foi configurado ainda: Seta um bloco como a cobblestone global.");
                        }
                        return false;
                    }

                    cs.sendMessage(" §e/jsb next §8- §eInforma-te quantos blocos são necessários para alcançar a proxima Phase.");
                    cs.sendMessage(" §e/jsb ab §8- §eInforma-te os boosters ativos.");
                    if (cs instanceof Player) {
                        cs.sendMessage(" §e/jsb voar §8- §eTe faz voar se há um boost de voar ativo.");
                        cs.sendMessage(" §e/jsb spam <msg> §8- §eSpama algo.");
                        if (cs.hasPermission("asb.admin")) {
                            cs.sendMessage(" §c/jsb set §8- §cSeta um bloco como a cobblestone global.");
                            cs.sendMessage(" §c/jsb reset §8- §cReseta seu progresso. Precisa de confirmação.");
                            cs.sendMessage(" §c/jsb cc §8- §cLimpa o chat.");
                        }
                    }
                    cs.sendMessage(" ");
                }
                else if (args[0].equalsIgnoreCase("ab")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\n").append(" §9§lASB §8- §fAqui estão os §6boosters §fativos:").append("\n\n");
                    int i = 0;
                    if (PowerupManager.hasteBoost != 1.f) {
                        sb.append(String.format(" §8- §6%.1fx §fBooster de Mining Speed §7por §6%.1fmin", PowerupManager.hasteBoost, MathAndRNG.turnIntoMinutes(PowerupManager.hasteBoostTimeLeft))).append("\n");
                        i++;
                    }
                    if (PowerupManager.flightBoostEnabled) {
                        sb.append(String.format(" §8- §fBooster de Voar §7por §6%.1fmin", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft))).append("\n");
                        i++;
                    }
                    if (PowerupManager.cobbleBoost != 1.f) {
                        sb.append(String.format(" §8- §6%.1fx §fBooster de Cobblestone §7por §6%.1fmin", PowerupManager.cobbleBoost, MathAndRNG.turnIntoMinutes(PowerupManager.cobbleBoostTimeLeft))).append("\n");
                        i++;
                    }
                    if (i==0) sb.append(" §cNenhum booster ativo.").append("\n");
                    sb.append("\n");
                    cs.sendMessage(sb.toString());
                }
                else if (args[0].equalsIgnoreCase("set")) {
                    if (!(cs instanceof Player) || !cs.hasPermission("asb.admin")) {
                        return true;
                    }
                    final Player player = (Player)cs;
                    final double X = player.getTargetBlock(null, 100).getX();
                    final double Y = player.getTargetBlock(null, 100).getY();
                    final double Z = player.getTargetBlock(null, 100).getZ();
                    this.pl.getConfig().set("globalCobblestone", (X + " " + Y + " " + Z));
                    this.pl.saveConfig();
                    this.pl.reloadConfig();
                    player.sendMessage("§9§lASB §8» §7A cobblestone global foi setada. Por favor reinicie o servidor para jogar.");
                }
                else if (args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("voar")) {
                    if (!(cs instanceof Player)) {
                        return true;
                    }
                    if (!PowerupManager.flightBoostEnabled) {
                        cs.sendMessage("§9§lASB §8» §cNão tem boosters de voar ativos.");
                        return true;
                    }
                    final Player player = (Player)cs;
                    if (!cmd.canFlyCombat) {
                        player.sendMessage("§9§lASB §8» §4Você está em combate. Espere em torno de 30 segundos para voar novamente.");
                        return true;
                    }
                    if (player.getAllowFlight()) {
                        player.sendMessage(String.format("§9§lASB §8» §cBoost de voar desativado. O booster tem %.1fmin restantes.", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft)));
                        player.setAllowFlight(false);
                    }
                    else {
                        player.sendMessage(String.format("§9§lASB §8» §aBoost de voar ativado. O booster tem %.1fmin restantes.", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft)));
                        player.setAllowFlight(true);
                    }
                }
                else if (args[0].equalsIgnoreCase("cc")) {
                    if (!cs.hasPermission("asb.admin")) return true;
                    for (int i = 0; i < 500; i++) {
                        Bukkit.broadcastMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    }
                }
                else if (args[0].equalsIgnoreCase("next")) {
                    switch (SBMain.phase) {
                        case 1: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de " + (SBConfig.blocksBrokenPhase2 - SBMain.totalGlobalCobblestoneBroken) + " blocos quebrados para chegar na proxima.");
                            break;
                        }
                        case 2: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de " + (SBConfig.blocksBrokenPhase3 - SBMain.totalGlobalCobblestoneBroken) + " blocos quebrados para chegar na proxima.");
                            break;
                        }
                        case 3: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de " + (SBConfig.blocksBrokenPhase4 - SBMain.totalGlobalCobblestoneBroken) + " blocos quebrados para chegar na proxima.");
                            break;
                        }
                        case 4: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de " + (SBConfig.blocksBrokenPhase5 - SBMain.totalGlobalCobblestoneBroken) + " blocos quebrados para chegar na proxima.");
                            break;
                        }
                        case 5: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de " + (SBConfig.blocksBrokenPhase6 - SBMain.totalGlobalCobblestoneBroken) + " blocos quebrados para chegar na proxima.");
                            break;
                        }
                        case 6: {
                            cs.sendMessage("§9§lASB §8» §aEsse servidor está na Phase " + SBMain.phase + ". Você precisa de 0 blocos quebrados para chegar na proxima, porque você já chegou ao final. Parabéns!");
                            break;
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("reset") && cs instanceof Player) {
                    if (!cs.hasPermission("asb.admin")) return true;
                    final Player csP = (Player)cs;
                    if (cmd.waitingForConf.contains(csP.getUniqueId())) {
                        csP.sendMessage("§9§lASB §8» §cPlease type in chat, YES or NO");
                        return true;
                    }
                    cmd.waitingForConf.add(csP.getUniqueId());
                    csP.sendMessage("§9§lASB §8» §cAre you SURE you want to RESET THE PROGRESS? This will RESET EVERYTHING YOU HAVE DONE.\nIncluding:\nMissions;\nBlocks Broken;\nPhase\nPlease type in YES if you're REALLY SURE. (if yes reset world too)");
                }
            }
            else if (args.length >= 2 && args[0].equalsIgnoreCase("spam")) {
                if (!(cs instanceof Player)) {
                    return true;
                }
                final Player player = (Player)cs;
                final StringBuilder sb = new StringBuilder(args[1]);
                for (int i = 2; i < args.length; ++i) {
                    sb.append(' ').append(args[i]);
                }
                for (int i = 0; i < 10; ++i) {
                    player.chat(sb.toString());
                }
            }
        }
        return false;
    }
    
    static {
        cmd.canFlyCombat = true;
        cmd.waitingForConf = new ArrayList<UUID>();
    }
}
