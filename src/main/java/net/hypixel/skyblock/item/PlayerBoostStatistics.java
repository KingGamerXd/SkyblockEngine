package net.hypixel.skyblock.item;

public interface PlayerBoostStatistics extends MaterialStatistics {
    default int getBaseDamage() {
        return 0;
    }

    default double getBaseStrength() {
        return 0.0;
    }

    default double getBaseCritChance() {
        return 0.0;
    }

    default double getBaseCritDamage() {
        return 0.0;
    }

    default double getBaseMagicFind() {
        return 0.0;
    }

    default double getBaseIntelligence() {
        return 0.0;
    }

    default double getBaseSpeed() {
        return 0.0;
    }

    default double getBaseHealth() {
        return 0.0;
    }

    default double getBaseDefense() {
        return 0.0;
    }

    default double getBaseAttackSpeed() {
        return 0.0;
    }

    default double getBaseFerocity() {
        return 0.0;
    }
}
