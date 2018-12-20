package hr.seos.sam;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;


public class Mechanics implements Listener {
    private SAM plugin = SAM.getPlugin(SAM.class);


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (plugin.running == null || plugin.running == false) {
            return;
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player victim = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            Player shark = plugin.shark;
            if (victim == shark) {
                e.setCancelled(true);
            }
            if (victim == shark || damager != shark) {
                e.setCancelled(true);
            } else {
                victim.sendMessage(ChatColor.LIGHT_PURPLE + shark.getName() + ChatColor.GREEN + ", the shark, has eaten you.");
                victim.sendMessage(ChatColor.GREEN + "You are now a " + ChatColor.LIGHT_PURPLE + "shark");
            }
        }

    }
}
