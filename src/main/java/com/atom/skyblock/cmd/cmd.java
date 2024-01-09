package com.atom.skyblock.cmd;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.configuration.SBConfig;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.farms.Farm;
import com.atom.skyblock.farms.items.FarmItemManager;
import com.atom.skyblock.powerups.PowerupManager;
import com.atom.skyblock.ultilidadesfodas.Database;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import dev.joel.bukkitutil.ci.CustomInventory;
import dev.joel.bukkitutil.ci.PlayerCustomInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
                    cs.sendMessage(" §eAtomicSkyBlock §8- §eFeito por AtomDev");
                    cs.sendMessage(" ");

                    if (SBMain.globalCobblestoneLocation == null) {
                        if (!cs.hasPermission("asb.admin")) {
                            cs.sendMessage("§eNenhum comando está disponível pois o plugin não foi configurado.");
                        } else {
                            cs.sendMessage("§c/jsb set §8 - §cEste é o unico comando disponível pois o plugin não foi configurado ainda: Seta um bloco como a cobblestone global.");
                        }
                        return false;
                    }

                    cs.sendMessage(" §e/asb next §8- §eInforma-te quantos blocos são necessários para alcançar a proxima Phase.");
                    cs.sendMessage(" §e/asb broken §8- §eInforma-te quantos blocos *você* já quebrou.");
                    cs.sendMessage(" §e/asb ab §8- §eInforma-te os boosters ativos.");
                    cs.sendMessage(" §e/asb recipes §8- §eInforma-te as receitas para farms.");
                    if (cs instanceof Player) {
                        cs.sendMessage(" §e/asb voar §8- §eTe faz voar se há um boost de voar ativo.");
                        cs.sendMessage(" §e/asb spam <msg> §8- §eSpama algo.");
                        if (cs.hasPermission("asb.admin")) {
                            cs.sendMessage(" §c/asb rm §8- §cRemove os blocos do chão.");
                            cs.sendMessage(" §c/asb set §8- §cSeta um bloco como a cobblestone global.");
                            cs.sendMessage(" §c/asb setblocks §8- §cSeta quantidade de blocos quebrados (limitado).");
                            cs.sendMessage(" §c/asb reset §8- §cReseta seu progresso. Precisa de confirmação.");
                            cs.sendMessage(" §c/asb cc §8- §cLimpa o chat.");
                        }
                    }
                    cs.sendMessage(" ");
                    return false;
                } else if (args[0].equalsIgnoreCase("ab")) {
                    StringBuilder sb = new StringBuilder();
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

                    if (i == 0) {
                        cs.sendMessage("§cNão há boosters ativos.");
                        return false;
                    }
                    cs.sendMessage(String.format("\n §9§lASB §8- §fAqui estão os §6%d boosters §fativos:\n\n", i));
                    sb.append("\n");
                    cs.sendMessage(sb.toString());
                    return false;
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (!(cs instanceof Player) || !cs.hasPermission("asb.admin")) {
                        return true;
                    }
                    final Player player = (Player) cs;
                    final double X = player.getTargetBlock(null, 100).getX();
                    final double Y = player.getTargetBlock(null, 100).getY();
                    final double Z = player.getTargetBlock(null, 100).getZ();
                    this.pl.getConfig().set("globalCobblestone", (X + " " + Y + " " + Z));
                    this.pl.saveConfig();
                    this.pl.reloadConfig();
                    player.sendMessage("§9§lASB §8» §7A cobblestone global foi setada. Por favor reinicie o servidor para jogar.");
                    return false;
                } else if (args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("voar")) {
                    if (!(cs instanceof Player)) {
                        return true;
                    }
                    if (!PowerupManager.flightBoostEnabled) {
                        cs.sendMessage("§9§lASB §8» §cNão tem boosters de voar ativos.");
                        return true;
                    }
                    final Player player = (Player) cs;
                    if (!cmd.canFlyCombat) {
                        player.sendMessage("§9§lASB §8» §4Você está em combate. Espere em torno de 30 segundos para voar novamente.");
                        return true;
                    }
                    if (player.getAllowFlight()) {
                        player.sendMessage(String.format("§9§lASB §8» §cBoost de voar desativado. O booster tem %.1fmin restantes.", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft)));
                        player.setAllowFlight(false);
                    } else {
                        player.sendMessage(String.format("§9§lASB §8» §aBoost de voar ativado. O booster tem %.1fmin restantes.", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft)));
                        player.setAllowFlight(true);
                    }
                    return false;
                }else if (args[0].equalsIgnoreCase("broken")) {
                    if (cs instanceof Player)
                        cs.sendMessage("§9§lASB §8» §eVocê já minerou " + DataManager.get((Player) cs).blocksBroken + " blocos!");
                    else cs.sendMessage("fuck you you bitch I hate you fuck you ok");
                } else if (args[0].equalsIgnoreCase("cc")) {
                    if (!cs.hasPermission("asb.admin")) return true;
                    for (int i = 0; i < 500; i++) {
                        Bukkit.broadcastMessage("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    }
                    return false;
                } else if (args[0].equalsIgnoreCase("next")) {
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
                    return false;
                } else if (args[0].equalsIgnoreCase("reset") && cs instanceof Player) {
                    if (!cs.hasPermission("asb.admin")) return true;
                    final Player csP = (Player) cs;
                    if (cmd.waitingForConf.contains(csP.getUniqueId())) {
                        csP.sendMessage("§9§lASB §8» §cPlease type in chat, YES or NO");
                        return true;
                    }
                    cmd.waitingForConf.add(csP.getUniqueId());
                    csP.sendMessage("§9§lASB §8» §cAre you SURE you want to RESET THE PROGRESS? This will RESET EVERYTHING YOU HAVE DONE.\nIncluding:\nMissions;\nBlocks Broken;\nPhase\nPlease type in YES if you're REALLY SURE. (if yes reset world too)");
                    return false;
                }else if (args[0].equalsIgnoreCase("rm")) {
                    if (!cs.hasPermission("asb.admin")) return true;
                    final Player csP = (Player) cs;
                    csP.chat("/kill @e[type=item]");
                }
            } else if (args.length >= 2 && args[0].equalsIgnoreCase("spam")) {
                if (!(cs instanceof Player)) {
                    return true;
                }
                final Player player = (Player) cs;
                final StringBuilder sb = new StringBuilder(args[1]);
                for (int i = 2; i < args.length; ++i) {
                    sb.append(' ').append(args[i]);
                }
                for (int i = 0; i < 10; ++i) {
                    player.chat(sb.toString());
                }
                return false;
            }

            if (args.length == 1 && cs instanceof Player && args[0].equalsIgnoreCase("recipes")) {
                ((Player)cs).openInventory(new TheGUI().create());
            }

            if (!cs.hasPermission("asb.admin")) return true;

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("setblocks")) {
                    try {
                        int i = Integer.parseInt(args[1]); // ex
                        if (Database.getBlocksNeededForNewPhase() < 0) {
                            cs.sendMessage("could not fetch blocks needed to make calculations fuck you");
                            return true;
                        }
                        if (i > Database.getBlocksNeededForNewPhase()) {
                            i = Database.getBlocksNeededForNewPhase();
                            cs.sendMessage("§aSetting blocks to " + Database.getBlocksNeededForNewPhase() + " (initially set too big for phase support).");
                        }
                        SBMain.totalGlobalCobblestoneBroken = i;
                    }catch (NumberFormatException ex) {
                        cs.sendMessage("bro ur dumb put integer fuck u");
                    }
                }
            }
        }
        return false;
    }

    public class TheGUI extends CustomInventory {
        @Override
        public Inventory create() {
            final Inventory inv = Bukkit.createInventory(null, 27, "§aReceitas");

            final ItemStack istack = new ItemStack(Material.GOLD_NUGGET);
            final ItemMeta istackm = istack.getItemMeta();
            istackm.setDisplayName("§eComo criar as farms");
            istackm.setLore(
                    Arrays.asList(
                            " ",
                            " §7Pegue todos os §bitens§7 da §areceita§7,",
                            " §7depois jogue no §8chão§7. O SkyBlock vai",
                            " §aautomaticamente §7criar a farm para você.",
                            " §cCuidado para não fazer isto perto do §4void§c!",
                            " "
                    )
            );

            istack.setItemMeta(istackm);
            inv.setItem(4, istack);

            final ItemStack c1 = new ItemStack(Material.COPPER_INGOT);
            final ItemStack c2 = new ItemStack(Material.IRON_INGOT);
            final ItemStack c3 = new ItemStack(Material.GOLD_INGOT);
            final ItemStack c4 = new ItemStack(Material.DIAMOND);
            final ItemStack c5 = new ItemStack(Material.NETHERITE_INGOT);

            final ItemMeta c1m = c1.getItemMeta();
            final ItemMeta c2m = c2.getItemMeta();
            final ItemMeta c3m = c3.getItemMeta();
            final ItemMeta c4m = c4.getItemMeta();
            final ItemMeta c5m = c5.getItemMeta();

            c1m.setDisplayName("§aFarm de Animais do Overworld");
            c2m.setDisplayName("§aFarm de Mobs do Overworld");
            c3m.setDisplayName("§bFarm de Mobs do Nether");
            c4m.setDisplayName("§bFarm de Todos os Mobs");
            c5m.setDisplayName("§8Farm de Todos os Mobs e Animais");

            final List<String> baseLore = Arrays.asList(
                    " ",
                    " §eReceita: "
            );

            final List<String> lore1 = new ArrayList<>(baseLore);
            final List<String> lore2 = new ArrayList<>(baseLore);
            final List<String> lore3 = new ArrayList<>(baseLore);
            final List<String> lore4 = new ArrayList<>(baseLore);
            final List<String> lore5 = new ArrayList<>(baseLore);

            lore1.add(" §612x §7Flint");
            lore1.add(" §630x §fIron Ingot");
            lore1.add(" §61x §9Iron Sword");
            lore1.add(" ");

            lore2.add(" §612x §cCopper Ingot");
            lore2.add(" §642x §fIron Ingot");
            lore2.add(" §65x §bDiamond");
            lore2.add(" §61x §9Iron Sword");
            lore2.add(" ");

            lore3.add(" §612x §eGold Ingot");
            lore3.add(" §618x §5Obsidian");
            lore3.add(" §61x §8Wither Skeleton Head");
            lore3.add(" ");

            lore4.add(" §610x §8Netherite Ingot");
            lore4.add(" §624x §bDiamond");
            lore4.add(" §62x §8Wither Skeleton Head");
            lore4.add(" ");

            lore5.add(" §67x §8Netherite Block");
            lore5.add(" §610x §bDiamond Block");
            lore5.add(" §62x §dDragon Head");
            lore5.add(" ");

            c1m.setLore(lore1);
            c2m.setLore(lore2);
            c3m.setLore(lore3);
            c4m.setLore(lore4);
            c5m.setLore(lore5);

            c1.setItemMeta(c1m);
            c2.setItemMeta(c2m);
            c3.setItemMeta(c3m);
            c4.setItemMeta(c4m);
            c5.setItemMeta(c5m);

            inv.setItem(11, c1);
            inv.setItem(12, c2);
            inv.setItem(13, c3);
            inv.setItem(14, c4);
            inv.setItem(15, c5);

            final ItemStack is = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

            for (int i = 0; i < 27; i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getAmount() == 0 || inv.getItem(i).getType() == Material.AIR) inv.setItem(i, is);
            }
            return inv;
        }
    }

    static {
        cmd.canFlyCombat = true;
        cmd.waitingForConf = new ArrayList<UUID>();
    }
}
