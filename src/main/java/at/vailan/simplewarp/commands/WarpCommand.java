package at.vailan.simplewarp.commands;

import at.vailan.simplewarp.Permissions;
import at.vailan.simplewarp.SimpleWarp;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private final SimpleWarp plugin;

    public WarpCommand(SimpleWarp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (!s.hasPermission(Permissions.USE)) {
            s.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length == 0) {
            s.sendMessage(plugin.getMessage("not-enough-arguments"));
            return true;
        }

        if (args.length > 2) {
            s.sendMessage(plugin.getMessage("too-many-arguments"));
            return true;
        }

        String warpName = args[0].toLowerCase();
        if (!(plugin.getWarpManager().warpExists(warpName))) {
            s.sendMessage(plugin.getMessage("warp-not-existing", "warp", warpName));
            return true;
        }

        if (args.length == 2) {
            if (!s.hasPermission(Permissions.EXECUTE)) {
                s.sendMessage(plugin.getMessage("no-permission"));
                return true;
            }

            Player t = Bukkit.getPlayerExact(args[1]);
            if (t == null || !t.isOnline()) {
                s.sendMessage(plugin.getMessage("player-not-existing", "player", args[1]));
                return true;
            }

            t.teleport(plugin.getWarpManager().getWarp(warpName));
            return true;
        }

        if(s instanceof Player p) {
            p.teleport(plugin.getWarpManager().getWarp(warpName));
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player p)) return completions;

        if (args.length == 1) {
            if (!p.hasPermission(Permissions.USE)) return completions;
            completions.addAll(plugin.getWarpManager().getAllWarpNames());
        }

        else if (args.length == 2) {
            if (!p.hasPermission(Permissions.EXECUTE)) return completions;
            for (Player online : Bukkit.getOnlinePlayers()) {
                completions.add(online.getName());
            }
        }

        return completions;
    }

}