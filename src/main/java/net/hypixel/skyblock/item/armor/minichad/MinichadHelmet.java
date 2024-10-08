package net.hypixel.skyblock.item.armor.minichad;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;

public class MinichadHelmet implements MaterialFunction, SkullStatistics, ToolStatistics {
    @Override
    public double getBaseStrength() {
        return 60.0;
    }

    @Override
    public double getBaseCritChance() {
        return 0.05;
    }

    @Override
    public double getBaseCritDamage() {
        return 0.25;
    }

    @Override
    public double getBaseIntelligence() {
        return 2.0;
    }

    @Override
    public double getBaseMagicFind() {
        return 0.05;
    }

    @Override
    public double getBaseSpeed() {
        return 0.02;
    }

    @Override
    public double getBaseHealth() {
        return 70.0;
    }

    @Override
    public double getBaseDefense() {
        return 50.0;
    }

    @Override
    public String getURL() {
        return "7953b6c68448e7e6b6bf8fb273d7203acd8e1be19e81481ead51f45de59a8";
    }

    @Override
    public String getDisplayName() {
        return "Minichad Helmet";
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
        return SpecificItemType.HELMET;
    }

    @Override
    public String getLore() {
        return null;
    }

    @Override
    public void load() {
        final ShapedRecipe recipe = new ShapedRecipe(SMaterial.HIDDEN_MINICHAD_HELMET);
        recipe.shape("123", "456", "789");
        recipe.set('1', SMaterial.HIDDEN_DIMOON_FRAG, 24);
        recipe.set('2', SMaterial.HIDDEN_DIMOON_FRAG, 24);
        recipe.set('3', SMaterial.HIDDEN_DIMOON_FRAG, 24);
        recipe.set('4', SMaterial.HIDDEN_DIMOON_FRAG, 24);
        recipe.set('5', SMaterial.HIDDEN_SHARD_DIAMOND, 1);
        recipe.set('6', SMaterial.HIDDEN_DIMOON_FRAG, 24);
    }
}
