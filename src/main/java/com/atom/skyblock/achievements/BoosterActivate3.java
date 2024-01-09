package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.BoosterActivateEvent;
import com.atom.skyblock.powerups.PowerupManager;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BoosterActivate3 extends Achievement {
    @Override
    public String name() {
        return "Para que esperar?";
    }

    @Override
    public String description() {
        return "Ative um booster que já estava ativo e adicione ao tempo de booster.";
    }

    @Override
    public String reward() {
        return "125 Blocos Globais e 3x Blocos de Iron";
    }

    @Override
    public String id() {
        return "asb_a_aba1b";
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
    public AchievementRarity rarity() {
        return AchievementRarity.UNCOMMON;
    }

    @Override
    public Material itemDisplay() {
        return Material.FEATHER;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.totalGlobalCobblestoneBroken+= 125;
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.IRON_BLOCK, 3));
        this.conclude(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onActivateBooster(final BoosterActivateEvent ev) {
        final boolean valid = (PowerupManager.flightBoostEnabled && ev.getType() == BoosterActivateEvent.BoosterType.FLIGHT) ||
                (PowerupManager.cobbleBoostTimeLeft > 0L && ev.getType() == BoosterActivateEvent.BoosterType.COMMON_STONE) ||
                (PowerupManager.hasteBoostTimeLeft > 0L && ev.getType() == BoosterActivateEvent.BoosterType.HASTE);
        if (valid && !AchievementAPI.hasCompleted(ev.getPlayer(), this)) {
            this.onComplete(ev.getPlayer());
        }
    }
}
