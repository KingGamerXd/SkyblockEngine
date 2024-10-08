package net.hypixel.skyblock.item.armor.vanilla.chainmail;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class ChainmailLeggings implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Chainmail Chestplate";
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
        return SpecificItemType.LEGGINGS;
    }

    @Override
    public double getBaseDefense() {
        return 20.0;
    }
}
