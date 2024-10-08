package net.hypixel.skyblock.features.skill;

import org.bukkit.ChatColor;
import net.hypixel.skyblock.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArcherSkill extends Skill implements DungeonsSkill {
    public static final ArcherSkill INSTANCE;

    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String getAlternativeName() {
        return "{skip}";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("");
    }

    @Override
    public List<String> getLevelUpInformation(final int level, final int lastLevel, final boolean showOld) {
        return Collections.singletonList("");
    }

    @Override
    public boolean hasSixtyLevels() {
        return false;
    }

    @Override
    public void onSkillUpdate(final User user, final double previousXP) {
        super.onSkillUpdate(user, previousXP);
    }

    @Override
    public List<String> getPassive() {
        final List<String> t = new ArrayList<String>();
        t.add("Doubleshot");
        t.add("Bone Plating");
        t.add("Bouncy Arrows" + ChatColor.RED + " Soon!");
        return t;
    }

    @Override
    public List<String> getOrb() {
        final List<String> t = new ArrayList<String>();
        t.add("Explosive Shot");
        t.add("Machine Gun Bow");
        return t;
    }

    @Override
    public List<String> getGhost() {
        final List<String> t = new ArrayList<String>();
        t.add("Stun Bow");
        t.add("Healing Bow");
        return t;
    }

    static {
        INSTANCE = new ArcherSkill();
    }
}
