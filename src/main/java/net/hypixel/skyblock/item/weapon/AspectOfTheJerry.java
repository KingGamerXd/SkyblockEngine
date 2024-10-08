package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.*;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.item.*;

public class AspectOfTheJerry implements ToolStatistics, MaterialFunction, Ability {
    @Override
    public String getAbilityName() {
        return "Parley";
    }

    @Override
    public String getAbilityDescription() {
        return "Release your inner Jerry.";
    }

    @Override
    public int getAbilityCooldownTicks() {
        return 100;
    }

    @Override
    public void onAbilityUse(final Player player, final SItem sItem) {
        player.playSound(player.getLocation(), Sound.VILLAGER_IDLE, 1.0f, 1.0f);
    }

    @Override
    public int getManaCost() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "Aspect of the Jerry";
    }

    @Override
    public int getBaseDamage() {
        return 1;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }
}
