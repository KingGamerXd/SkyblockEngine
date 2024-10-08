package net.hypixel.skyblock.entity.zombie;

import net.hypixel.skyblock.features.slayer.SlayerQuest;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.entity.ZombieStatistics;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;

import java.util.Objects;

public abstract class BaseZombie implements ZombieStatistics, EntityFunction {
    @Override
    public void onDeath(final SEntity sEntity, final Entity killed, final Entity damager) {
        if (!(damager instanceof Player)) {
            return;
        }
        final Player player = (Player) damager;
        final User user = User.getUser(player.getUniqueId());
        final SlayerQuest quest = user.getSlayerQuest();
        if (quest == null) {
            return;
        }
        if (quest.getSpawned() != 0L) {
            return;
        }
        if (Objects.equals(quest.getType().getName(), "Revenant Horror") || Objects.equals(quest.getType().getName(), "Atoned Horror")) {
            final Location k = killed.getLocation().clone();
            if (SUtil.random(0, 8) == 0 && quest.getType().getTier() == 3) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.REVENANT_SYCOPHANT).setTarget(player), 12L);
                return;
            }
            if (SUtil.random(0, 16) == 0 && quest.getType().getTier() == 4) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.REVENANT_CHAMPION).setTarget(player), 12L);
                return;
            }
            if (SUtil.random(0, 45) == 0 && quest.getType().getTier() == 4) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.DEFORMED_REVENANT).setTarget(player), 12L);
            }
            if (SUtil.random(0, 16) == 0 && quest.getType().getTier() == 5) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.ATONED_CHAMPION).setTarget(player), 12L);
            }
            if (SUtil.random(0, 40) == 0 && quest.getType().getTier() == 5) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.ATONED_REVENANT).setTarget(player), 12L);
            }
        }
    }
}
