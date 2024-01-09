package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.GlobalCobblestoneMineEvent;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.powerups.impl.BoosterItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import dev.joel.bukkitutil.BukkitUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AchievementTwo extends Achievement {
    @Override
    public String name() {
        return "Ima de Loot";
    }

    @Override
    public String description() {
        return "Ganha-se quando abre 120 Baús de Loot gerado pelo Bloco de Cobblestone.";
    }

    @Override
    public String reward() {
        return "2x Booster de Cobble (2x)";
    }

    @Override
    public String id() {
        return "asb_a_1b";
    }

    @Override
    public JavaPlugin source() {
        return SBMain.INSTANCE;
    }

    @Override
    public boolean showTitle() {
        return true;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public Material itemDisplay() {
        return Material.CHEST_MINECART;
    }

    @Override
    public AchievementRarity rarity() {
        return AchievementRarity.VERY_RARE;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(2.f));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, BoosterItem.returnItemStack(2.f));
        this.conclude(player);
    }

    @EventHandler
    public void onOpenChest(final PlayerInteractEvent ev) {
        if (!AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ev.getClickedBlock() != null &&
                        ev.getClickedBlock().getType().name().contains("CHEST") &&
                        ev.getClickedBlock().getLocation().distance(SBMain.globalCobblestoneLocation) < 0.5D) {
                    if (DataManager.get(ev.getPlayer()).openedChestSinceBlock) return;
                    DataManager.get(ev.getPlayer()).openedChestSinceBlock = true;
                    AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress += (double) 1 / 120;
                    if (AchievementAPI.getAchievementInfo(this, ev.getPlayer()).progress >= 1) {
                        this.onComplete(ev.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGC(final GlobalCobblestoneMineEvent ev) {
        DataManager.get(ev.getPlayer()).openedChestSinceBlock = false;
    }
}
