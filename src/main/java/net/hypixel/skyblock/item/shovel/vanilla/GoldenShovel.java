package net.hypixel.skyblock.item.shovel.vanilla;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class GoldenShovel implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Golden Shovel";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public int getBaseDamage() {
        return 15;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.TOOL;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SHOVEL;
    }
}
