package at.vailan.simplewarp.commands;

import at.vailan.simplewarp.Permissions;
import at.vailan.simplewarp.SimpleWarp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpCommand implements CommandExecutor, TabExecutor {

    private final SimpleWarp plugin;

    public WarpCommand(SimpleWarp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission(Permissions.USE)) {
            s.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(args.length == 0 ? plugin.getMessage("not-enough-arguments")
                    : plugin.getMessage("too-many-arguments"));
            return true;
        }

        String warpName = args[0].toLowerCase();
        if (!(plugin.getWarpManager().warpExists(warpName))) {
            p.sendMessage(plugin.getMessage("warp-not-existing", "warp", warpName));
            return true;
        }

        p.teleport(plugin.getWarpManager().getWarp(warpName));

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