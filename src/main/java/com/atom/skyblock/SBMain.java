package com.atom.skyblock;

import com.atom.skyblock.cmd.cmd;
import com.atom.skyblock.configuration.SBConfig;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.events.*;
import com.atom.skyblock.events.farming.FarmEvents;
import com.atom.skyblock.farms.FarmManager;
import com.atom.skyblock.papi.PAPI;
import com.atom.skyblock.ultilidadesfodas.Announcements;
import com.atom.skyblock.ultilidadesfodas.AtomAchievementsHook;
import com.atom.skyblock.ultilidadesfodas.Database;
import com.atom.skyblock.events.pu.InteractingEvent;
import com.atom.skyblock.ultilidadesfodas.IndividualDatabase;
import dev.atom.atomachievements.AtomAchievements;
import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.bukkitutil.sql.SQLFile;
import dev.joel.bukkitutil.sql.enums.UpdateType;
import lombok.SneakyThrows;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;

import java.io.File;
import java.util.Collection;

public final class SBMain extends JavaPlugin
{
    public static Location globalCobblestoneLocation;
    public static Block globalCobblestone;
    public static int phase;
    public static int totalGlobalCobblestoneBroken;

    public IndividualDatabase idb;

    public static boolean aAc = false;
    public boolean a = true;

    public static SBMain INSTANCE; // this dumbass didn't do this LMFAO

    @Override
    public void onLoad() {
        if (!BukkitUtil.isCreated()) BukkitUtil.create();
        if (this.getConfig() == null) AtomAchievements.canStart = false;
        if (!new File(getDataFolder(), "config.yml").exists()) AtomAchievements.canStart = false;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        if (!BukkitUtil.isLoaded()) BukkitUtil.INSTANCE.load();
        BukkitUtil.assign(this);
        Database.openConSQLite();
        idb = new IndividualDatabase();
        SBMain.totalGlobalCobblestoneBroken = Database.getBlocks();
        SBMain.phase = Database.getPhase();
        this.getCommand("asb").setExecutor(new cmd());
        if (this.getConfig().getString("globalCobblestone") == null || this.getConfig().getString("globalCobblestone").equalsIgnoreCase("unset")) {
            Bukkit.getConsoleSender().sendMessage("§4§lA cobblestone global não foi setada. Por favor use /asb set enquanto olha para um bloco para setar-lo como a cobblestone.");
            a = false;
            return;
        }
        final String bSplit = this.getConfig().getString("globalCobblestone");
        final String[] aSplit = bSplit.split(" ");
        World world = null;
        for (final World worlds : Bukkit.getWorlds()) {
            if (!worlds.getName().contains("the_end") && !worlds.getName().contains("nether")) {
                world = worlds;
            }
        }
        final double X = Double.parseDouble(aSplit[0]);
        final double Y = Double.parseDouble(aSplit[1]);
        final double Z = Double.parseDouble(aSplit[2]);
        SBMain.globalCobblestoneLocation = new Location(world, X, Y, Z);
        SBMain.globalCobblestone = SBMain.globalCobblestoneLocation.getBlock();

        BukkitUtil.INSTANCE.getConfigUtil().registerConfigurationSetting(SBConfig.class);

        /*
            Removing last restart "farm mobs" before continuing
         */
        for (final Entity entities : world.getEntities()) {
            if (entities instanceof LivingEntity) {
                if (entities.getCustomName() != null && entities.getCustomName().startsWith("_farmmob")) {
                    entities.remove();
                }
            }
        }

        FarmManager.loadOfflineFarms();

        BukkitUtil.INSTANCE.register(new MiningEvent());
        BukkitUtil.INSTANCE.register(new CombatEvent());
        BukkitUtil.INSTANCE.register(new ChattingEvent());
        BukkitUtil.INSTANCE.register(new OnUpdatePhase());
        BukkitUtil.INSTANCE.register(new MoveEvent());
        BukkitUtil.INSTANCE.register(new InteractingEvent());
        BukkitUtil.INSTANCE.register(new JoinEvent());
        BukkitUtil.INSTANCE.register(new FarmEvents());

        new BukkitRunnable() {
            public void run() {
                Database.updateBlocks(SBMain.totalGlobalCobblestoneBroken);
                Database.updatePhase(SBMain.phase);

                for (final Player playerIn : Bukkit.getOnlinePlayers()) {
                    idb.store(DataManager.get(playerIn));
                }

                Bukkit.getConsoleSender().sendMessage("§9[ASB - SQLITE] §fAtualização da base de dados realizada; Blocos quebrados: " + SBMain.totalGlobalCobblestoneBroken);
            }
        }.runTaskTimerAsynchronously(this, 20L, 600L);

        if (BukkitUtil.INSTANCE.isAPlugin("PlaceholderAPI")) {
            new PAPI().register();
        }else {
            BukkitUtil.send("§c[ASB] §cPlaceholderAPI isn't available, skipping...");
        }

        aAc = AtomAchievementsHook.start(); // cool bro

        new BukkitRunnable() {
            @Override
            public void run() {
                Collection<Entity> lE = globalCobblestoneLocation.getWorld().getNearbyEntities(globalCobblestoneLocation, 2, 2, 2);
                for (final Entity e : lE) {
                    if (e.getType().equals(EntityType.DROPPED_ITEM)) {
                        e.teleport(globalCobblestoneLocation.getBlock().getLocation().clone().add(0.5, 1, 0.5));
                    }
                }
            }
        }.runTaskTimer(this, 0L, 2L);

        Announcements.start();
    }
    
    public void onDisable() {
        if (!a) return;
        Database.updateBlocks(SBMain.totalGlobalCobblestoneBroken);
        Database.updatePhase(SBMain.phase);

        for (final Player playerIn : Bukkit.getOnlinePlayers()) {
            idb.store(DataManager.get(playerIn));
        }

        FarmManager.removeAllAS();

        Bukkit.getConsoleSender().sendMessage("§9[AtomicSkyBlock] §cSaindo... Temos " + SBMain.totalGlobalCobblestoneBroken + " blocos quebrados até agora. Tchau tchau!");
        Database.closeConnection();
        idb.sf.finishConnection();
    }
    
    static {
        SBMain.phase = 1;
        SBMain.totalGlobalCobblestoneBroken = 0;
    }
}
