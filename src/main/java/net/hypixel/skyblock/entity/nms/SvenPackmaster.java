package net.hypixel.skyblock.entity.nms;

import com.google.common.util.concurrent.AtomicDouble;
import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.features.enchantment.EnchantmentType;
import net.hypixel.skyblock.entity.*;
import net.hypixel.skyblock.entity.wolf.WolfStatistics;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.SMaterial;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SvenPackmaster extends EntityWolf implements SNMSEntity, EntityFunction, WolfStatistics, SlayerBoss {
    private static final TieredValue<Double> MAX_HEALTH_VALUES;
    private static final TieredValue<Double> DAMAGE_VALUES;
    private static final TieredValue<Double> TRUE_DAMAGE_VALUES;
    private static final TieredValue<Double> SPEED_VALUES;
    private final int tier;
    private final long end;
    private SEntity hologram;
    private SEntity hologram_name;
    private final UUID spawnerUUID;
    private long lastDamage;
    private boolean pupsSpawned;
    private boolean isActive;
    private final List<SEntity> pups;

    public SvenPackmaster(Integer tier, UUID spawnerUUID) {
        super(((CraftWorld) Bukkit.getPlayer(spawnerUUID).getWorld()).getHandle());
        this.tier = tier;
        this.end = System.currentTimeMillis() + 240000L;
        this.spawnerUUID = spawnerUUID;
        this.lastDamage = System.currentTimeMillis() - 1L;
        this.pupsSpawned = false;
        this.isActive = false;
        this.pups = new ArrayList<SEntity>();
    }

    public SvenPackmaster(World world) {
        super(world);
        this.tier = 1;
        this.end = System.currentTimeMillis() + 240000L;
        this.spawnerUUID = UUID.randomUUID();
        this.lastDamage = System.currentTimeMillis() - 1L;
        this.pupsSpawned = false;
        this.isActive = false;
        this.pups = new ArrayList<SEntity>();
    }

    public void t_() {
        super.t_();
        Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (null == player) {
            return;
        }
        if (System.currentTimeMillis() > this.end) {
            User.getUser(player.getUniqueId()).failSlayerQuest();
            ((Wolf) this.bukkitEntity).remove();
            this.hologram.remove();
            return;
        }
        if (((Wolf) this.bukkitEntity).getWorld() == player.getWorld() && 20.0 <= this.getBukkitEntity().getLocation().distance(player.getLocation()) && 0 == SUtil.random(0, 10)) {
            this.getBukkitEntity().teleport(player.getLocation());
        }
        if (System.currentTimeMillis() > this.lastDamage && (this.pups.isEmpty() || !this.pupsSpawned)) {
            this.setHealth(Math.min(this.getMaxHealth(), this.getHealth() + this.getHealth() * 5.0E-4f));
        }
        if (this.pups.isEmpty()) {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMovementSpeed());
        } else {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3);
        }
        this.getBukkitEntity().getWorld().spigot().playEffect(this.getBukkitEntity().getLocation(), Effect.FLAME, 0, 1, (float) SUtil.random(-1.0, 1.0), 0.0f, (float) SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
        this.getBukkitEntity().getWorld().spigot().playEffect(this.getBukkitEntity().getLocation(), Effect.FIREWORKS_SPARK, 0, 1, (float) SUtil.random(-1.0, 1.0), 0.0f, (float) SUtil.random(-1.0, 1.0), 0.0f, 1, 100);
        ((Wolf) this.getBukkitEntity()).setTarget(player);
        Entity entity = this.getBukkitEntity().getHandle();
        double height = entity.getBoundingBox().e - entity.getBoundingBox().b;
        this.hologram_name.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, height, 0.0));
        this.hologram_name.getEntity().setCustomName(Sputnik.trans(Sputnik.entityNameTag((LivingEntity) this.getBukkitEntity(), Sputnik.buildcustomString(this.getEntityName(), 0, true))));
        this.hologram.getEntity().teleport(this.getBukkitEntity().getLocation().clone().add(0.0, 1.1, 0.0));
        if (!this.isActive) {
            this.hologram.getEntity().setCustomName(ChatColor.RED + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000));
        } else {
            this.hologram.getEntity().setCustomName(Sputnik.trans("&bCalling the pups! &c" + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000)));
        }
    }

    public void onSpawn(LivingEntity entity, SEntity sEntity) {
        entity.setMetadata("BOSS_OWNER_" + Bukkit.getPlayer(this.getSpawnerUUID()).getUniqueId().toString(), new FixedMetadataValue(SkyBlock.getPlugin(), true));
        entity.setMetadata("SlayerBoss", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (null != player) {
            player.playSound(player.getLocation(), Sound.WOLF_HOWL, 1.0f, 5.0f);
        }
        this.hologram = new SEntity(entity.getLocation().add(0.0, 1.1, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND);
        ((ArmorStand) this.hologram.getEntity()).setVisible(false);
        ((ArmorStand) this.hologram.getEntity()).setGravity(false);
        this.hologram.getEntity().setCustomNameVisible(true);
        entity.setMetadata("notDisplay", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        Entity e = this.getBukkitEntity().getHandle();
        double height = e.getBoundingBox().e - e.getBoundingBox().b;
        this.hologram_name = new SEntity(entity.getLocation().add(0.0, height, 0.0), SEntityType.UNCOLLIDABLE_ARMOR_STAND);
        ((ArmorStand) this.hologram_name.getEntity()).setVisible(false);
        ((ArmorStand) this.hologram_name.getEntity()).setGravity(false);
        this.hologram_name.getEntity().setCustomNameVisible(true);
        new BukkitRunnable() {
            public void run() {
                if (entity.isDead()) {
                    SUtil.delay(() -> SvenPackmaster.this.hologram_name.remove(), 20L);
                    SvenPackmaster.this.hologram.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 1L);
    }

    public String getEntityName() {
        return ChatColor.RED + "☠ " + ChatColor.WHITE + "Sven Packmaster";
    }

    public double getEntityMaxHealth() {
        return MAX_HEALTH_VALUES.getByNumber(this.tier);
    }

    public double getDamageDealt() {
        return DAMAGE_VALUES.getByNumber(this.tier);
    }

    public double getMovementSpeed() {
        return SPEED_VALUES.getByNumber(this.tier);
    }

    public void onDeath(SEntity sEntity, org.bukkit.entity.Entity killed, org.bukkit.entity.Entity damager) {
        SUtil.delay(() -> this.hologram_name.remove(), 20L);
        this.hologram.remove();
        for (SEntity pup : this.pups) {
            pup.getEntity().setHealth(0.0);
        }
        User user = User.getUser(damager.getUniqueId());
        user.addCoins(50000);
        user.send(ChatColor.GOLD + "+50000 Coins");
    }

    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        User.getUser(player.getUniqueId()).damage(TRUE_DAMAGE_VALUES.getByNumber(this.tier), EntityDamageEvent.DamageCause.ENTITY_ATTACK, this.getBukkitEntity());
        if (player.getUniqueId().equals(this.spawnerUUID)) {
            this.lastDamage = System.currentTimeMillis() + 15000L;
        }
    }

    public LivingEntity spawn(Location location) {
        this.world = ((CraftWorld) location.getWorld()).getHandle();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (LivingEntity) this.getBukkitEntity();
    }

    public void onDamage(SEntity sEntity, org.bukkit.entity.Entity damager, EntityDamageByEntityEvent e, AtomicDouble damage) {
        if (e.getDamager() instanceof Arrow) {
            e.setCancelled(true);
            return;
        }
        Player player = Bukkit.getPlayer(this.spawnerUUID);
        if (null == player) {
            return;
        }
        if (3 <= this.tier && sEntity.getEntity().getHealth() - damage.get() < this.getEntityMaxHealth() / 2.0 && !this.pupsSpawned) {
            this.isActive = true;
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
            player.playSound(player.getLocation(), Sound.WOLF_HOWL, 1.0f, 1.0f);
            SUtil.delay(() -> this.hologram.getEntity().setCustomName(Sputnik.trans("&c" + SUtil.getFormattedTime(this.end - System.currentTimeMillis(), 1000))), 120L);
            SUtil.delay(() -> this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMovementSpeed()), 120L);
            SUtil.delay(() -> this.isActive = false, 120L);
            this.pupsSpawned = true;
            for (int i = 0; 10 > i; ++i) {
                SUtil.delay(() -> {
                    if (this.bukkitEntity.isDead()) {
                    } else {
                        List<SEntity> pups = this.pups;

                        SEntity sEntity2 = new SEntity(sEntity.getEntity(), SEntityType.SVEN_PUP, this.getEntityMaxHealth() * 0.1, this.getDamageDealt() * 0.5, player, this);
                        pups.add(sEntity2);
                    }
                }, i * 20);
            }
        }
    }

    public List<EntityDrop> drops() {
        List<EntityDrop> drops = new ArrayList<EntityDrop>();
        int teeth = SUtil.random(1, 3);
        if (2 == this.tier) {
            teeth = SUtil.random(9, 18);
        }
        if (3 == this.tier) {
            teeth = SUtil.random(30, 50);
        }
        if (4 == this.tier) {
            teeth = SUtil.random(50, 64);
        }
        drops.add(new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.WOLF_TOOTH).getStack(), teeth), EntityDropType.GUARANTEED, 1.0));
        if (2 <= this.tier) {
            int hamsterWheel = 1;
            if (3 == this.tier) {
                hamsterWheel = SUtil.random(2, 4);
            }
            if (4 == this.tier) {
                hamsterWheel = SUtil.random(4, 5);
            }
            drops.add(new EntityDrop(SUtil.setStackAmount(SItem.of(SMaterial.HAMSTER_WHEEL).getStack(), hamsterWheel), EntityDropType.OCCASIONAL, 0.2));
            drops.add(new EntityDrop(SMaterial.SPIRIT_RUNE, EntityDropType.RARE, 0.05));
        }
        if (3 <= this.tier) {
            SItem critBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            critBook.addEnchantment(EnchantmentType.CRITICAL, 6);
            drops.add(new EntityDrop(critBook.getStack(), EntityDropType.EXTRAORDINARILY_RARE, 0.005));
            drops.add(new EntityDrop(SMaterial.RED_CLAW_EGG, EntityDropType.CRAZY_RARE, (3 == this.tier) ? 0.0025 : 0.002));
        }
        if (4 <= this.tier) {
            SItem chimBook = SItem.of(SMaterial.ENCHANTED_BOOK);
            chimBook.addEnchantment(EnchantmentType.CHIMERA, 1);
            drops.add(new EntityDrop(chimBook.getStack(), EntityDropType.CRAZY_RARE, 0.002));
            SItem chimBook2 = SItem.of(SMaterial.ENCHANTED_BOOK);
            chimBook2.addEnchantment(EnchantmentType.CHIMERA, 2);
            drops.add(new EntityDrop(chimBook2.getStack(), EntityDropType.INSANE_RARE, 0.0016666666666666668));
            drops.add(new EntityDrop(SMaterial.COUTURE_RUNE, EntityDropType.CRAZY_RARE, 0.00625));
            drops.add(new EntityDrop(SMaterial.GRIZZLY_BAIT, EntityDropType.CRAZY_RARE, 0.006666666666666667));
            drops.add(new EntityDrop(SMaterial.OVERFLUX_CAPACITOR, EntityDropType.CRAZY_RARE, 0.006666666666666667));
        }
        return drops;
    }

    public double getXPDropped() {
        return 0.0;
    }

    public boolean isBaby() {
        return false;
    }

    public boolean isAngry() {
        return true;
    }

    public UUID getSpawnerUUID() {
        return this.spawnerUUID;
    }

    public int getTier() {
        return this.tier;
    }

    public List<SEntity> getPups() {
        return this.pups;
    }

    static {
        MAX_HEALTH_VALUES = new TieredValue<Double>(2000.0, 40000.0, 750000.0, 2000000.0);
        DAMAGE_VALUES = new TieredValue<Double>(60.0, 200.0, 450.0, 1100.0);
        TRUE_DAMAGE_VALUES = new TieredValue<Double>(0.0, 10.0, 50.0, 200.0);
        SPEED_VALUES = new TieredValue<Double>(0.35, 0.4, 0.45, 0.55);
    }
}
