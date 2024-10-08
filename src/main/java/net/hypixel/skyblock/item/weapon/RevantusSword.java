package net.hypixel.skyblock.item.weapon;

import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.item.*;
import net.hypixel.skyblock.util.Sputnik;

public class RevantusSword implements ToolStatistics, MaterialFunction {
    @Override
    public int getBaseDamage() {
        return 500;
    }

    @Override
    public double getBaseStrength() {
        return 150.0;
    }

    @Override
    public double getBaseCritDamage() {
        return 0.2;
    }

    @Override
    public double getBaseIntelligence() {
        return 100.0;
    }

    @Override
    public String getDisplayName() {
        return "Dark Claymore";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.LONGSWORD;
    }

    @Override
    public String getLore() {
        return Sputnik.trans("&oThis &othing &owas &otoo &obig &oand &olong &oto &obe &ocalled &oa &osword, &oit &owas &omore &olike &oa &olarge &oiron &opipe, &ocan &oswing &oup &oto &c&o6 &oblocks!");
    }

    @Override
    public void load() {
        final ShapedRecipe recipe = new ShapedRecipe(SMaterial.HIDDEN_REVANTUS_SWORD);
        recipe.shape("123", "456", "789");
        recipe.set('1', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('2', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('3', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('4', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('5', SMaterial.HIDDEN_SHARD_DIAMOND, 1);
        recipe.set('6', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('7', SMaterial.HIDDEN_DIMOON_FRAG, 64);
        recipe.set('8', SMaterial.HIDDEN_SHARD_DIAMOND, 1);
        recipe.set('9', SMaterial.HIDDEN_DIMOON_FRAG, 64);
    }
}
