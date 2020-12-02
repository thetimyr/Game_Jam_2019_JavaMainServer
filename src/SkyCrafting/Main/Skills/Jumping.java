package SkyCrafting.Main.Skills;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Main;

import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class Jumping implements Listener
{
    private HashSet<Player> jumping;
    static WeakHashMap<Player, Long> cols;
    
    static {
        Jumping.cols = new WeakHashMap<Player, Long>();
    }
    
    public Jumping() {
        this.jumping = new HashSet<Player>();
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Jumping.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезарежаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        Jumping.cols.put(user, time);
        return true;
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.PAPER) {
            return;
        }
        if (Main.instance.mesagges(p, 8)) {
            return;
        }
        if (check(p, 15000L)) {
            final Vector v = p.getLocation().getDirection();
            v.setY(0).normalize().multiply(4).setY(1.5);
            p.setVelocity(v);
            this.jumping.add(p);
        }
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player && this.jumping.contains(e.getEntity())) {
            e.setCancelled(true);
            this.jumping.remove(e.getEntity());
        }
    }
}
