package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.FlightBoostItem;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KMFly extends Achievement {
    @Override
    public String name() {
        return "Seus primeiros... voos?";
    }

    @Override
    public String description() {
        return "Voe 300 Metros.";
    }

    @Override
    public String reward() {
        return "1x Booster de Voar";
    }

    @Override
    public String id() {
        return "asb_a_new_2";
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
        return Material.NETHER_STAR;
    }

    @Override
    public Type type() {
        return Type.ACTION;
    }

    @Override
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, FlightBoostItem.returnItemStack());
        this.conclude(player);
    }

    @EventHandler
    public void obc(final PlayerMoveEvent ev) {
        final Player player = ev.getPlayer();
        if (AchievementAPI.hasCompleted(player, this)) return;
        if (!player.isFlying()) return;

        /*
            Calculates the total distance persecuted by using 2 deltas (sides of triangle) and hypotenuse (distance A to B) (omg trigonometry)
         */
        final double deltaXZ = Math.hypot((ev.getTo().getX() - ev.getFrom().getX()), (ev.getTo().getZ() - ev.getFrom().getZ())); // me when I do an anticheat thing in an non-anticheat plugin omg

        AchievementAPI.getAchievementInfo(this, player).progress+= deltaXZ/300;
        if (AchievementAPI.getAchievementInfo(this, player).progress >= 1) {
            this.onComplete(player);
        }
    }

}
