package net.hypixel.skyblock.features.dungeons.blessing;

import net.hypixel.skyblock.SkyBlock;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlessingChest {
    public static final Map<Block, BlessingChest> CHEST_CACHE;
    private boolean opened;
    private boolean locked;
    private final Blessings type;
    private final byte state;
    private final Block chest;
    private final SkyBlock sse;

    public BlessingChest(Blessings type, Block chest, byte state) {
        this.sse = SkyBlock.getPlugin();
        this.type = type;
        this.state = state;
        this.locked = false;
        this.opened = false;
        this.chest = chest;
        CHEST_CACHE.put(chest, this);
        new BukkitRunnable() {
            public void run() {
                if (!CHEST_CACHE.containsKey(chest)) {
                    this.cancel();
                    return;
                }
                Collection<Entity> ce = chest.getWorld().getNearbyEntities(chest.getLocation(), 10.0, 10.0, 10.0);
                ce.removeIf(entity -> !(entity instanceof Player));
                if (0 < ce.size()) {
                    BlessingChest.this.show();
                } else {
                    BlessingChest.this.hide();
                }
            }
        }.runTaskTimer(this.sse, 0L, 1L);
    }

    public void open(Player opener) {
        if (!this.opened && !this.locked) {
            Blessings.openBlessingChest(this.chest, this.type, opener);
            this.opened = true;
            return;
        }
        if (this.locked) {
            opener.sendMessage(Sputnik.trans("&cThat chest is locked!"));
            return;
        }
        if (this.opened) {
            opener.sendMessage(Sputnik.trans("&cThe chest has already been searched!"));
        }
    }

    public void destroy() {
        this.chest.setType(Material.AIR);
        CHEST_CACHE.remove(this.chest);
    }

    public void hide() {
        this.chest.getLocation().getBlock().setType(Material.AIR);
    }

    public void show() {
        if (Material.CHEST != this.chest.getType()) {
            this.chest.getLocation().getBlock().setType(Material.CHEST);
            this.chest.setData(this.state);
            final Location chestLocation = this.chest.getLocation();
            if (this.isOpened()) {
                SUtil.delay(() -> {
                    // todo : fix it
                    final BlockPosition pos = new BlockPosition(chestLocation.getBlockX(), chestLocation.getBlockY(), chestLocation.getBlockZ());
                    final PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(pos, Blocks.CHEST, 1, 1);
                    for (final Player p : chestLocation.getWorld().getPlayers()) {
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                    }
                }, 1L);
            }
        }
    }

    public boolean isOpened() {
        return this.opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Blessings getType() {
        return this.type;
    }

    static {
        CHEST_CACHE = new HashMap<Block, BlessingChest>();
    }
}
