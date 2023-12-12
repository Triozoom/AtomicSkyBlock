package com.atom.skyblock.ultilidadesfodas;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.configuration.SBConfig;
import io.puharesource.mc.titlemanager.api.v2.*;

import java.io.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.sql.*;

public class Database
{
    private static TitleManagerAPI api;
    public static Connection con;
    
    public static void openConSQLite() {
        final File file = new File((SBMain.getPlugin(SBMain.class)).getDataFolder(), "skyblock.sqlite_db");
        final String url = "jdbc:sqlite:" + file;
        try {
            Class.forName("org.sqlite.JDBC");
            Database.con = DriverManager.getConnection(url);
            checkTable();
            Bukkit.getConsoleSender().sendMessage("§a[AtomicSkyBlock] SQLite iniciado com sucesso!");
        }
        catch (SQLException | ClassNotFoundException ex3) {
            Bukkit.getConsoleSender().sendMessage(ex3.getMessage());
            ex3.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§c[AtomicSkyBlock] Infelizmente sem a database o plugin não funciona! Teremos que desligar-lo.");
            Bukkit.getPluginManager().disablePlugin(SBMain.getPlugin(SBMain.class));
        }
    }
    
    public static void checkTable() {
        try {
            final PreparedStatement stm = Database.con.prepareStatement("CREATE TABLE IF NOT EXISTS `skyblock` (`blocksbroken` INT, `phase` INT)");
            stm.execute();
            Bukkit.getConsoleSender().sendMessage("§a[AtomicSkyBlock] Tabela foi carregada.");
            final PreparedStatement stm2 = Database.con.prepareStatement("INSERT INTO `skyblock` VALUES (?, ?)");
            stm2.setInt(1, 0);
            stm2.setInt(2, 1);
            stm2.execute();
        }
        catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(ex.getMessage());
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§c[AtomicSkyBlock] Sem a tabela não conseguimos estorar os valores na database, portanto, o plugin não funciona! Teremos que desligar-lo.");
            Bukkit.getPluginManager().disablePlugin(SBMain.getPlugin(SBMain.class));
        }
    }
    
    public static void closeConnection() {
        if (Database.con != null) {
            try {
                Database.con.close();
            }
            catch (SQLException ex) {
                Bukkit.getConsoleSender().sendMessage(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    public static void updateBlocks(final int blocksBroken) {
        try {
            final PreparedStatement stm = Database.con.prepareStatement("UPDATE `skyblock` SET blocksbroken=?");
            stm.setInt(1, blocksBroken);
            stm.execute();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getBlocks() {
        try {
            final PreparedStatement stm = Database.con.prepareStatement("SELECT * FROM `skyblock`");
            final ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("blocksbroken");
            }
            for (final Player bug : Bukkit.getOnlinePlayers()) {
                bug.sendMessage("§4§lBUG IN SKYBLOCK CODE");
            }
            return 0;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static void updatePhase(final int phase) {
        try {
            final PreparedStatement stm = Database.con.prepareStatement("UPDATE `skyblock` SET phase=?");
            stm.setInt(1, phase);
            stm.execute();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getPhase() {
        try {
            final PreparedStatement stm = Database.con.prepareStatement("SELECT * FROM `skyblock`");
            final ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("phase");
            }
            for (final Player bug : Bukkit.getOnlinePlayers()) {
                bug.sendMessage("§4§lBUG IN SKYBLOCK CODE");
            }
            return 0;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static void fullReset() {
        try {
            SBMain.totalGlobalCobblestoneBroken = 0;
            SBMain.phase = 1;
            final PreparedStatement stm = Database.con.prepareStatement("UPDATE `skyblock` SET blocksbroken=0");
            final PreparedStatement stm2 = Database.con.prepareStatement("UPDATE `skyblock` SET phase=1"); /* what the fuck did I do here bro. literally making the plugin unusable if you reset and then log off without getting to a new phase. dumbass. */
            stm.execute();
            stm2.execute();
            for (final Player all : Bukkit.getOnlinePlayers()) {
                Database.api.sendTitles(all, "§c§lSKYBLOCK", "§cUm reset foi feito.");
                Database.api.sendActionbar(all, "§eSe preparem para o jogo novamente!");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int getBlocksNeededForNewPhase() {
        switch (SBMain.phase) {
            case 1:
                return SBConfig.blocksBrokenPhase2;
            case 2:
                return SBConfig.blocksBrokenPhase3;
            case 3:
                return SBConfig.blocksBrokenPhase4;
            case 4:
                return SBConfig.blocksBrokenPhase5;
            case 5:
                return SBConfig.blocksBrokenPhase6;
            case 6:
                return -1;
            default: return -2;
        }
    }
    
    static {
        Database.api = (TitleManagerAPI)Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        Database.con = null;
    }
}
