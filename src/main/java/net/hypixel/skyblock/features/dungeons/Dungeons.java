package net.hypixel.skyblock.features.dungeons;

import net.hypixel.skyblock.util.SLog;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;

import java.util.*;

public class Dungeons {
    private static Map<World, Dungeons> DUNGEONS_CACHE;
    public List<Player> players;
    public int witherKeys;
    public boolean bloodKey;
    public boolean isBloodCleared;
    public List<String> openedRooms;
    public int deaths;
    public int cryptsBlown;
    public int score;
    private final World world;
    private final UUID runUUID;

    public Dungeons(final World world, final List<Player> listPlayers) {
        this.players = new ArrayList<Player>();
        this.witherKeys = 0;
        this.bloodKey = false;
        this.isBloodCleared = false;
        this.deaths = 0;
        this.cryptsBlown = 0;
        this.score = 0;
        this.world = world;
        this.players = listPlayers;
        this.runUUID = UUID.randomUUID();
        Dungeons.DUNGEONS_CACHE.put(world, this);
    }

    public static Dungeons getDungeonsInstance(final World w) {
        if (Dungeons.DUNGEONS_CACHE.containsKey(w)) {
            return Dungeons.DUNGEONS_CACHE.get(w);
        }
        SLog.severe("Cannot find dungeons instance for this world.");
        return null;
    }

    public void spawnMobAt(final Location loc, final SEntityType type, final boolean starred, final UUID roomUUID) {
        final SEntity sentity = new SEntity(loc, type);
        sentity.setStarred(starred);
    }

    public void spawnMobWithCustomStats(final Location loc, final SEntityType type, final boolean starred, final double HP, final double damage) {
    }

    public void spawnKeyAt() {
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setWitherKeys(final int witherKeys) {
        this.witherKeys = witherKeys;
    }

    public int getWitherKeys() {
        return this.witherKeys;
    }

    public void setBloodKey(final boolean bloodKey) {
        this.bloodKey = bloodKey;
    }

    public boolean isBloodKey() {
        return this.bloodKey;
    }

    public void setBloodCleared(final boolean isBloodCleared) {
        this.isBloodCleared = isBloodCleared;
    }

    public boolean isBloodCleared() {
        return this.isBloodCleared;
    }

    public void setOpenedRooms(final List<String> openedRooms) {
        this.openedRooms = openedRooms;
    }

    public List<String> getOpenedRooms() {
        return this.openedRooms;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }

    public int getCryptsBlown() {
        return this.cryptsBlown;
    }

    public void setCryptsBlown(final int cryptsBlown) {
        this.cryptsBlown = cryptsBlown;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    static {
        Dungeons.DUNGEONS_CACHE = new HashMap<World, Dungeons>();
    }
}
