package net.hypixel.skyblock.item.armor.vanilla.leather;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class LeatherHelmet implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Leather Helmet";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.HELMET;
    }

    @Override
    public double getBaseDefense() {
        return 5.0;
    }
}
