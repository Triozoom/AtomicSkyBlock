package com.atom.skyblock.events.pu;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.powerups.impl.BoosterItem;
import com.atom.skyblock.powerups.PowerupManager;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class InteractingEvent implements Listener {

    private BukkitTask runCobble = null;
    private BukkitTask runFlight = null;
    private BukkitTask runHaste = null;

    @EventHandler
    public void on(final PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem() == null) return;
            if (e.getItem().getItemMeta() == null) return;
            if (e.getItem().getItemMeta().getDisplayName() == null) return;
            final String dn = e.getItem().getItemMeta().getDisplayName();
            if (dn.startsWith("§6§lACTIVATE")) {
                if (BoosterItem.verify(e.getItem())) {
                    final BoosterActivateEvent ev = new BoosterActivateEvent(e.getPlayer(), BoosterActivateEvent.BoosterType.COMMON_STONE);
                    Bukkit.getPluginManager().callEvent(ev);
                    final float f = BoosterItem.returnBoostAmountFromIs(e.getItem());
                    PowerupManager.cobbleBoost = f;
                    if (runCobble != null && PowerupManager.cobbleBoostTimeLeft > 0) {
                        PowerupManager.cobbleBoostTimeLeft += 36000L;
                        Bukkit.broadcastMessage("\n§9§lASB §7» §fUm booster de " + f + String.format("x de cobblestone foi ativado por mais 30 minutos! Tem no total %.1fmin de tempo.\n", MathAndRNG.turnIntoMinutes(PowerupManager.cobbleBoostTimeLeft)));
                        for (final Player x : Bukkit.getOnlinePlayers()) {
                            x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de %.1fx.", e.getPlayer().getName(), f));
                            x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                        }
                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                        return;
                    }
                    PowerupManager.cobbleBoostTimeLeft = 36000L;
                    runCobble = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (PowerupManager.cobbleBoostTimeLeft <= 0) {
                                PowerupManager.cobbleBoost = 1.f;
                                PowerupManager.cobbleBoostTimeLeft = 0;
                                runCobble = null;
                                this.cancel();
                            }
                            PowerupManager.cobbleBoostTimeLeft -= 20L;
                        }
                    }.runTaskTimer(SBMain.getPlugin(SBMain.class), 0L, 20L);
                    Bukkit.broadcastMessage("\n§9§lASB §7» §fUm booster de " + f + "x de cobblestone foi ativado por 30 minutos!\n");
                    for (final Player x : Bukkit.getOnlinePlayers()) {
                        x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de %.1fx.", e.getPlayer().getName(), f));
                        x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                    }
                    e.getItem().setAmount(e.getItem().getAmount() - 1);
                } else if (FlightBoostItem.verify(e.getItem())) {
                    final BoosterActivateEvent ev = new BoosterActivateEvent(e.getPlayer(), BoosterActivateEvent.BoosterType.FLIGHT);
                    Bukkit.getPluginManager().callEvent(ev);
                    PowerupManager.flightBoostEnabled = true;
                    if (runFlight != null && PowerupManager.flightBoostTimeLeft > 0) {
                        PowerupManager.flightBoostTimeLeft += 18000L;
                        Bukkit.broadcastMessage(String.format("\n§9§lASB §7» §fUm booster de voar foi ativado por mais 15 minutos! Tem no total %.1fmin de tempo.\n", MathAndRNG.turnIntoMinutes(PowerupManager.flightBoostTimeLeft)));
                        for (final Player x : Bukkit.getOnlinePlayers()) {
                            x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de voar!", e.getPlayer().getName()));
                            x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                        }
                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                        return;
                    }
                    PowerupManager.flightBoostTimeLeft = 18000L;
                    runFlight = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (PowerupManager.flightBoostTimeLeft <= 0) {
                                PowerupManager.flightBoostEnabled = false;
                                PowerupManager.flightBoostTimeLeft = 0;
                                for (final Player x : Bukkit.getOnlinePlayers()) {
                                    x.setAllowFlight(false);
                                    x.sendMessage("§9§lASB §8» §cO boost de voar acabou!");
                                }
                                runFlight = null;
                                this.cancel();
                            }
                            // TODO: Improve efficiency by using switch clause
                            else if (PowerupManager.flightBoostTimeLeft == 12000L) {
                                Bukkit.broadcastMessage("§9§lASB §8» §eO boost de voar tem 10 minutos restantes!");
                            }else if (PowerupManager.flightBoostTimeLeft == 3600L) {
                                Bukkit.broadcastMessage("§9§lASB §8» §6O boost de voar tem 3 minutos restantes!");
                            }else if (PowerupManager.flightBoostTimeLeft == 1200L) {
                                Bukkit.broadcastMessage("§9§lASB §8» §cO boost de voar tem 1 minuto restante!");
                            }else if (PowerupManager.flightBoostTimeLeft == 200L) {
                                Bukkit.broadcastMessage("§9§lASB §8» §4O boost de voar tem apenas 10 segundos restantes!");
                            }
                            PowerupManager.flightBoostTimeLeft -= 20L;
                        }
                    }.runTaskTimer(SBMain.getPlugin(SBMain.class), 0L, 20L);
                    Bukkit.broadcastMessage("\n§9§lASB §7» §fUm booster de voar foi ativado por 15 minutos!\n");
                    for (final Player x : Bukkit.getOnlinePlayers()) {
                        x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de voar!", e.getPlayer().getName()));
                        x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                    }
                    e.getItem().setAmount(e.getItem().getAmount() - 1);

                // HASTE //
                }else if (HasteBoostItem.verify(e.getItem())) {
                    final BoosterActivateEvent ev = new BoosterActivateEvent(e.getPlayer(), BoosterActivateEvent.BoosterType.HASTE);
                    Bukkit.getPluginManager().callEvent(ev);
                    final float f = HasteBoostItem.returnBoostAmountFromIs(e.getItem());
                    PowerupManager.hasteBoost = f;
                    if (runHaste != null && PowerupManager.hasteBoostTimeLeft > 0) {
                        PowerupManager.hasteBoostTimeLeft += 12000L;
                        Bukkit.broadcastMessage("\n§9§lASB §7» §fUm booster de " + f + String.format("x de mining speed foi ativado por mais 10 minutos! Tem no total %.1fmin de tempo.\n", MathAndRNG.turnIntoMinutes(PowerupManager.hasteBoostTimeLeft)));
                        for (final Player x : Bukkit.getOnlinePlayers()) {
                            x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de %.1fx mining speed.", e.getPlayer().getName(), f));
                            x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                        }
                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                        return;
                    }
                    PowerupManager.hasteBoostTimeLeft = 12000L;
                    runHaste = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (PowerupManager.hasteBoostTimeLeft <= 0) {
                                PowerupManager.hasteBoost = 1.f;
                                PowerupManager.hasteBoostTimeLeft = 0;
                                this.cancel();
                                for (final Player x : Bukkit.getOnlinePlayers()) {
                                    x.removePotionEffect(PotionEffectType.FAST_DIGGING);
                                }
                                runHaste = null;
                                return;
                            }
                            for (final Player x : Bukkit.getOnlinePlayers()) {
                                if (!x.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                                    x.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1200, (int)f, false, false));
                                }
                            }
                            PowerupManager.hasteBoostTimeLeft -= 20L;
                        }
                    }.runTaskTimer(SBMain.getPlugin(SBMain.class), 0L, 20L);
                    Bukkit.broadcastMessage("\n§9§lASB §7» §fUm booster de " + f + "x de mining speed foi ativado por 10 minutos!\n");
                    for (final Player x : Bukkit.getOnlinePlayers()) {
                        x.sendTitle("§6§lUM BOOSTER FOI ATIVO", String.format("§fO jogador %s ativou um booster de %.1fx mining speed.", e.getPlayer().getName(), f));
                        x.playSound(x.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 0.3f);
                    }
                    e.getItem().setAmount(e.getItem().getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void ogfjnkawgjnkma(final InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase("§aReceitas")) e.setCancelled(true);
    }
}
