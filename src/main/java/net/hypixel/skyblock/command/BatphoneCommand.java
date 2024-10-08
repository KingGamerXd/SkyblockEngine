package net.hypixel.skyblock.command;

import net.hypixel.skyblock.features.ranks.PlayerRank;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.gui.GUI;
import net.hypixel.skyblock.gui.GUIType;
import net.hypixel.skyblock.item.oddities.MaddoxBatphone;
import net.hypixel.skyblock.util.SUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandParameters(description = "Hidden command for Maddox Batphone.", permission = PlayerRank.DEFAULT)
public class BatphoneCommand extends SCommand {
    public static final UUID ACCESS_KEY;
    public static final List<String> KEYS;

    @Override
    public void run(CommandSource sender, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandFailException("Console senders cannot use this command!");
        }
        if (!args[0].equals(ACCESS_KEY.toString())) {
            return;
        }
        if (!KEYS.contains(args[1])) {
            throw new CommandFailException(ChatColor.RED + "✆ It's too late now, the phone line is off! Call again!");
        }
        Player player = sender.getPlayer();
        MaddoxBatphone.CALL_COOLDOWN.add(player.getUniqueId());
        SUtil.delay(() -> MaddoxBatphone.CALL_COOLDOWN.remove(player.getUniqueId()), 400L);
        GUI gui = GUIType.SLAYER.getGUI();
        gui.open(player);
    }

    static {
        ACCESS_KEY = UUID.randomUUID();
        KEYS = new ArrayList<String>();
    }
}
