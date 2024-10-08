package net.hypixel.skyblock.item.bow;

import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.item.*;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.hypixel.skyblock.item.*;

public class HurricaneBow implements ToolStatistics, BowFunction, Ability {
    @Override
    public String getAbilityName() {
        return "Tempest";
    }

    @Override
    public String getAbilityDescription() {
        return "The more kills you get using this bow the more powerful it becomes! Reach 250 kills to unlock its full potential.";
    }

    @Override
    public int getAbilityCooldownTicks() {
        return 0;
    }

    @Override
    public int getManaCost() {
        return 0;
    }

    @Override
    public AbilityActivation getAbilityActivation() {
        return AbilityActivation.NO_ACTIVATION;
    }

    @Override
    public String getDisplayName() {
        return "Hurricane Bow";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.RANGED_WEAPON;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.BOW;
    }

    @Override
    public int getBaseDamage() {
        return 120;
    }

    @Override
    public double getBaseStrength() {
        return 50.0;
    }

    @Override
    public boolean displayKills() {
        return true;
    }

    @Override
    public void onBowShoot(final SItem bow, final EntityShootBowEvent e) {
        final Player shooter = (Player) e.getEntity();
        final int kills = bow.getDataInt("kills");
        final Location location = shooter.getEyeLocation().add(shooter.getEyeLocation().getDirection().toLocation(shooter.getWorld()));
        final float speed = e.getForce() * 3.0f;
        if (kills >= 20) {
            final Location l = location.clone();
            l.setYaw(location.getYaw() - 15.0f);
            shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter(shooter);
        }
        if (kills >= 50) {
            final Location l = location.clone();
            l.setYaw(location.getYaw() + 15.0f);
            shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter(shooter);
        }
        if (kills >= 100) {
            final Location l = location.clone();
            l.setYaw(location.getYaw() - 30.0f);
            shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter(shooter);
        }
        if (kills >= 250) {
            final Location l = location.clone();
            l.setYaw(location.getYaw() + 30.0f);
            shooter.getWorld().spawnArrow(l, l.getDirection(), speed, 1.0f).setShooter(shooter);
        }
    }

    @Override
    public void onBowHit(final Entity hit, final Player shooter, final Arrow arrow, final SItem weapon, final AtomicDouble finalDamage) {
        if (!(hit instanceof LivingEntity)) {
            return;
        }
        if (hit instanceof Villager) {
            return;
        }
        if (((LivingEntity) hit).getHealth() - finalDamage.get() <= 0.0) {
            weapon.setKills(weapon.getDataInt("kills") + 1);
        }
    }
}
