package net.hypixel.skyblock.features.reforge;

import net.hypixel.skyblock.item.GenericItemType;
import net.hypixel.skyblock.item.RarityValue;

import java.util.Collections;
import java.util.List;

public class Renowned implements Reforge {
    @Override
    public String getName() {
        return "Renowned";
    }

    @Override
    public RarityValue<Double> getStrength() {
        return new RarityValue<Double>(3.0, 4.0, 6.0, 8.0, 10.0, 12.0);
    }

    @Override
    public RarityValue<Double> getCritChance() {
        return new RarityValue<Double>(0.02, 0.04, 0.06, 0.08, 0.1, 0.12);
    }

    @Override
    public RarityValue<Double> getCritDamage() {
        return new RarityValue<Double>(0.03, 0.04, 0.06, 0.08, 0.1, 0.12);
    }

    @Override
    public RarityValue<Double> getIntelligence() {
        return new RarityValue<Double>(3.0, 4.0, 6.0, 8.0, 10.0, 12.0);
    }

    @Override
    public List<GenericItemType> getCompatibleTypes() {
        return Collections.singletonList(GenericItemType.ARMOR);
    }
}
