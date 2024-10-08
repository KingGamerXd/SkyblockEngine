package net.hypixel.skyblock.command;

import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.features.ranks.PlayerRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.hypixel.skyblock.user.User;
import net.hypixel.skyblock.util.SLog;
import net.hypixel.skyblock.util.SUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CommandParameters(description = "Modify your coin amount.", usage = "", aliases = "ssend", permission = PlayerRank.ADMIN)
public class SSend
        extends SCommand {
    public Map<UUID, List<String>> servers = new HashMap<UUID, List<String>>();
    public Map<UUID, List<String>> players = new HashMap<UUID, List<String>>();

    @Override
    public void run(CommandSource sender, String[] args) {

        // todo - make it work
//        UUID runUUID = UUID.randomUUID();
//        if (SkyBlock.getPlugin().getBc() == null) {
//            this.send("&cThis is not a BungeeCord based server!");
//            return;
//        }
//        if (sender.getPlayer() == null) {
//            this.send("&cConsole Sender cannot execute Proxy commands!");
//            return;
//        }
//        Player p = sender.getPlayer();
//        if (!p.hasPermission("sse.proxy.bungeesend")) {
//            this.send("&cThis command is restricted!");
//            return;
//        }
//        if (args.length != 2) {
//            this.send("&cCorrect Command Usage: /ssend <all/current/specific player> <server name>");
//            return;
//        }
//        SkyBlock.getPlugin().getBc().getServers().whenComplete((result, error) -> this.servers.put(runUUID, result));
//        for (int i = 0; i < this.servers.get(runUUID).size(); ++i) {
//            SLog.info(this.servers.get(runUUID).get(i));
//        }
//        boolean isExist = false;
//        String targetServer = null;
//        for (String sv : this.servers.get(runUUID)) {
//            SLog.info(sv);
//            if (!sv.equalsIgnoreCase(args[1])) continue;
//            targetServer = sv;
//            isExist = true;
//        }
//        if (!isExist) {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < this.servers.get(runUUID).size(); ++i) {
//                String server = this.servers.get(runUUID).get(i);
//                if (i == this.servers.get(runUUID).size() - 1) {
//                    sb.append(server);
//                    continue;
//                }
//                sb.append(server + ", ");
//            }
//            this.send("&cThat server doesn't exist! &aYou may send players to these following servers: &f" + sb);
//            this.servers.remove(runUUID);
//            this.players.remove(runUUID);
//            return;
//        }
//        String finalTarget = targetServer;
//        if (args[0].equalsIgnoreCase("all")) {
//            SkyBlock.getPlugin().getBc().getPlayerList("ALL").whenComplete((result, error) -> this.players.put(runUUID, result));
//            this.send("&7Hooking up request for all players you requested (All Servers)...");
//            for (String player : this.players.get(runUUID)) {
//                SkyBlock.getPlugin().getBc().forward("ALL", "savePlayerData", "ALL_PLAYERS".getBytes());
//                SkyBlock.getPlugin().getBc().sendMessage(player, "&7Hooking up request...");
//                SUtil.delay(() -> {
//                    SkyBlock.getPlugin().getBc().sendMessage(player, "&7Sending you to " + finalTarget + "...");
//                    SkyBlock.getPlugin().getBc().connectOther(player, finalTarget);
//                }, 8L);
//            }
//            this.servers.remove(runUUID);
//            this.players.remove(runUUID);
//        } else if (args[0].equalsIgnoreCase("current") || args[0].equalsIgnoreCase("cur")) {
//            this.send("&7Hooking up request for all players you requested (This Server)...");
//            for (Player player : Bukkit.getOnlinePlayers()) {
//                User u = User.getUser(player.getUniqueId());
//                u.send("&7Hooking up request...");
//                u.asyncSavingData();
//                SUtil.delay(() -> {
//                    u.send("&7Sending you to " + finalTarget + "...");
//                    SkyBlock.getPlugin().getBc().connect(player, finalTarget);
//                }, 8L);
//            }
//            this.servers.remove(runUUID);
//            this.players.remove(runUUID);
//        } else {
//            for (String player : this.players.get(runUUID)) {
//                if (!args[0].equalsIgnoreCase(player)) continue;
//                this.send("&7Hooking up request for " + player + "...");
//                SkyBlock.getPlugin().getBc().sendMessage(player, "&7Hooking up request...");
//                SUtil.delay(() -> {
//                    SkyBlock.getPlugin().getBc().sendMessage(player, "&7Sending you to " + finalTarget + "...");
//                    SkyBlock.getPlugin().getBc().connectOther(player, finalTarget);
//                }, 8L);
//                this.servers.remove(runUUID);
//                this.players.remove(runUUID);
//                return;
//            }
//            this.send("&cUnable to find that player, maybe they've gone offline?");
//            this.servers.remove(runUUID);
//            this.players.remove(runUUID);
//        }
    }
}

