package com.atom.skyblock.ultilidadesfodas;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.data.PlayerData;
import com.atom.skyblock.farms.Farm;
import com.google.gson.JsonObject;
import dev.joel.bukkitutil.sql.SQLFile;
import dev.joel.bukkitutil.sql.enums.UpdateType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IndividualDatabase {

    public SQLFile sf;

    public IndividualDatabase() {
        sf = new SQLFile(SBMain.INSTANCE, UpdateType.JOIN_AND_QUIT, "individual.sqlite_db", "ass", "`uuid` TEXT, `blocksBroken` INT, `farms` TEXT");
    }

    public void store(final PlayerData data) {
        try {
            final PreparedStatement prp = sf.con.prepareStatement("SELECT * FROM `ass` WHERE uuid=?");
            prp.setString(1, data.player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (rs.next()) {
                // update
                final PreparedStatement update = sf.con.prepareStatement("UPDATE `ass` SET blocksBroken=?, farms=? WHERE uuid=?");
                update.setInt(1, data.blocksBroken);
                update.setString(3, data.player.getUniqueId().toString());
                update.setString(2, turnIntoString(data));
                update.execute();
            } else {
                // insert
                final PreparedStatement insert = sf.con.prepareStatement("INSERT INTO `ass` VALUES (?,?,?)");
                insert.setString(1, data.player.getUniqueId().toString());
                insert.setInt(2, data.blocksBroken);
                insert.setString(3 , "{}");
                insert.execute();
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String turnIntoString(final PlayerData data) {
        final JSONObject obj = new JSONObject();
        final JSONArray array = new JSONArray();
        for (final Farm farmIn : data.ownedFarms) {
            final JSONObject farmObj = new JSONObject();
            final Location loc = farmIn.location;
            farmObj.put("parsed_loc", loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getWorld().getName());
            farmObj.put("etype", farmIn.spawnType.parseable);
            array.put(farmObj);
        }
        obj.put("farms", array);
        return obj.toString();
    }

    public void createData(final Player player) {
        DataManager.add(player, new PlayerData(player, get(player)));
    }

    public int get(final Player player) {
        try {
            final PreparedStatement prp = sf.con.prepareStatement("SELECT * FROM `ass` WHERE uuid=?");
            prp.setString(1, player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (rs.next()) {
                return rs.getInt("blocksBroken");
            } else {
                player.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE, 1)); // bro has never been in server
                return 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }



    public String getJsonString(final Player player) {
        try {
            final PreparedStatement prp = sf.con.prepareStatement("SELECT * FROM `ass` WHERE uuid=?");
            prp.setString(1, player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (rs.next()) {
                return rs.getString("farms");
            } else {
                return "{}";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public JSONObject getJsonObject(final Player player) {
        final String str = getJsonString(player);
        return new JSONObject(str);
    }
}
