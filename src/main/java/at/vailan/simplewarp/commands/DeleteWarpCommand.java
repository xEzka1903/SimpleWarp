package at.vailan.simplewarp.commands;

import at.vailan.simplewarp.Permissions;
import at.vailan.simplewarp.SimpleWarp;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteWarpCommand implements CommandExecutor, TabCompleter {

    private final SimpleWarp plugin;

    public DeleteWarpCommand(SimpleWarp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        if (!p.hasPermission(Permissions.DELETE)) {
            p.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(args.length == 0 ? plugin.getMessage("not-enough-arguments")
                    : plugin.getMessage("too-many-arguments"));
            return true;
        }

        String warpName = args[0].toLowerCase();
        if (!plugin.getWarpManager().warpExists(warpName)) {
            p.sendMessage(plugin.getMessage("warp-not-existing", "warp", warpName));
            return true;
        }

        plugin.getWarpManager().removeWarp(warpName);
        p.sendMessage(plugin.getMessage("warp-successfully-deleted", "warp", warpName));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        List<String> warps = new ArrayList<>(plugin.getWarpManager().getAllWarpNames());

        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            return warps.stream()
                    .filter(w -> w.toLowerCase().startsWith(partial))
                    .collect(Collectors.toList());
        }

        return List.of();
    }

}