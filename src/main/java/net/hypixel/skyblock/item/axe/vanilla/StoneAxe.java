package net.hypixel.skyblock.item.axe.vanilla;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class StoneAxe implements ToolStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Stone Axe";
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
        return SpecificItemType.AXE;
    }
}
