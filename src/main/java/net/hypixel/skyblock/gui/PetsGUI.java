package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.item.pet.Pet;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.PaginationList;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PetsGUI extends GUI {
    private static final int[] INTERIOR;
    public static final Map<UUID, Boolean> PET_SHOWN;
    private int page;
    private final boolean pickup;

    public static void setShowPets(final Player p, final boolean bo) {
        if (p == null) {
            SLog.severe("An unexpected error occured on Pets saving!");
        }
        PetsGUI.PET_SHOWN.put(p.getUniqueId(), bo);
    }

    public static boolean getShowPet(final Player p) {
        if (p == null) {
            return true;
        }
        if (PetsGUI.PET_SHOWN.containsKey(p.getUniqueId())) {
            return PetsGUI.PET_SHOWN.get(p.getUniqueId());
        }
        PetsGUI.PET_SHOWN.put(p.getUniqueId(), true);
        return true;
    }

    public PetsGUI(final int page, final boolean pickup) {
        super("Pets", 54);
        this.page = page;
        this.pickup = pickup;
    }

    public PetsGUI(final boolean pickup) {
        this(1, pickup);
    }

    public PetsGUI() {
        this(false);
    }

    @Override
    public void onOpen(final GUIOpenEvent e) {
        final Player player = e.getPlayer();
        final User user = User.getUser(player.getUniqueId());
        this.border(PetsGUI.BLACK_STAINED_GLASS_PANE);
        final PaginationList<Pet.PetItem> paged = new PaginationList<Pet.PetItem>(28);
        paged.addAll(user.getPets());
        if (paged.size() == 0) {
            this.page = 0;
        }
        final int finalPage = this.page;
        if (this.page > 1) {
            this.title = "(" + finalPage + "/" + this.page + ") Pets";
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new PetsGUI(finalPage - 1, false).open((Player) e.getWhoClicked());
                }

                @Override
                public int getSlot() {
                    return 45;
                }

                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, ChatColor.GREEN + "Pervious Page");
                }
            });
        }
        if (this.page != paged.getPageCount()) {
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    new PetsGUI(finalPage + 1, false).open((Player) e.getWhoClicked());
                }

                @Override
                public int getSlot() {
                    return 53;
                }

                @Override
                public ItemStack getItem() {
                    return SUtil.createNamedItemStack(Material.ARROW, ChatColor.GREEN + "Next Page");
                }
            });
        }
        final Pet.PetItem active = user.getActivePet();
        String name;
        if (active == null) {
            name = ChatColor.RED + "None";
        } else {
            name = active.getRarity().getColor() + active.getType().getDisplayName(active.getType().getData());
        }
        this.set(4, SUtil.getStack(ChatColor.GREEN + "Pets", Material.BONE, (short) 0, 1, ChatColor.GRAY + "View and manage all of your", ChatColor.GRAY + "Pets.", " ", ChatColor.GRAY + "Level up your pets faster by", ChatColor.GRAY + "gaining XP in their favorite", ChatColor.GRAY + "skill!", " ", ChatColor.GRAY + "Selected pet: " + name));
        this.set(47, SUtil.getStack(ChatColor.GREEN + "Pet Score Rewards", Material.DIAMOND, (short) 0, 1, ChatColor.GRAY + "Pet score is calculated based", ChatColor.GRAY + "on how many " + ChatColor.GREEN + "unique" + ChatColor.GRAY + " pets you", ChatColor.GRAY + "have and the " + ChatColor.GREEN + "rarity" + ChatColor.GRAY + " of these", ChatColor.GRAY + "pets.", " ", ChatColor.GOLD + "10 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "1 Magic Find", ChatColor.GOLD + "25 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "2 Magic Find", ChatColor.GOLD + "50 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "3 Magic Find", ChatColor.GOLD + "75 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "4 Magic Find", ChatColor.GOLD + "100 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "5 Magic Find", ChatColor.GOLD + "130 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "6 Magic Find", ChatColor.GOLD + "175 Score: " + ChatColor.GRAY + "+" + ChatColor.AQUA + "7 Magic Find", " ", ChatColor.BLUE + "Your Pet Score: " + ChatColor.RED + "Coming soon!"));
        this.set(GUIClickableItem.createGUIOpenerItem(GUIType.SKYBLOCK_MENU, player, ChatColor.GREEN + "Go Back", 48, Material.ARROW, ChatColor.GRAY + "To SkyBlock Menu"));
        this.set(GUIClickableItem.getCloseItem(49));
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                new PetsGUI(PetsGUI.this.page, !PetsGUI.this.pickup).open(player);
            }

            @Override
            public int getSlot() {
                return 50;
            }

            @Override
            public ItemStack getItem() {
                return SUtil.getStack(ChatColor.GREEN + "Convert Pet to an Item", Material.INK_SACK, (short) (PetsGUI.this.pickup ? 10 : 8), 1, ChatColor.GRAY + "Enable this setting and", ChatColor.GRAY + "click any pet to convert it", ChatColor.GRAY + "to an item.", " ", PetsGUI.this.pickup ? (ChatColor.GREEN + "Enabled") : (ChatColor.RED + "Disabled"));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                final Player p = (Player) e.getWhoClicked();
                if (p == null) {
                    return;
                }
                if (PetsGUI.getShowPet(player)) {
                    PetsGUI.setShowPets(player, false);
                    player.sendMessage(ChatColor.GREEN + "Hide Pets is now enabled!");
                    player.closeInventory();
                } else {
                    PetsGUI.this.showPetInRange(player);
                    PetsGUI.setShowPets(player, true);
                    player.sendMessage(ChatColor.RED + "Hide Pets is now disabled!");
                    player.closeInventory();
                }
            }

            @Override
            public int getSlot() {
                return 51;
            }

            @Override
            public ItemStack getItem() {
                ItemStack isBuilder = new ItemStack(Material.BEDROCK, 1);
                if (PetsGUI.getShowPet(player)) {
                    isBuilder = SUtil.getStack(ChatColor.GREEN + "Hide Pets", Material.STONE_BUTTON, (short) 0, 1, ChatColor.GRAY + "Hide all pets which are", ChatColor.GRAY + "little heads from being", ChatColor.GRAY + "visible in the world.", " ", ChatColor.GRAY + "Pet effects remain active.", " ", ChatColor.GRAY + "Currently: " + ChatColor.GREEN + "Pets shown!", ChatColor.GRAY + "Selected pet: " + name, " ", ChatColor.YELLOW + "Click to hide!");
                } else {
                    isBuilder = SUtil.getStack(ChatColor.RED + "Hide Pets", Material.STONE_BUTTON, (short) 0, 1, ChatColor.GRAY + "Hide all pets which are", ChatColor.GRAY + "little heads from being", ChatColor.GRAY + "visible in the world.", " ", ChatColor.GRAY + "Pet effects remain active.", " ", ChatColor.GRAY + "Currently: " + ChatColor.RED + "Pets hidden!", ChatColor.GRAY + "Selected pet: " + name, " ", ChatColor.YELLOW + "Click to show!");
                }
                return isBuilder;
            }
        });
        final List<Pet.PetItem> p = paged.getPage(this.page);
        if (p == null) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            final int slot = PetsGUI.INTERIOR[i];
            final Pet.PetItem pet = p.get(i);
            final String n = pet.getRarity().getColor() + pet.getType().getDisplayName(pet.getType().getData());
            final SItem item = SItem.of(pet.getType());
            item.setRarity(pet.getRarity());
            item.setDataDouble("xp", pet.getXp());
            item.getData().setBoolean("equipped", true);
            item.update();
            if (!this.pickup) {
                final ItemMeta meta = item.getStack().getItemMeta();
                final List<String> lore = meta.getLore();
                lore.add(" ");
                if (pet.isActive()) {
                    lore.add(ChatColor.RED + "Click to despawn");
                } else {
                    lore.add(ChatColor.YELLOW + "Click to summon");
                }
                meta.setLore(lore);
                item.getStack().setItemMeta(meta);
            }
            this.set(new GUIClickableItem() {
                @Override
                public void run(final InventoryClickEvent e) {
                    if (PetsGUI.this.pickup) {
                        if (Sputnik.isFullInv(player)) {
                            player.sendMessage(ChatColor.RED + "Your inventory is full! Clean it up!");
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                            return;
                        }
                        final SItem n = SItem.of(pet.getType());
                        n.setRarity(pet.getRarity());
                        n.setDataDouble("xp", pet.getXp());
                        player.getInventory().addItem(n.getStack());
                        pet.setActive(false);
                        if (user.getActivePet() == pet) {
                            PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                        }
                        user.removePet(pet);
                        new PetsGUI(PetsGUI.this.page, false).open(player);
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                    } else {
                        if (pet.isActive()) {
                            pet.setActive(false);
                            player.closeInventory();
                            PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                            player.sendMessage(ChatColor.GREEN + "You despawned your " + n + ChatColor.GREEN + "!");
                            return;
                        }
                        user.equipPet(pet);
                        PetsGUI.destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
                        player.closeInventory();
                        player.sendMessage(ChatColor.GREEN + "You spawned your " + n + ChatColor.GREEN + "!");
                        PetsGUI.spawnFlyingHeads(player, user.getActivePetClass(), pet.toItem().getStack());
                    }
                }

                @Override
                public int getSlot() {
                    return slot;
                }

                @Override
                public ItemStack getItem() {
                    return item.getStack();
                }
            });
        }
    }

    public static void applyThingy(final ArmorStand as, final boolean a) {
        as.setCustomNameVisible(a);
        as.setMarker(true);
        as.setVisible(false);
        as.setGravity(false);
        as.setArms(true);
        as.setRightArmPose(new EulerAngle(0.0, 45.0, 0.0));
        as.setMetadata("pets", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        as.setRemoveWhenFarAway(false);
    }

    public static BukkitTask spawnFlyingHeads(final Player player, final Pet petclass, final ItemStack stacc) {
        destroyArmorStandWithUUID(player.getUniqueId(), player.getWorld());
        final Pet.PetItem active = User.getUser(player.getUniqueId()).getActivePet();
        final int level = Pet.getLevel(active.getXp(), active.getRarity());
        final Location location = player.getLocation();
        final ArmorStand name = (ArmorStand) player.getWorld().spawn(player.getLocation(), (Class) ArmorStand.class);
        applyThingy(name, true);
        name.setSmall(true);
        name.setMetadata(player.getUniqueId().toString() + "_pets", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        final ArmorStand stand = (ArmorStand) player.getWorld().spawn(player.getLocation(), (Class) ArmorStand.class);
        applyThingy(stand, false);
        stand.setMetadata(player.getUniqueId().toString() + "_pets", new FixedMetadataValue(SkyBlock.getPlugin(), true));
        final String displayname = Sputnik.trans("&8[&7Lv" + level + "&8] " + active.toItem().getRarity().getColor() + player.getName() + "'s " + petclass.getDisplayName());
        stand.getEquipment().setItemInHand(stacc);
        name.setCustomName(displayname);
        stand.setMetadata(player.getUniqueId().toString(), new FixedMetadataValue(SkyBlock.getPlugin(), true));
        return new BukkitRunnable() {
            int count = 0;
            int stat = 0;

            public void run() {
                final Pet.PetItem active1 = User.getUser(player.getUniqueId()).getActivePet();
                if (active1 == null || !player.isOnline() || !player.getWorld().equals(stand.getWorld())) {
                    name.remove();
                    stand.remove();
                    this.cancel();
                    return;
                }
                if (name.isDead()) {
                    stand.remove();
                    this.cancel();
                    return;
                }
                if (!player.getWorld().getEntities().contains(name)) {
                    name.remove();
                    stand.remove();
                    if (active1 != null) {
                        Sputnik.createPet(player);
                    }
                    this.cancel();
                    return;
                }
                if (player.getWorld().getName().contains("f6")) {
                    name.setCustomNameVisible(false);
                }
                final Pet.PetItem active2 = User.getUser(player.getUniqueId()).getActivePet();
                final int level1 = Pet.getLevel(active2.getXp(), active2.getRarity());
                name.setCustomName(Sputnik.trans("&8[&7Lv" + level1 + "&8] " + active2.toItem().getRarity().getColor() + player.getName() + "'s " + petclass.getDisplayName()));
                stand.getEquipment().setItemInHand(stacc);
                final Location target = player.getLocation();
                target.setPitch(0.0f);
                target.add(target.getDirection().multiply(-1));
                final double distance = target.distance(location);
                final double yoffset = Math.sin(this.count / 4.0f) / 1.7 + 1.0;
                if ((distance < 5.0 && this.stat >= 1) || distance < 0.6) {
                    if (this.stat < 5) {
                        ++this.stat;
                        return;
                    }
                    ++this.count;
                    if (this.count > 24) {
                        this.count = 0;
                    }
                    location.setY((location.getY() + target.getY()) / 2.0);
                } else {
                    this.stat = 0;
                    final Vector v = target.toVector().subtract(location.toVector()).normalize().multiply(Math.min(9.0, Math.min(15.0, distance / 2.0) / 4.0 + 0.2));
                    location.setDirection(v);
                    location.add(v);
                    if (this.count > 13) {
                        this.count /= (int) 1.1;
                    } else if (this.count < 11) {
                        this.count *= (int) 1.1;
                    }
                }
                final Location nameLoc = location.clone();
                if (nameLoc.distanceSquared(target) > 25.0) {
                    name.setCustomNameVisible(false);
                } else if (!target.getWorld().getName().contains("f6")) {
                    name.setCustomNameVisible(true);
                }
                nameLoc.setPitch(0.0f);
                nameLoc.add(nameLoc.getDirection().multiply(0.15));
                nameLoc.setYaw(nameLoc.getYaw() + 90.0f);
                nameLoc.add(nameLoc.getDirection().multiply(0.527));
                name.teleport(nameLoc.add(0.0, yoffset + 0.85, 0.0));
                stand.teleport(location.clone().add(0.0, yoffset, 0.0));
                final Pet pet_ = User.getUser(player.getUniqueId()).getActivePetClass();
                if ((distance < 5.0 && this.stat >= 1) || distance < 0.6) {
                    PetsGUI.spawnParticle(pet_, nameLoc.clone().add(0.0, -0.2, 0.0));
                } else {
                    PetsGUI.spawnParticle(pet_, nameLoc.clone().add(0.0, -0.2, 0.0).add(location.clone().add(0.0, yoffset, 0.0).getDirection().clone().multiply(-0.6)));
                }
                PetsGUI.sendDestroyPacket(stand, name, player.getWorld(), player);
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 3L, 3L);
    }

    public static void destroyArmorStandWithUUID(final UUID uuid, final World w) {
        final String uuidString = uuid.toString() + "_pets";
        for (final Entity e : w.getEntities()) {
            if (e.hasMetadata(uuidString)) {
                e.remove();
            }
        }
    }

    public static void spawnParticle(final Pet pet, final Location l) {
        final World w = l.getWorld();
        for (final Entity e : w.getNearbyEntities(l, 30.0, 35.0, 30.0)) {
            if (e instanceof Player) {
                final Player p = (Player) e;
                if (!getShowPet(p)) {
                    continue;
                }
                pet.particleBelowA(p, l);
            }
        }
    }

    public static void sendDestroyPacket(final Entity as1, final Entity as2, final World w, final Player owner) {
        final net.minecraft.server.v1_8_R3.Entity el = ((CraftEntity) as1).getHandle();
        final net.minecraft.server.v1_8_R3.Entity el_ = ((CraftEntity) as2).getHandle();
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(el.getId());
        final PacketPlayOutEntityDestroy packet_ = new PacketPlayOutEntityDestroy(el_.getId());
        for (final Entity e : w.getNearbyEntities(as1.getLocation(), 30.0, 35.0, 30.0)) {
            if (e instanceof Player) {
                final Player p = (Player) e;
                if (getShowPet(p)) {
                    continue;
                }
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet_);
            }
        }
    }

    public void showPetInRange(final Player p) {
        for (final Entity e : p.getNearbyEntities(30.0, 35.0, 30.0)) {
            if (e.hasMetadata("pets")) {
                final net.minecraft.server.v1_8_R3.Entity el = ((CraftEntity) e).getHandle();
                final net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(((LivingEntity) e).getEquipment().getItemInHand());
                el.setEquipment(0, nmsItem);
                final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving) el);
                final PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(el.getId(), 0, nmsItem);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
            }
        }
    }

    static {
        INTERIOR = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
        PET_SHOWN = new HashMap<UUID, Boolean>();
    }
}
