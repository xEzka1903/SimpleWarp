package at.vailan.simplewarp.commands;

import at.vailan.simplewarp.Permissions;
import at.vailan.simplewarp.SimpleWarp;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final SimpleWarp plugin;

    public SetWarpCommand(SimpleWarp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        if (!p.hasPermission(Permissions.SET)) {
            p.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(args.length == 0 ? plugin.getMessage("not-enough-arguments")
                    : plugin.getMessage("too-many-arguments"));
            return true;
        }

        Location loc = p.getLocation();

        String warpName = args[0].toLowerCase();
        if (plugin.getWarpManager().warpExists(warpName)) {
            p.sendMessage(plugin.getMessage("warp-already-exists", "warp", warpName));
            return true;
        }

        plugin.getWarpManager().addWarp(warpName, loc);
        p.sendMessage(plugin.getMessage("warp-successfully-created", "warp", warpName));

        return true;
    }

}