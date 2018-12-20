package hr.seos.sam;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class Mechanics implements Listener {
    private SAM plugin = SAM.getPlugin(SAM.class);


    @EventHandler
    public void onDamage (EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player vicitm = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            damager.sendMessage(plugin.shark.toString());
            vicitm.sendMessage(plugin.shark.toString());

        }
    }
}
