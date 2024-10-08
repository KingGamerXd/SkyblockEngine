package net.hypixel.skyblock.item.enchanted;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class EnchantedJungleWood implements EnchantedMaterialStatistics, MaterialFunction {
    @Override
    public String getDisplayName() {
        return "Enchanted Jungle Wood";
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
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public SMaterial getCraftingMaterial() {
        return SMaterial.JUNGLE_WOOD;
    }

    @Override
    public MaterialQuantifiable getResult() {
        return new MaterialQuantifiable(SMaterial.ENCHANTED_JUNGLE_WOOD);
    }
}
