package net.hypixel.skyblock.command;

import net.hypixel.skyblock.features.ranks.PlayerRank;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import net.hypixel.skyblock.item.SItem;

@CommandParameters(description = "Sets data for a Spec item.", permission = PlayerRank.ADMIN)
public class DataCommand extends SCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        if (3 > args.length) {
            throw new CommandArgumentException();
        }
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        Player player = sender.getPlayer();
        PlayerInventory inv = player.getInventory();
        if (null == inv.getItemInHand()) {
            throw new CommandFailException(ChatColor.RED + "Error! Hold an item in your hand!");
        }
        SItem sItem = SItem.find(inv.getItemInHand());
        String key = args[0];
        if (!sItem.hasDataFor(key)) {
            throw new CommandFailException(ChatColor.RED + "Error! This item does not have data for '" + key + "'");
        }
        String joined = StringUtils.join(args, " ", 1, args.length - 1);
        String lowerCase = args[args.length - 1].toLowerCase();
        switch (lowerCase) {
            case "string":
                sItem.setDataString(key, joined);
                break;
            case "integer":
            case "int":
                sItem.setDataInt(key, Integer.parseInt(joined));
                break;
            case "long":
                sItem.setDataLong(key, Long.parseLong(joined));
                break;
            case "boolean":
            case "bool":
                sItem.setDataBoolean(key, Boolean.parseBoolean(joined));
                break;
            case "double":
            case "d":
                sItem.setDataDouble(key, Double.parseDouble(joined));
                break;
            case "float":
            case "f":
                sItem.setDataFloat(key, Float.parseFloat(joined));
                break;
        }
        sItem.update();
        this.send(ChatColor.GREEN + "'" + key + "' for this item has been set to '" + joined + "' as type '" + args[args.length - 1].toLowerCase() + "'");
    }
}
