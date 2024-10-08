package net.hypixel.skyblock.entity.end;

import net.hypixel.skyblock.features.slayer.SlayerQuest;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.entity.EntityFunction;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityType;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;

public abstract class BaseEnderman implements EndermanStatistics, EntityFunction {
    @Override
    public void onDeath(SEntity sEntity, Entity killed, Entity damager) {
        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        User user = User.getUser(player.getUniqueId());
        SlayerQuest quest = user.getSlayerQuest();
        if (null == quest) {
            return;
        }
        if (0L != quest.getSpawned()) {
            return;
        }
        if ("Voidgloom Seraph" == quest.getType().getName()) {
            Location k = killed.getLocation().clone();
            if (0 == SUtil.random(0, 8) && 3 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDLING_DEVOTEE).setTarget(player), 12L);
                return;
            }
            if (0 == SUtil.random(0, 16) && 4 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDLING_RADICAL).setTarget(player), 12L);
                return;
            }
            if (0 == SUtil.random(0, 45) && 4 == quest.getType().getTier()) {
                SlayerQuest.playMinibossSpawn(k, player);
                SUtil.delay(() -> new SEntity(k, SEntityType.VOIDCRAZED_MANIAC).setTarget(player), 12L);
            }
        }
    }
}
