package net.hypixel.skyblock.entity.dragon.type;

import net.hypixel.skyblock.entity.dragon.Dragon;
import net.minecraft.server.v1_8_R3.World;
import org.apache.commons.lang3.Range;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class UnstableDragon extends Dragon {
    public UnstableDragon(World world) {
        super(world, 1.4, (Range<Double>) Range.between((Comparable) 0.6, (Comparable) 0.9), 300L);
    }

    public UnstableDragon() {
        this(((CraftWorld) Bukkit.getWorlds().get(0)).getHandle());
    }

    public String getEntityName() {
        return "Unstable Dragon";
    }

    public double getEntityMaxHealth() {
        return 9000000.0;
    }

    public double getDamageDealt() {
        return 1400.0;
    }
}
