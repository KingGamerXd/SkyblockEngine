package net.hypixel.skyblock.command;


import net.hypixel.skyblock.features.ranks.PlayerRank;
import net.hypixel.skyblock.user.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@CommandParameters(description = "go to or create your island", aliases = "is", permission = PlayerRank.DEFAULT)
public class IslandCommand extends SCommand {
    @Override
    public void run(final CommandSource sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        final Player player = sender.getPlayer();
        PlayerUtils.sendToIsland(player);
       // player.sendMessage(ChatColor.RED + "Not Yet Comming in next Patch!");
    }
}
