package net.hypixel.skyblock.entity.dungeons.minibosses;

import com.google.common.util.concurrent.AtomicDouble;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.hypixel.skyblock.SkyBlock;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import net.hypixel.skyblock.entity.SEntity;
import net.hypixel.skyblock.entity.SEntityEquipment;
import net.hypixel.skyblock.entity.zombie.BaseZombie;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.user.PlayerStatistics;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.EntityManager;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;

public class Unstable extends BaseZombie {
    private boolean isEating;
    private boolean isBowing;
    private boolean EatingCooldown;
    private boolean CDDR;
    private boolean CDLS;

    public Unstable() {
        this.isEating = false;
        this.isBowing = false;
        this.EatingCooldown = false;
        this.CDDR = false;
        this.CDLS = false;
    }

    @Override
    public String getEntityName() {
        return Sputnik.trans("&d&lLost Adventurer");
    }

    @Override
    public double getEntityMaxHealth() {
        return 5.2E8;
    }

    @Override
    public double getDamageDealt() {
        return 4800000.0;
    }

    @Override
    public void onSpawn(LivingEntity entity, SEntity sEntity) {
        ((CraftZombie) entity).setBaby(false);
        AttributeInstance followRange = ((CraftLivingEntity) entity).getHandle().getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        followRange.setValue(40.0);
        PlayerDisguise pl = Sputnik.applyPacketNPC(entity, "ewogICJ0aW1lc3RhbXAiIDogMTYwNzI2OTE0ODI0NSwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lMzgxYTE4NzVlZmViMmEyNjBkMzU3OTg0YzNlNzVhN2RmYmM3YTIyYjU4MmJmZTk5OTA0MjFjYmI0NDVhY2MiCiAgICB9CiAgfQp9", "IPtgN21aEt2XOntNnSueh3eJHFJOEwIad/ttKraizcl4ffFSymZQo3NhgUPbRnSy2lFNHR/kTl6fczwnRtwxBNmG/YeA7kyr225kgROEAnze8owdQSDkFhvcnP5QRn5oBW9Aa11ZZRlcHuMEUYyZptYkUo8VZ4EV6NSkxz6n5Vjph8KJc2qTLsFtSZIi9lvMAvZRuR4IaKZT85Pnf04Lqmsj1mXkV22pjG3t5MQECsAEmV6beLAVkSjpxQqEkr86+tCbeYRx77fZxxhswHhCZsTB50I5EpDJ3cx5pvvmz8LW2/KoEppXt+NAW/3P4aHuj34KFMp1+qJjSHStl9yKHEdCzTKUy2bh3b6cq6hr0WfCAOes1X0uObindLA6kP+y0UbtvPHGnuyslvQ47X5NdkT54oATW8wBWh85gPUEPLsBXO3VBvfK5omSgaSrC3D1xZbuFjdDYETg4BmTlJOnhoDHUTAvl/a9gQTnItyJkLvkoa/WNWlSk6lcNqz09X7VipnerY5XYad1A1T5XngOg6+TUBwW+XXcxxh5FDH3kMGLomDCbZjaiKm2QvPzVrHVtZ0DhdvQ3jzGa/oSyCbQCWWgusptxeMcOvgxtrjtb4ZxnFiR3H8XYPYWsSxl2zZUlq7Ias9MbKILu8CulOX90i3Gha27lSIaf+ksUO4vrII=", true);
        PlayerWatcher skywatch = pl.getWatcher();
        LivingEntity target = ((CraftZombie) entity).getTarget();
        EntityManager.DEFENSE_PERCENTAGE.put(entity, 82);
        entity.setMetadata("SlayerBoss", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        entity.setMetadata("LD", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (Unstable.this.isEating) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    Sputnik.sendEatingAnimation(entity);
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 4L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                if (entity.getHealth() < entity.getMaxHealth() / 2.0 && !Unstable.this.EatingCooldown && !Unstable.this.isEating) {
                    Unstable.this.EatingCooldown = true;
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 65, 4));
                    Unstable.this.isBowing = false;
                    SUtil.delay(() -> Unstable.this.isEating = true, 5L);
                    entity.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_APPLE));
                    new BukkitRunnable() {
                        public void run() {
                            if (entity.isDead()) {
                                return;
                            }
                            entity.getEquipment().setItemInHand(new ItemStack(Material.AIR));
                            entity.getWorld().playSound(entity.getLocation(), Sound.BURP, 1.0f, 1.0f);
                            double healamount = Unstable.this.getEntityMaxHealth() * SUtil.random(40, 60) / 100.0;
                            if (!entity.isDead()) {
                                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + healamount));
                            }
                            Unstable.this.isEating = false;
                            SUtil.delay(() -> {
                                Object val$entity = entity;
                                if (!Unstable.this.isBowing) {
                                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                                } else {
                                    entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                                }
                            }, 5L);
                            SUtil.delay(() -> Unstable.this.EatingCooldown = false, SUtil.random(600, 800));
                        }
                    }.runTaskLater(SkyBlock.getPlugin(), 60L);
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 10L);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    Sputnik.zero(entity);
                    this.cancel();
                    return;
                }
                LivingEntity target1 = ((CraftZombie) entity).getTarget();
                if (null != target1) {
                    if (6.0 <= target1.getLocation().distance(entity.getLocation()) && 16.0 > target1.getLocation().distance(entity.getLocation())) {
                        entity.teleport(entity.getLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        Sputnik.sendHeadRotation(entity, entity.getLocation().getYaw(), entity.getLocation().getPitch());
                    }
                    if ((6.0 > target1.getLocation().distance(entity.getLocation()) || 16.0 < target1.getLocation().distance(entity.getLocation())) && !Unstable.this.isEating) {
                        SUtil.delay(() -> {
                            Object val$entity = entity;
                            entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                        }, 0L);
                        Unstable.this.isBowing = false;
                    }
                    if (6.0 <= target1.getLocation().distance(entity.getLocation()) && 16.0 > target1.getLocation().distance(entity.getLocation()) && !Unstable.this.isBowing && !Unstable.this.isEating) {
                        Unstable.this.isBowing = true;
                        skywatch.setRightClicking(false);
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 4));
                        entity.getEquipment().setItemInHand(SItem.of(SMaterial.BOW).getStack());
                        new BukkitRunnable() {
                            int t = 0;
                            int atkCharge = 20;
                            double bowPower = 2.2;
                            boolean crit = true;

                            public void run() {
                                if (10.0 >= target1.getLocation().distance(entity.getLocation())) {
                                    this.atkCharge = 10;
                                    this.bowPower = 1.1;
                                    this.crit = false;
                                }
                                ++this.t;
                                if (!Unstable.this.isBowing) {
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    this.cancel();
                                    return;
                                }
                                if (5 == this.t) {
                                    if (!Unstable.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(true);
                                }
                                if (this.t == this.atkCharge) {
                                    if (!Unstable.this.isBowing) {
                                        return;
                                    }
                                    skywatch.setRightClicking(false);
                                }
                                if (this.t >= this.atkCharge + 1) {
                                    if (!Unstable.this.isBowing) {
                                        return;
                                    }
                                    Location location = entity.getEyeLocation().add(entity.getEyeLocation().getDirection().toLocation(entity.getWorld()));
                                    Location l = location.clone();
                                    l.setYaw(location.getYaw());
                                    Arrow arr = entity.getWorld().spawnArrow(l, l.getDirection(), (float) this.bowPower, 1.6f);
                                    arr.setShooter(entity);
                                    if (!this.crit) {
                                        arr.setCritical(1 == SUtil.random(0, 1));
                                    } else {
                                        arr.setCritical(true);
                                    }
                                    skywatch.setRightClicking(false);
                                    entity.removePotionEffect(PotionEffectType.SLOW);
                                    Unstable.this.isBowing = false;
                                }
                            }
                        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 1L);
                    }
                    if (10.0 >= target1.getLocation().distance(entity.getLocation()) && !Unstable.this.isBowing && !Unstable.this.isEating && 5 > SUtil.random(0, 100) && !Unstable.this.CDLS) {
                        Unstable.this.CDLS = true;
                        Unstable.this.lightningPlayer(entity);
                        SUtil.delay(() -> Unstable.this.CDLS = false, 400L);
                    }
                    if (5.0 >= target1.getLocation().distance(entity.getLocation()) && !Unstable.this.isBowing && !Unstable.this.isEating) {
                        if (30 < SUtil.random(0, 100)) {
                            return;
                        }
                        if (Unstable.this.CDDR) {
                            return;
                        }
                        Unstable.this.CDDR = true;
                        skywatch.setRightClicking(true);
                        Unstable.this.playPar(entity.getEyeLocation().setDirection(target1.getLocation().toVector().subtract(entity.getLocation().toVector())));
                        entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.0f);
                        for (Entity e : target1.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 3.0, 3.0, 3.0)) {
                            if (e instanceof Player) {
                                Player player = (Player) e;
                                player.sendMessage(Sputnik.trans("&cLost Adventurer &aused &6Dragon's Breath &aon you!"));
                                player.setVelocity(entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1.0).multiply(4.0));
                                PlayerStatistics statistics = PlayerUtils.STATISTICS_CACHE.get(player.getUniqueId());
                                if (null == statistics) {
                                    return;
                                }
                                double defense = statistics.getDefense().addAll();
                                int dmglater = (int) Math.round(Unstable.this.getDamageDealt() * 3.0 - Unstable.this.getDamageDealt() * 3.0 * (defense / (defense + 100.0)));
                                User.getUser(player.getUniqueId()).damage(dmglater, EntityDamageEvent.DamageCause.ENTITY_ATTACK, entity);
                                ((LivingEntity) e).damage(1.0E-6, null);
                            }
                        }
                        SUtil.delay(() -> {
                            Object val$skywatch = skywatch;
                            if (!Unstable.this.isBowing) {
                                skywatch.setRightClicking(false);
                            }
                        }, 20L);
                        SUtil.delay(() -> Unstable.this.CDDR = false, 250L);
                    }
                } else if (!Unstable.this.isEating) {
                    Unstable.this.isBowing = false;
                    entity.getEquipment().setItemInHand(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()));
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 2L);
        new BukkitRunnable() {
            Location loc = entity.getLocation();
            final EntityLiving nms = ((CraftLivingEntity) entity).getHandle();

            public void run() {
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                this.loc.setY(0.0);
                this.nms.setSprinting(false);
                Location loc2 = entity.getLocation();
                loc2.setY(0.0);
                if (entity.hasMetadata("frozen")) {
                    return;
                }
                if (null == ((CraftZombie) entity).getTarget()) {
                    return;
                }
                if (((CraftZombie) entity).getTarget().getWorld() != entity.getWorld()) {
                    return;
                }
                if (4.0 >= ((CraftZombie) entity).getTarget().getLocation().distance(entity.getLocation()) || Unstable.this.isEating || Unstable.this.isBowing) {
                    return;
                }
                if (0.2 <= this.loc.distance(loc2)) {
                    this.nms.setSprinting(true);
                    if (entity.isOnGround() && 0.5 <= this.loc.distance(loc2)) {
                        double motY = 0.4199999868869782;
                        double motX = 0.0;
                        double motZ = 0.0;
                        if (this.nms.hasEffect(MobEffectList.JUMP)) {
                            motY += (this.nms.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.2f;
                        }
                        if (this.nms.isSprinting()) {
                            float f = this.nms.yaw * 0.01745329f;
                            motX -= MathHelper.sin(f) * 0.9f;
                            motZ += MathHelper.cos(f) * 0.9f;
                        }
                        entity.setVelocity(new Vector(motX, motY, motZ));
                    }
                    this.loc = entity.getLocation().clone();
                    return;
                }
                this.nms.setSprinting(false);
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 7L);
        new BukkitRunnable() {
            public void run() {
                EntityLiving nms = ((CraftLivingEntity) entity).getHandle();
                if (entity.isDead()) {
                    this.cancel();
                    return;
                }
                for (Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation().add(entity.getLocation().getDirection().multiply(1.0)), 1.5, 1.5, 1.5)) {
                    if (Unstable.this.isEating) {
                        continue;
                    }
                    if (Unstable.this.isBowing) {
                        continue;
                    }
                    if (!(entities instanceof Player)) {
                        continue;
                    }
                    Player target = (Player) entities;
                    if (GameMode.CREATIVE == target.getGameMode()) {
                        continue;
                    }
                    if (GameMode.SPECTATOR == target.getGameMode()) {
                        continue;
                    }
                    if (target.hasMetadata("NPC")) {
                        continue;
                    }
                    if (7 == target.getNoDamageTicks()) {
                        continue;
                    }
                    if (8 < SUtil.random(0, 10)) {
                        continue;
                    }
                    entity.teleport(entity.getLocation().setDirection(target.getLocation().subtract(entities.getLocation()).toVector()));
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer) players).getHandle().playerConnection.sendPacket(new PacketPlayOutAnimation(((CraftLivingEntity) entity).getHandle(), 0));
                    }
                    nms.r(((CraftPlayer) target).getHandle());
                    break;
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 2L);
    }

    @Override
    public void onDamage(SEntity sEntity, Entity damager, EntityDamageByEntityEvent e, AtomicDouble damage) {
        Entity en = sEntity.getEntity();
        Vector v = new Vector(0, 0, 0);
        SUtil.delay(() -> en.setVelocity(v), 1L);
    }

    @Override
    public SEntityEquipment getEntityEquipment() {
        return new SEntityEquipment(SUtil.enchant(SItem.of(SMaterial.ASPECT_OF_THE_DRAGONS).getStack()), SUtil.enchant(SItem.of(SMaterial.UNSTABLE_DRAGON_HELMET).getStack()), SUtil.enchant(SItem.of(SMaterial.UNSTABLE_DRAGON_CHESTPLATE).getStack()), SUtil.enchant(SItem.of(SMaterial.UNSTABLE_DRAGON_LEGGINGS).getStack()), SUtil.enchant(SItem.of(SMaterial.UNSTABLE_DRAGON_BOOTS).getStack()));
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean hasNameTag() {
        return false;
    }

    @Override
    public boolean isVillager() {
        return false;
    }

    @Override
    public double getXPDropped() {
        return 0.0;
    }

    @Override
    public double getMovementSpeed() {
        return 0.3;
    }

    public void playPar(Location l) {
        ConeEffect Effect = new ConeEffect(SkyBlock.effectManager);
        Effect.setLocation(l.clone().add(l.getDirection().normalize().multiply(-0.25)).add(0.0, -0.1, 0.0));
        Effect.particle = ParticleEffect.FLAME;
        Effect.angularVelocity = 0.39269908169872414;
        Effect.lengthGrow = 0.025f;
        Effect.particles = 30;
        Effect.period = 3;
        Effect.iterations = 5;
        Effect.start();
    }

    public void lightningPlayer(Entity en) {
        for (Entity e : en.getNearbyEntities(10.0, 10.0, 10.0)) {
            if (e instanceof Player) {
                Player p = (Player) e;
                p.getWorld().strikeLightningEffect(p.getLocation());
                User.getUser(p.getUniqueId()).damage(p.getMaxHealth() * 3.0 / 100.0, EntityDamageEvent.DamageCause.ENTITY_ATTACK, e);
            }
        }
    }
}
