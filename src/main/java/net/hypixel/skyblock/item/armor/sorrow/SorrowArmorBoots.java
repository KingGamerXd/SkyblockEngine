package net.hypixel.skyblock.item.armor.sorrow;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class SorrowArmorBoots implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Sorrow Boots";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.ARMOR;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOOTS;
    }

    @Override
    public double getBaseMagicFind() {
        return 0.05;
    }

    @Override
    public double getBaseDefense() {
        return 75.0;
    }
}
