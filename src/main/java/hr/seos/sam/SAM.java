package hr.seos.sam;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public final class SAM extends JavaPlugin {

    public Permission pp = new Permission("sam.admin");

    @Override
    public void onEnable() {
        PluginManager register = Bukkit.getServer().getPluginManager();
        getCommand("sam").setExecutor(new Commands());
        register.addPermission(pp);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
