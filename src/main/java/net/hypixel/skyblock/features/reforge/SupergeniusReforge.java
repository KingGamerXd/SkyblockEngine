package net.hypixel.skyblock.features.reforge;

import net.hypixel.skyblock.item.RarityValue;

public class SupergeniusReforge implements Reforge {
    @Override
    public String getName() {
        return "Supergenius";
    }

    @Override
    public RarityValue<Double> getIntelligence() {
        return RarityValue.singleDouble(1000000.0);
    }
}
