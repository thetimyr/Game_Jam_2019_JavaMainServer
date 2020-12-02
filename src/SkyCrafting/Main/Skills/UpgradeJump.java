package SkyCrafting.Main.Skills;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class UpgradeJump implements Listener
{
    private HashSet<Player> jumping;
    static WeakHashMap<Player, Long> cols;
    
    static {
        UpgradeJump.cols = new WeakHashMap<Player, Long>();
    }
    
    public UpgradeJump() {
        this.jumping = new HashSet<Player>();
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = UpgradeJump.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезарежаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        UpgradeJump.cols.put(user, time);
        return true;
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.RECORD_4) {
            return;
        }
        if (Main.instance.mesagges(p, 15)) {
            return;
        }
        if (check(p, 15000L)) {
            final Vector v = p.getLocation().getDirection();
            v.setY(0).normalize().multiply(4).setY(1.5);
            p.setVelocity(v);
            Telekines.CreateEffect(p.getLocation());
            this.jumping.add(p);
            for (final Entity entity : p.getNearbyEntities(5.0, 5.0, 5.0)) {
                if (!(entity instanceof LivingEntity)) {
                    continue;
                }
                if (entity instanceof Villager) {
                    return;
                }
                if (Main.instance.CheckregionLivingEntity(entity)) {
                    p.sendMessage("Вы не можете накласть эфект на игроков рядом со спавном!");
                    return;
                }
                final LivingEntity target = (LivingEntity)entity;
                if (target.equals(p)) {
                    final Player craftp = (Player)target;
                    if (Levels.getFaction(p).equalsIgnoreCase(Levels.getFaction(craftp))) {
                        return;
                    }
                }
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 150, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
            }
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
