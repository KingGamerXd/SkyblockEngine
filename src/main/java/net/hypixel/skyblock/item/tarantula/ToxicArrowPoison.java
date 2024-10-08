package net.hypixel.skyblock.item.tarantula;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class ToxicArrowPoison implements MaterialStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Toxic Arrow Poison";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.ARROW_POISON;
    }
}
