package net.hypixel.skyblock.entity.den;

import net.hypixel.skyblock.SkyBlock;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SlimeStatistics;
import net.hypixel.skyblock.util.SUtil;

public class SpidersDenSlime implements SlimeStatistics, EntityFunction {
    @Override
    public String getEntityName() {
        return "Slime";
    }

    @Override
    public double getEntityMaxHealth() {
        return SUtil.random(200.0, 400.0);
    }

    @Override
    public double getDamageDealt() {
        return 140.0;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void onAttack(final EntityDamageByEntityEvent e) {
        new BukkitRunnable() {
            public void run() {
                e.getEntity().setVelocity(e.getEntity().getVelocity().clone().setY(1.5));
            }
        }.runTaskLater(SkyBlock.getPlugin(), 1L);
    }

    @Override
    public double getXPDropped() {
        return 4.0;
    }
}
