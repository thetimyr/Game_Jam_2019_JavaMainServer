package SkyCrafting.Main.Skills;

import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Item;

import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Main;

import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.*;

public class Drainlife implements Listener
{
    static WeakHashMap<Player, Long> cols;
    
    static {
        Drainlife.cols = new WeakHashMap<Player, Long>();
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Drainlife.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезаряжаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        Drainlife.cols.put(user, time);
        return true;
    }
    
    @SuppressWarnings("rawtypes")
	public static void CreateEffect(final Location loc) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float)(loc.getX() + 0.0), (float)(loc.getY() + 1.0), (float)(loc.getZ() + 0.0), 0.0f, 0.0f, 0.0f, 0.0f, 10, new int[0]);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void effect(final Location loc, final Player p) {
        final BukkitRunnable runable = new BukkitRunnable() {
            Vector current = loc.toVector();
            World world = p.getWorld();
            int timer = 100;
            
            public void run() {
                if (this.timer-- >= 0) {
                    final Vector targetVector = p.getLocation().toVector();
                    final Vector tempvector = this.current.clone();
                    tempvector.subtract(p.getLocation().toVector()).normalize();
                    final Vector diff = this.current.subtract(tempvector);
                    final Location loc1 = diff.toLocation(this.world);
                    Drainlife.CreateEffect(loc1);
                    if (this.current.distanceSquared(targetVector) < 4.0) {
                        double h = p.getHealth() + 7.0;
                        if (h > p.getMaxHealth()) {
                            h = p.getMaxHealth();
                        }
                        p.setHealth(h);
                        this.cancel();
                    }
                }
                else {
                    this.cancel();
                }
            }
        };
        runable.runTaskTimer((Plugin)Main.instance, 0L, 1L);
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player Player = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.BOOK) {
            return;
        }
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.chekJedi(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 14)) {
            return;
        }
        final LivingEntity entityTarget = Main.instance.rayTraceEntity(Player, 16);
        if (entityTarget == null || entityTarget.getPassenger() != null || entityTarget instanceof Villager || entityTarget instanceof Item || entityTarget.isOp()) {
            Player.sendMessage("Цель не обнаружена!");
        }
        else {
            if (Main.instance.checEntytiSith(entityTarget)) {
                return;
            }
            if (Main.instance.CheckregionLivingEntity((Entity)entityTarget)) {
                Player.sendMessage("Вы не можете использовать это рядом со спавном!");
                return;
            }
            if (check(Player, 20000L)) {
                double health = entityTarget.getHealth() - 4.0;
                if (health < 0.0) {
                    health = 0.0;
                }
                entityTarget.setHealth(health);
                entityTarget.playEffect(EntityEffect.HURT);
                effect(entityTarget.getLocation(), Player);
            }
        }
    }
}
