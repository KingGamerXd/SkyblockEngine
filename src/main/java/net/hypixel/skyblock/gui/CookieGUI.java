package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.user.PlayerUtils;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SUtil;
import net.hypixel.skyblock.util.Sputnik;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class CookieGUI extends GUI {
    public CookieGUI(String query, int page) {
        super("Booster Cookie", 36);
        this.fill(CookieGUI.BLACK_STAINED_GLASS_PANE);
    }

    @Override
    public void onOpen(GUIOpenEvent e) {
        Player player = e.getPlayer();
        User user = User.getUser(player.getUniqueId());
        String text_ = "&7Status";
        if (0L < PlayerUtils.getCookieDurationTicks(player)) {
            text_ = "&7Duration";
        }
        this.set(new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                GUIType.SKYBLOCK_MENU.getGUI().open((Player) e.getWhoClicked());
            }

            @Override
            public int getSlot() {
                return 31;
            }

            @Override
            public ItemStack getItem() {
                return SUtil.getStack(Sputnik.trans("&aGo Back"), Material.ARROW, (short) 0, 1, ChatColor.GRAY + "To SkyBlock Menu");
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.ANVIL.getGUI().open((Player) e.getWhoClicked());
                } else {
                    ((Player) e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }

            @Override
            public int getSlot() {
                return 30;
            }

            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to check it out!";
                }
                return SUtil.getStack(Sputnik.trans("&6Anvil"), Material.ANVIL, (short) 0, 1, ChatColor.DARK_GRAY + "/av", "", ChatColor.GRAY + "Access an Anvil from anywhere in", ChatColor.GRAY + "SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Disabled bruh.");
                } else {
                    ((Player) e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }

            @Override
            public int getSlot() {
                return 32;
            }

            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to check it out!";
                }
                return SUtil.getStack(Sputnik.trans("&6Auction House"), Material.GOLD_BARDING, (short) 0, 1, ChatColor.DARK_GRAY + "/auh", "", ChatColor.GRAY + "Access the Auction House menu", ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.TRASH.getGUI().open((Player) e.getWhoClicked());
                } else {
                    ((Player) e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }

            @Override
            public int getSlot() {
                return 33;
            }

            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to open!";
                }
                return SUtil.getStack(Sputnik.trans("&6Trash Bin"), Material.CAULDRON_ITEM, (short) 0, 1, ChatColor.DARK_GRAY + "/bin", "", ChatColor.GRAY + "Access your personal Trash Bin", ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    GUIType.FARM_MERCHANT.getGUI().open((Player) e.getWhoClicked());
                } else {
                    ((Player) e.getWhoClicked()).playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                    e.getWhoClicked().sendMessage(Sputnik.trans("&cYou need the Cookie Buff active to use this feature!"));
                }
            }

            @Override
            public int getSlot() {
                return 29;
            }

            @Override
            public ItemStack getItem() {
                String text = "&cRequires Cookie Buff!";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text = "&eClick to open!";
                }
                return SUtil.getStack(Sputnik.trans("&6Farm Merchant"), Material.APPLE, (short) 0, 1, ChatColor.DARK_GRAY + "/fm", "", ChatColor.GRAY + "Access the Farm Merchant menu", ChatColor.GRAY + "from anywhere in SkySim SkyBlock.", "", Sputnik.trans(text));
            }
        });
        this.set(11, SUtil.enchant(SUtil.getStack(ChatColor.GOLD + "Booster Cookie", Material.COOKIE, (short) 0, 1, ChatColor.GRAY + "Aquire booster cookies from", ChatColor.GRAY + "the community shop in the hub.", "", Sputnik.trans("&dCookie Buff:"), Sputnik.trans("&8▶ &b+35% &7Bonus Combat XP"), Sputnik.trans("&8▶ &b+30✯ &7Bonus Magic Find"), Sputnik.trans("&8▶ &c+100❁ &7Bonus Strength"), Sputnik.trans("&8▶ &a+200❈ &7Bonus Defense"), Sputnik.trans("&8▶ &9+25☠ &7Bonus Crit Damage"), Sputnik.trans("&8▶ &c+35⫽ &7Bonus Ferocity"), Sputnik.trans("&8▶ &b+2000✎ &7Bonus Intelligence"), Sputnik.trans("&8▶ &7Keep &6coins &7and &deffects &7on death"), Sputnik.trans("&8▶ &7Access to &6/auh &7and &6/fm"), Sputnik.trans("&8▶ &7Access to &6/av &7and &6/bin &7(Trash Bin)"), Sputnik.trans("&8▶ &7A shiny &e✪ &6Badge &7on your &aname tag."), "", Sputnik.trans("&7" + text_ + ": " + PlayerUtils.getCookieDurationDisplayGUI(player)), "", Sputnik.trans("&8NOTE: All effects listed above given"), Sputnik.trans("&8by the cookie will NOT stack!"))));
        this.set(13, SUtil.getStack(ChatColor.AQUA + "Bits", Material.DIAMOND, (short) 0, 1, ChatColor.GRAY + "You can earn Bits from killing", ChatColor.GRAY + "mobs with melee weapons. Only.", "", Sputnik.trans("&7Bits Purse: &b" + PlaceholderAPI.setPlaceholders(player, "%royaleeconomy_balance_purse%")), "", Sputnik.trans("&8Bits can be used to purchase Booster"), Sputnik.trans("&8Cookie and various items in the Community"), Sputnik.trans("&8Shop.")));
        this.set(15, SUtil.enchant(SUtil.getStack(ChatColor.GOLD + "Fame Rank", Material.GOLD_HELMET, (short) 0, 1, ChatColor.GRAY + "This feature is not available.", "", "" + ChatColor.RED + ChatColor.BOLD + "NOT COMING SOON!", "", Sputnik.trans("&8Not planned release."))));
        new BukkitRunnable() {
            public void run() {
                String text_ = "&7Status";
                if (0L < PlayerUtils.getCookieDurationTicks(player)) {
                    text_ = "&7Duration";
                }
                if (CookieGUI.this != GUI.GUI_MAP.get(player.getUniqueId())) {
                    this.cancel();
                    return;
                }
                InventoryView stackInventory = player.getOpenInventory();
                ItemStack craftStack = stackInventory.getItem(11);
                ItemMeta meta = craftStack.getItemMeta();
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Aquire booster cookies from", ChatColor.GRAY + "the community shop in the hub.", "", Sputnik.trans("&dCookie Buff:"), Sputnik.trans("&8▶ &b+35% &7Bonus Combat XP"), Sputnik.trans("&8▶ &b+30✯ &7Bonus Magic Find"), Sputnik.trans("&8▶ &c+100❁ &7Bonus Strength"), Sputnik.trans("&8▶ &a+200❈ &7Bonus Defense"), Sputnik.trans("&8▶ &9+25☠ &7Bonus Crit Damage"), Sputnik.trans("&8▶ &c+35⫽ &7Bonus Ferocity"), Sputnik.trans("&8▶ &b+2000✎ &7Bonus Intelligence"), Sputnik.trans("&8▶ &7Keep &6coins &7and &deffects &7on death"), Sputnik.trans("&8▶ &7Access to &6/auh &7and &6/fm"), Sputnik.trans("&8▶ &7Access to &6/av &7and &6/bin &7(Trash Bin)"), Sputnik.trans("&8▶ &7A shiny &e✪ &6Badge &7on your &aname tag."), "", Sputnik.trans("&7" + text_ + ": " + PlayerUtils.getCookieDurationDisplayGUI(player)), "", Sputnik.trans("&8NOTE: All effects listed above given"), Sputnik.trans("&8by the cookie will NOT stack!")));
                craftStack.setItemMeta(meta);
            }
        }.runTaskTimer(SkyBlock.getPlugin(), 0L, 20L);
    }

    public CookieGUI(String query) {
        this(query, 1);
    }

    public CookieGUI(int page) {
        this("", page);
    }

    public CookieGUI() {
        this(1);
    }
}
