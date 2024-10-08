package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.item.Rarity;
import net.hypixel.skyblock.item.SItem;
import net.hypixel.skyblock.features.reforge.ReforgeType;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import net.hypixel.skyblock.item.Reforgable;

import java.util.*;
import java.util.stream.Collectors;

public class ReforgeAnvilGUI extends GUI implements Listener {
    private static final ItemStack DEFAULT_REFORGE_ITEM;
    private static final Map<Rarity, Integer> COST_MAP;
    private static final List<UUID> COOLDOWN;

    public void fillFrom(final Inventory i, final int startFromSlot, final int height, final ItemStack stacc) {
        i.setItem(startFromSlot, stacc);
        i.setItem(startFromSlot + 9, stacc);
        i.setItem(startFromSlot + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9, stacc);
        i.setItem(startFromSlot + 9 + 9 + 9 + 9, stacc);
    }

    public ReforgeAnvilGUI() {
        super("Reforge Item", 45);
        this.fill(BLACK_STAINED_GLASS_PANE);
        this.set(GUIClickableItem.getCloseItem(40));
        this.set(new GUIClickableItem() {
            @Override
            public int getSlot() {
                return 22;
            }

            @Override
            public ItemStack getItem() {
                return ReforgeAnvilGUI.DEFAULT_REFORGE_ITEM;
            }

            @Override
            public boolean canPickup() {
                return false;
            }

            @Override
            public void run(final InventoryClickEvent e) {
                final SItem sItem = SItem.find(e.getClickedInventory().getItem(13));
                if (sItem == null) {
                    return;
                }
                if (!(sItem.getType().getGenericInstance() instanceof Reforgable)) {
                    return;
                }
                final List<ReforgeType> possible = Arrays.stream(ReforgeType.values()).filter(type -> type.getReforge().getCompatibleTypes().contains(sItem.getType().getStatistics().getType()) && type.isAccessible()).collect(Collectors.toList());
                final Player player = (Player) e.getWhoClicked();
                if (possible.size() == 0) {
                    player.sendMessage(ChatColor.RED + "That item cannot be reforged!");
                    return;
                }
                if (ReforgeAnvilGUI.COOLDOWN.contains(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "Please wait a little bit before doing this!");
                    return;
                }
                final User user = User.getUser(player.getUniqueId());
                final int cost = ReforgeAnvilGUI.COST_MAP.get(sItem.getRarity());
                if (user.getCoins() - cost < 0L) {
                    player.sendMessage(ChatColor.RED + "You cannot afford to reforge this!");
                    return;
                }
                final String prev = sItem.getFullName();
                user.subCoins(cost);
                sItem.setReforge(possible.get(SUtil.random(0, possible.size() - 1)).getReforge());
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
                player.sendMessage(ChatColor.GREEN + "You reforged your " + prev + ChatColor.GREEN + " into a " + sItem.getFullName() + ChatColor.GREEN + "!");
                ReforgeAnvilGUI.COOLDOWN.add(player.getUniqueId());
                new BukkitRunnable() {
                    public void run() {
                        ReforgeAnvilGUI.COOLDOWN.remove(player.getUniqueId());
                    }
                }.runTaskLater(SkyBlock.getPlugin(), 20L);
            }
        });
        this.set(13, null);
    }

    @Override
    public void update(final Inventory inventory) {
        new BukkitRunnable() {
            public void run() {
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    inventory.setItem(22, DEFAULT_REFORGE_ITEM);
                    return;
                }
                ItemStack stack = SUtil.getStack(ChatColor.GREEN + "Reforge Item", Material.ANVIL, (short) 0, 1, ChatColor.GRAY + "Reforges the above item, giving", ChatColor.GRAY + "it a random item modifier that", ChatColor.GRAY + "boosts its stats.", " ", ChatColor.GRAY + "Cost", ChatColor.GOLD + SUtil.commaify(ReforgeAnvilGUI.COST_MAP.get(sItem.getRarity())) + " Coins", " ", ChatColor.YELLOW + "Click to reforge!");
                if (!sItem.isReforgable()) {
                    stack = SUtil.getStack(ChatColor.RED + "Error!", Material.BARRIER, (short) 0, 1, ChatColor.GRAY + "You cannot reforge this item!");
                }
                inventory.setItem(22, stack);
            }
        }.runTaskLater(SkyBlock.getPlugin(), 1L);
    }

    @Override
    public void onOpen(final GUIOpenEvent e) {
        new BukkitRunnable() {
            public void run() {
                final Player player = e.getPlayer();
                if (ReforgeAnvilGUI.this != GUI_MAP.get(player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                final Inventory inventory = e.getInventory();
                final SItem sItem = SItem.find(inventory.getItem(13));
                if (sItem == null) {
                    fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short) 14, ChatColor.RESET + " "));
                    fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short) 14, ChatColor.RESET + " "));
                    return;
                }
                if (sItem.isReforgable()) {
                   fillFrom(inventory, 0, 5, SUtil.createColoredStainedGlassPane((short) 5, ChatColor.RESET + " "));
                   fillFrom(inventory, 8, 5, SUtil.createColoredStainedGlassPane((short) 5, ChatColor.RESET + " "));
                }
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 5L);
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }
        final Player player = (Player) e.getPlayer();
        final GUI gui = GUI_MAP.get(player.getUniqueId());
        if (gui == null) {
            return;
        }
        gui.onClose(e);
        GUI_MAP.remove(player.getUniqueId());
    }

    static {
        DEFAULT_REFORGE_ITEM = SUtil.getStack(ChatColor.YELLOW + "Reforge Item", Material.ANVIL, (short) 0, 1, ChatColor.GRAY + "Place an item above to reforge", ChatColor.GRAY + "it! Reforging items adds a", ChatColor.GRAY + "random modifier to the item that", ChatColor.GRAY + "grants stat boosts.");
        COST_MAP = new HashMap<Rarity, Integer>();
        COOLDOWN = new ArrayList<UUID>();
        ReforgeAnvilGUI.COST_MAP.put(Rarity.COMMON, 250);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.UNCOMMON, 500);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.RARE, 1000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.EPIC, 2500);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.LEGENDARY, 5000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.MYTHIC, 10000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.SUPREME, 15000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.SPECIAL, 25000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.VERY_SPECIAL, 50000);
        ReforgeAnvilGUI.COST_MAP.put(Rarity.EXCLUSIVE, 1000000);
    }
}
