package net.hypixel.skyblock.item.oddities;

import net.hypixel.skyblock.item.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.item.*;

public class SkyBlockMenu implements MaterialStatistics, MaterialFunction, Untradeable {
    @Override
    public String getDisplayName() {
        return ChatColor.GREEN + "SkyBlock Menu " + ChatColor.GRAY + "(Right Click)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.EXCLUSIVE;
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }

    @Override
    public String getLore() {
        return "View all of your progress, including your Skills, Collections, Recipes, and more!";
    }

    @Override
    public boolean displayRarity() {
        return false;
    }

    @Override
    public void onInteraction(final PlayerInteractEvent e) {
        GUIType.SKYBLOCK_MENU.getGUI().open(e.getPlayer());
    }

    @Override
    public void onInventoryClick(final SItem instance, final InventoryClickEvent e) {
        e.setCancelled(true);
        GUIType.SKYBLOCK_MENU.getGUI().open((Player) e.getWhoClicked());
    }
}
