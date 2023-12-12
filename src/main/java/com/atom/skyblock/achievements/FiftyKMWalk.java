package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.powerups.impl.HasteBoostItem;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FiftyKMWalk extends Achievement {
    @Override
    public String name() {
        return "Corredor Profissional";
    }

    @Override
    public String description() {
        return "Ande 50 quilometros.";
    }

    @Override
    public String reward() {
        return "Booster de Haste (5x) e Picareta Profissional";
    }

    @Override
    public String id() {
        return "asb_a_14";
    }

    @Override
    public Material itemDisplay() {
        return Material.DIAMOND_BOOTS;
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
    public void onComplete(Player player) {
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, HasteBoostItem.returnItemStack(5.F));
        final ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
        is.addEnchantment(Enchantment.DIG_SPEED, 2);
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, is);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle("§3§lCONQUISTA LENDÁRIA FOI COMPLETA", "§fO jogador" + player.getName() + " completou uma conquista LENDÁRIA!");
            online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 3F, 0.65F);
            online.playSound(online.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 3F, 1.3F);
        }
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

        AchievementAPI.getAchievementInfo(this, player).progress+= deltaXZ/50000; // a block = 1M; using this we can define that in blocks distance we can divide it by 10000 to get 10 KM
        if (AchievementAPI.getAchievementInfo(this, player).progress >= 1) {
            this.onComplete(player);
        }
    }

}
