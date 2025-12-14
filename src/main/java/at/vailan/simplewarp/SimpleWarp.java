package at.vailan.simplewarp;

import at.vailan.simplewarp.commands.DeleteWarpCommand;
import at.vailan.simplewarp.commands.WarpCommand;
import at.vailan.simplewarp.commands.SetWarpCommand;
import at.vailan.simplewarp.manager.WarpManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleWarp extends JavaPlugin {

    private static SimpleWarp plugin;

    private WarpManager warpManager;

    public WarpManager getWarpManager() { return warpManager; }

    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();
        warpManager = new WarpManager(this);
        warpManager.loadWarps();

        registerCommands();

        registerbStats();

        getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        warpManager.saveWarps();

        getLogger().info("Plugin disabled.");
    }

    private void registerCommands() {
        WarpCommand warpCmd = new WarpCommand(this);
        getCommand("warp").setExecutor(warpCmd);
        getCommand("warp").setTabCompleter(warpCmd);

        SetWarpCommand setWarpCmd = new SetWarpCommand(this);
        getCommand("setwarp").setExecutor(setWarpCmd);

        DeleteWarpCommand deleteWarpCmd = new DeleteWarpCommand(this);
        getCommand("deletewarp").setExecutor(deleteWarpCmd);
        getCommand("deletewarp").setTabCompleter(deleteWarpCmd);
    }

    private void registerbStats() {
        int pluginId = 18553;
        Metrics metrics = new Metrics(this, pluginId);
    }

    public String getPrefix() {
        return getConfig().getString("prefix", "§8[§6SimpleWarp§8] ");
    }

    public String getMessage(String key, String... placeholders) {
        String msg = getConfig().getString("messages." + key, "");

        if (placeholders.length % 2 == 0) {
            for (int i = 0; i < placeholders.length; i += 2) {
                String ph = placeholders[i];
                String value = placeholders[i + 1];
                msg = msg.replace("%" + ph + "%", value);
            }
        }

        return getPrefix() + msg;
    }

}