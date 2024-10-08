package net.hypixel.skyblock.entity;

public interface EntityStatistics {
    String getEntityName();

    double getEntityMaxHealth();

    double getDamageDealt();

    double getXPDropped();

    default SEntityEquipment getEntityEquipment() {
        return null;
    }

    default double getMovementSpeed() {
        return -1.0;
    }

    default int mobLevel() {
        return 0;
    }

    default boolean dealsTrueDamage() {
        return false;
    }

    default boolean hasNameTag() {
        return true;
    }

    default boolean removeWhenFarAway() {
        return false;
    }

    default boolean isVisible() {
        return true;
    }
}
