package com.atom.skyblock.farms;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.data.DataManager;
import com.atom.skyblock.data.PlayerData;
import com.atom.skyblock.ultilidadesfodas.MathAndRNG;
import dev.joel.bukkitutil.BukkitUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FarmManager {

    /*
        Will later be used to define ownership of Farms.
     */
    public static CopyOnWriteArrayList<Farm> allFarms = new CopyOnWriteArrayList<>();

    public static ConcurrentHashMap<Farm, List<ArmorStand>> armorStands = new ConcurrentHashMap<>();

    /*
        Should not add to allFarms due to it already being loaded.
        This is called on player join.
     */
    public static CopyOnWriteArrayList<Farm> updateFarms(final Player player) {
        /* final JSONObject json = SBMain.INSTANCE.idb.getJsonObject(player);
        if (json.isEmpty()) return new CopyOnWriteArrayList<>();
        final JSONArray array = json.getJSONArray("farms");
         */
        final CopyOnWriteArrayList<Farm> cowar_d = new CopyOnWriteArrayList<>();
        /* for (final Object objectIn : array) {
            final JSONObject obj = (JSONObject) (objectIn);
            final String[] parsedLoc = obj.getString("parsed_loc").split(" ");
            final String parsedEType = obj.getString("etype");

            final double x = Double.parseDouble(parsedLoc[0]);
            final double y = Double.parseDouble(parsedLoc[1]);
            final double z = Double.parseDouble(parsedLoc[2]);
            final World world = Bukkit.getWorld(parsedLoc[3]);

            final Location loc = new Location(world, x, y, z);

            final Farm.EntitiesType etype = Farm.EntitiesType.fromString(parsedEType);

            cowar_d.add(new Farm(loc, etype, player));
        } */


        for (final Farm farmIn : allFarms) {
            if (farmIn.owner.getUniqueId().toString().equals(player.getUniqueId().toString())) {
                BukkitUtil.send("adding farm for " + player.getName() + " at " + farmIn.location + ".");
                cowar_d.add(farmIn); /* this could fix it! */
            }
        }
        return cowar_d;
    }

    /*
        This method should update the farms' state. It does that ASYNCHRONOUSLY. (could cause issues).
        Example: Generating mobs.
        Will be called every few seconds.
        TODO: works but needs fixing
    */
    public static void updateAllFarmsState() {
        for (final Farm farm : allFarms) {
            if (farm.mobSpawned) {
                // this might cause issues with async
                if (!farm.lastEntity.isDead()) {
                    continue;
                }
                else {
                    farm.mobSpawned = false;
                }
            }
            int rng1 = MathAndRNG.generateInteger(4);

            if (rng1 == 2) {
                EntityType etype = null;
                final LivingEntity[] entity = {null};
                Farm.EntitiesType type = farm.spawnType;
                if (type == Farm.EntitiesType.ALL) {
                    final int rng2 = MathAndRNG.generateInteger(2); // ow, nether
                    if (rng2 == 1) type = Farm.EntitiesType.OVERWORLD;
                    else type = Farm.EntitiesType.NETHER;
                } else if (type == Farm.EntitiesType.ALL_NON_DANGER) {
                    final int rng2 = MathAndRNG.generateInteger(3); // ow, ow animals, nether
                    switch (rng2) {
                        case 1:
                            type = Farm.EntitiesType.OVERWORLD;
                            break;
                        case 2:
                            type = Farm.EntitiesType.OVERWORLD_NON_DANGER;
                            break;
                        case 3:
                            type = Farm.EntitiesType.NETHER;
                            break;
                    }
                }
                switch (type) {
                    case OVERWORLD:
                        rng1 = MathAndRNG.generateInteger(13); // SKEL, ZOMB, CREEPER, SPIDER 4, CAVE SPIDER 5, SLIME 6, HUSK 7, SFISH 8, PHANTOM 9, IGOLEM 10, RAVEGER 11, PILLAGER 12, WITCH 13,
                        switch (rng1) {
                            case 1:
                                etype = EntityType.SKELETON;
                                break;
                            case 2:
                                etype = EntityType.ZOMBIE;
                                break;
                            case 3:
                                etype = EntityType.CREEPER;
                                break;
                            case 4:
                                etype = EntityType.SPIDER;
                                break;
                            case 5:
                                etype = EntityType.CAVE_SPIDER;
                                break;
                            case 6:
                                etype = EntityType.SLIME;
                                break;
                            case 7:
                                etype = EntityType.HUSK;
                                break;
                            case 8:
                                etype = EntityType.SILVERFISH;
                                break;
                            case 9:
                                etype = EntityType.PHANTOM;
                                break;
                            case 10:
                                etype = EntityType.IRON_GOLEM;
                                break;
                            case 11:
                                etype = EntityType.RAVAGER;
                                break;
                            case 12:
                                etype = EntityType.PILLAGER;
                                break;
                            case 13:
                                etype = EntityType.WITCH;
                                break;
                        }
                        break;
                    case OVERWORLD_NON_DANGER:
                        rng1 = MathAndRNG.generateInteger(11); // COW, PIG, SHEEP, CHICKEN, HORSE, LLAMA 6, MOOSHR 7, RABBIT 8, WOLF 9, PBEAR 10, PANDA 11
                        switch (rng1) {
                            case 1:
                                etype = EntityType.COW;
                                break;
                            case 2:
                                etype = EntityType.PIG;
                                break;
                            case 3:
                                etype = EntityType.SHEEP;
                                break;
                            case 4:
                                etype = EntityType.CHICKEN;
                                break;
                            case 5:
                                etype = EntityType.HORSE;
                                break;
                            case 6:
                                etype = EntityType.LLAMA;
                                break;
                            case 7:
                                etype = EntityType.MUSHROOM_COW;
                                break;
                            case 8:
                                etype = EntityType.RABBIT;
                                break;
                            case 9:
                                etype = EntityType.WOLF;
                                break;
                            case 10:
                                etype = EntityType.POLAR_BEAR;
                                break;
                            case 11:
                                etype = EntityType.PANDA;
                                break;
                        }
                        break;
                    case NETHER:
                        rng1 = MathAndRNG.generateInteger(12); // STRIDER 1, PIGLIN 2, ZOGLIN 3, HOGLIN 4, WSKEL 5, MAGMA 6, ZOMBIE PIGLIN 7, STRIDER 8, BLAZE 9, SKEL 10, ENDERMEN 11
                        switch (rng1) {
                            case 1:
                                etype = EntityType.STRIDER;
                                break;
                            case 2:
                                etype = EntityType.PIGLIN;
                                break;
                            case 3:
                                etype = EntityType.ZOGLIN;
                                break;
                            case 4:
                                etype = EntityType.HOGLIN;
                                break;
                            case 5:
                                etype = EntityType.WITHER_SKELETON;
                                break;
                            case 6:
                                etype = EntityType.MAGMA_CUBE;
                                break;
                            case 7:
                                etype = EntityType.ZOMBIFIED_PIGLIN;
                                break;
                            case 8:
                                etype = EntityType.SKELETON_HORSE;
                                break;
                            case 9:
                                etype = EntityType.BLAZE;
                                break;
                            case 10:
                                etype = EntityType.SKELETON;
                                break;
                            case 11:
                                etype = EntityType.ENDERMAN;
                                break;
                            case 12:
                                etype = EntityType.PIGLIN_BRUTE;
                                break;
                        }
                        break;
                }

                if (farm.spawnType.name().contains("ALL")) {
                    // rare spawns
                    final int rng2 = MathAndRNG.generateInteger(50);
                    if (rng2 == 4) {
                        rng1 = MathAndRNG.generateInteger(farm.spawnType == Farm.EntitiesType.ALL ? 3 : 4);
                        switch (rng1) {
                            case 1:
                                etype = EntityType.ELDER_GUARDIAN;
                                break;
                            case 2:
                                etype = EntityType.SHULKER;
                                break;
                            case 3:
                                etype = EntityType.VINDICATOR;
                                break;
                            case 4: // only the RAREST farm
                                etype = EntityType.WITHER;
                                break;
                        }

                    }
                }

                if (etype != null) {
                    EntityType finalEtype = etype;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            BukkitUtil.send("trying to spawn a mob: " + finalEtype.name());
                            entity[0] = (LivingEntity) farm.location.getWorld().spawnEntity(farm.location.clone().add(0.5, 1, 0.5), finalEtype);
                            entity[0].setAI(false);
                            entity[0].setCustomName("_farmmob" + MathAndRNG.generateInteger(2147483630));
                            entity[0].setCustomNameVisible(false);
                            farm.lastEntity = entity[0];
                            for (final Entity e : farm.getNearbyEntities(10, 10, 10)) {
                                if (e instanceof Player) {
                                    ((Player)e).playSound(entity[0].getLocation(), Sound.ENTITY_ITEM_PICKUP, 3F, 0.65F);
                                    ((Player)e).playEffect(entity[0].getLocation(), Effect.SMOKE, 10);
                                }
                            }
                        }
                    }.runTask(SBMain.INSTANCE);
                    farm.mobSpawned = true;
                }
            }

        }

    }

    /*
        Method should be used when placing a FARM.
        **DO NOT** use for when you want to add a database retrieved farm.
        All possible farms will be stored within "allFarms" COW Array List.
     */
    public static void addFarm(final Player player, final Farm farm) {
        final PlayerData data = DataManager.get(player);
        data.ownedFarms.add(farm); // will be later turned into a JSON file when stored.
        allFarms.add(farm);
        armorStands.put(farm, new ArrayList<>());
        addAS(farm);
    }

    /*
        Method should be used when breaking a FARM. (If player is owner, that is.)
     */
    public static void removeFarm(final Player player, final Farm farm) {
        final PlayerData data = DataManager.get(player);
        data.ownedFarms.remove(farm);
        allFarms.remove(farm);
        removeAS(farm);
    }

    public static void loadOfflineFarms() throws SQLException {
        final PreparedStatement prp = SBMain.INSTANCE.idb.sf.con.prepareStatement("SELECT * FROM `ass`");
        final ResultSet rs = prp.executeQuery();
        int i = 0;
        int _i = 0;
        while (rs.next()) {
            final String str = rs.getString("farms");
            final JSONObject obj = new JSONObject(str);
            if (obj.isEmpty()) continue;
            _i++;
            final JSONArray array = obj.getJSONArray("farms");
            for (final Object objIn : array) {
                i++;
                final JSONObject indObj = (JSONObject) objIn;
                final String[] parsedLoc = indObj.getString("parsed_loc").split(" ");
                final String parsedEType = indObj.getString("etype");

                final double x = Double.parseDouble(parsedLoc[0]);
                final double y = Double.parseDouble(parsedLoc[1]);
                final double z = Double.parseDouble(parsedLoc[2]);
                final World world = Bukkit.getWorld(parsedLoc[3]);

                final Location loc = new Location(world, x, y, z);

                final Farm.EntitiesType etype = Farm.EntitiesType.fromString(parsedEType);

                final Farm farm = new Farm(loc, etype, Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("uuid"))));

                addAS(farm);
                allFarms.add(farm);
            }
        }

        BukkitUtil.send("§aLoaded " + i + " placed farms from " + _i + " players in total.");

        new BukkitRunnable() {
            @Override
            public void run() {
                updateAllFarmsState();
            }
        }.runTaskTimerAsynchronously(SBMain.INSTANCE, 0L, 38L);
    }

    private static void addAS(final Farm farm) {
        for (final Entity e : farm.getNearbyEntities(1, 6, 1)) {
            if (e instanceof ArmorStand && !((ArmorStand)e).isVisible()) return;
        }
        ArmorStand as = (ArmorStand) farm.location.getWorld().spawnEntity(farm.location.clone().add(0.5, 3, 0.5), EntityType.ARMOR_STAND); //Spawn the ArmorStand

        as.setGravity(false); //Make sure it doesn't fall
        as.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
        as.setCustomName("§c§lFARM DE MOBS"); //Set this to the text you want
        as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
        as.setVisible(false); //Makes the ArmorStand invisible

        ArmorStand _as = (ArmorStand) farm.location.getWorld().spawnEntity(farm.location.clone().add(0.5, 2.7, 0.5), EntityType.ARMOR_STAND); //Spawn the ArmorStand

        _as.setGravity(false); //Make sure it doesn't fall
        _as.setCanPickupItems(false); //I'm not sure what happens if you leave this _as it is, but you might _as well disable it
        _as.setCustomName("§fTipo: " + getType(farm)); //Set this to the text you want
        _as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
        _as.setVisible(false); //Makes the ArmorStand invisible

        ArmorStand __as = (ArmorStand) farm.location.getWorld().spawnEntity(farm.location.clone().add(0.5, 2.4, 0.5), EntityType.ARMOR_STAND); //Spawn the ArmorStand

        __as.setGravity(false); //Make sure it doesn't fall
        __as.setCanPickupItems(false); //I'm not sure what happens if you leave this __as it is, but you might __as well disable it
        __as.setCustomName("§fDono: " + farm.owner.getName()); //Set this to the text you want
        __as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
        __as.setVisible(false); //Makes the ArmorStand invisible

        _as.setLeftLegPose(new EulerAngle(0, 1, 0));
        _as.setRightLegPose(new EulerAngle(0, 1, 0));

        as.setLeftLegPose(new EulerAngle(0, 1, 0));
        as.setRightLegPose(new EulerAngle(0, 1, 0));

        __as.setLeftLegPose(new EulerAngle(0, 1, 0));
        __as.setRightLegPose(new EulerAngle(0, 1, 0));

        final List<ArmorStand> asls = new ArrayList<>();
        asls.add(as); asls.add(_as); asls.add(__as);

        armorStands.put(farm, asls);
    }

    private static String getType(final Farm farm) {
        switch (farm.spawnType) {
            case OVERWORLD:
                return "Mobs do Overworld";
            case OVERWORLD_NON_DANGER:
                return "Animais do Overworld";
            case NETHER:
                return "Mobs do Nether";
            case ALL:
                return "Todos os Mobs";
            case ALL_NON_DANGER:
                return "Todos os Mobs e Animais";
        }
        return "";
    }

    private static void removeAS(final Farm farm) {
        if (!armorStands.containsKey(farm) || armorStands.get(farm) == null || armorStands.get(farm).isEmpty()) return;
        for (final Entity e : armorStands.get(farm)) {
            e.remove();
        }
        for (final Entity e : farm.getNearbyEntities(1, 6, 1)) {
            if (e instanceof ArmorStand && !((ArmorStand)e).isVisible()) {
                e.remove();
            }
        }
        armorStands.put(farm, new ArrayList<>());
    }

    public static void removeAllAS() {
        for (final Farm farmIn : allFarms) {
            removeAS(farmIn);
        }
    }
}
