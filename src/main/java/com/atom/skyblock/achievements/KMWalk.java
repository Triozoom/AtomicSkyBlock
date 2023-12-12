package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KMWalk extends Achievement {
    @Override
    public String name() {
        return "Seus primeiros passos";
    }

    @Override
    public String description() {
        return "Ande 1 quilometro.";
    }

    @Override
    public String reward() {
        return "Rapidez 2 por 1 minuto e Booster de Haste (2x)";
    }

    @Override
    public String id() {
        return "asb_a_12";
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
    public Material itemDisplay() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(2.F));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 3));
        this.conclude(player);
    }

    @EventHandler
    public void obc(final PlayerMoveEvent ev) {
        final Player player = ev.getPlayer();
        if (AchievementAPI.hasCompleted(player, this)) return;

        /*
            Calculates the total distance persecuted by using 2 deltas (sides of triangle) and hypotenuse (distance A to B) (omg trigonometry)
         */
        final double deltaXZ = Math.hypot((ev.getTo().getX() - ev.getFrom().getX()), (ev.getTo().getZ() - ev.getFrom().getZ())); // me when I do an anticheat thing in an non-anticheat plugin omg

        AchievementAPI.getAchievementInfo(this, player).progress+= deltaXZ/1000; // a block = 1M; using this we can define that in blocks distance we can divide it by 1000 to get a KM
        if (AchievementAPI.getAchievementInfo(this, player).progress >= 1) {
            this.onComplete(player);
        }
    }

}
