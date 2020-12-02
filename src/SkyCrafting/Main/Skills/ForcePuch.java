package SkyCrafting.Main.Skills;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Main;

import org.bukkit.event.*;

public class ForcePuch implements Listener
{
    double a;
    static WeakHashMap<Player, Long> cols;
    
    static {
        ForcePuch.cols = new WeakHashMap<Player, Long>();
    }
    
    public ForcePuch() {
        this.a = 3.0;
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = ForcePuch.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезаряжаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        ForcePuch.cols.put(user, time);
        return true;
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player Player = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.INK_SACK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getDurability() != 12) {
            return;
        }
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.chekSith(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 7)) {
            return;
        }
        if (check(Player, 10000L)) {
            for (final Entity entity : Player.getNearbyEntities(5.0, 5.0, 5.0)) {
                if (!(entity instanceof LivingEntity)) {
                    continue;
                }
                if (entity instanceof Villager) {
                    return;
                }
                if (Main.instance.CheckregionLivingEntity(entity)) {
                    Player.sendMessage("Вы не можете откидывать игроков рядом со спавном!");
                    return;
                }
                final LivingEntity target = (LivingEntity)entity;
                if (Main.instance.checEntytiJedi(target)) {
                    return;
                }
                final Vector p = Player.getLocation().toVector();
                final Vector e2 = target.getLocation().toVector();
                final Vector v = e2.subtract(p).normalize().multiply(this.a);
                if (this.a != 0.0) {
                    v.setY(v.getY() + 1.5);
                }
                else {
                    v.setY(1.5);
                }
                if (v.getY() > 2.0) {
                    v.setY(2);
                }
                target.setVelocity(v);
            }
        }
    }
}
