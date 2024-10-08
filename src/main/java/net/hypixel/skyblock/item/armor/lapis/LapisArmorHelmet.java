package net.hypixel.skyblock.item.armor.lapis;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class LapisArmorHelmet implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Lapis Armor Helmet";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
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
        return 25.0;
    }
}
