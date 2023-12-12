package com.atom.skyblock.achievements;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.api.VoidDeathEvent;
import dev.atom.atomachievements.achievement.i.Achievement;
import dev.atom.atomachievements.api.AchievementAPI;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimalKiller extends Achievement {
    @Override
    public String name() {
        return "Caçador";
    }

    @Override
    public String description() {
        return "Mate um animal.";
    }

    @Override
    public String reward() {
        return "1x Kit de Caça";
    }

    @Override
    public String id() {
        return "asb_a_7";
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
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.IRON_SWORD, 1));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.BOW, 1));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.CROSSBOW, 1));
        SBMain.globalCobblestoneLocation.getWorld().dropItemNaturally(SBMain.globalCobblestoneLocation, new ItemStack(Material.ARROW, 32));
        this.conclude(player);
    }

    @EventHandler
    public void onKillAnimal(final EntityDeathEvent ev) {
        if (ev.getEntity().getKiller() != null && ev.getEntity() instanceof Animals) {
            final Player player = ev.getEntity().getKiller();
            if (!AchievementAPI.hasCompleted(player, this)) {
                this.onComplete(player);
            }
        }
    }
}
