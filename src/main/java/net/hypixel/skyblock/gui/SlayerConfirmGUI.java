package net.hypixel.skyblock.gui;

import net.hypixel.skyblock.features.slayer.SlayerBossType;
import net.hypixel.skyblock.util.SUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SlayerConfirmGUI extends GUI {
    public SlayerConfirmGUI(final SlayerBossType type, final Runnable onConfirm) {
        super("Confirm", 27);
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                onConfirm.run();
                e.getWhoClicked().closeInventory();
            }

            @Override
            public ItemStack getItem() {
                return SUtil.getStack(ChatColor.GREEN + "Confirm", Material.STAINED_CLAY, (short) 13, 1, ChatColor.GRAY + "Kill " + type.getType().getPluralName() + " to spawn the", ChatColor.GRAY + "boss!", "", ChatColor.YELLOW + "Click to start quest!");
            }

            @Override
            public int getSlot() {
                return 11;
            }
        });
        this.set(new GUIClickableItem() {
            @Override
            public void run(final InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
            }

            @Override
            public ItemStack getItem() {
                return SUtil.getStack(ChatColor.RED + "Cancel", Material.STAINED_CLAY, (short) 14, 1);
            }

            @Override
            public int getSlot() {
                return 15;
            }
        });
    }
}
