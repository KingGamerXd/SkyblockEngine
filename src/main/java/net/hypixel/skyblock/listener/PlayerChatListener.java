package net.hypixel.skyblock.listener;



import net.hypixel.skyblock.features.ranks.PlayerRank;
import net.hypixel.skyblock.listener.PListener;
import net.hypixel.skyblock.user.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener extends PListener {
    @EventHandler(priority = EventPriority.LOW)
    public void Event(AsyncPlayerChatEvent e) {
        String userTag;
        User data = User.getUser(e.getPlayer());
        e.setFormat("%1$s" + (data.rank == PlayerRank.DEFAULT ? ChatColor.GRAY : ChatColor.WHITE) + ": %2$s");
        e.getPlayer().setDisplayName(e.getPlayer().getName());
        PlayerRank rank = data.rank;
        if (rank == PlayerRank.DEFAULT) {
            userTag = rank.getPrefix() + e.getPlayer().getName();
        } else {
            userTag = rank.getPrefix() + " " + e.getPlayer().getName();
        }
        if (!e.getPlayer().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', userTag))) {
            e.getPlayer().setDisplayName(ChatColor.translateAlternateColorCodes('&', userTag));
        }
        if (rank.isAboveOrEqual(PlayerRank.MVPPLUSPLUS)) {
            e.setMessage(e.getMessage().replace("<3", "§c❤").replace("⭐", "§6✭").replace(":owo:", "§dO§5w§dO").replace("o/", "§d(/◕ヮ◕)/").replace(":OOF:", "§c§lOOF").replace(":123:", "§a1§e2§c3").replace(":shrug:", "§e¯\\(ツ)/¯").replace(":yes:", "§a✔").replace(":no:", "§c✖").replace(":java:", "§b♨").replace(":arrow:", "§e➡").replace(":typing:", "§e✎§6..."));
        }
    }
}