package SkyCrafting.Main.Skills;

import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Item;

import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Main;

import org.bukkit.potion.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.*;

public class Perelivania implements Listener
{
    static WeakHashMap<Player, Long> cols;
    
    static {
    	Perelivania.cols = new WeakHashMap<Player, Long>();
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Perelivania.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезаряжаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
            
        }
        Perelivania.cols.put(user, time);
        return true;
    }
    
    @SuppressWarnings("rawtypes")
	public static void CreateEffect(final Location loc) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, (float)(loc.getX() + 0.0), (float)(loc.getY() + 1.0), (float)(loc.getZ() + 0.0), 0.0f, 0.0f, 0.0f, 0.0f, 10, new int[0]);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void effect(final Location loc, final LivingEntity entityTarget) {
        final BukkitRunnable runable = new BukkitRunnable() {
            Vector current = loc.toVector();
            World world = entityTarget.getWorld();
            int timer = 100;
            
            public void run() {
                if (this.timer-- >= 0) {
                    final Vector targetVector = entityTarget.getLocation().toVector();
                    final Vector tempvector = this.current.clone();
                    tempvector.subtract(entityTarget.getLocation().toVector()).normalize();
                    final Vector diff = this.current.subtract(tempvector);
                    final Location loc1 = diff.toLocation(this.world);
                    VspyshkaSilu.CreateEffect(loc1);
                    if (this.current.distanceSquared(targetVector) < 4.0) {
                        entityTarget.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
                        this.cancel();
                    }
                }
                else {
                    this.cancel();
                }
            }
        };
        runable.runTaskTimer((Plugin)Main.instance, 1L, 1L);
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player Player = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.CAULDRON) {
            return;
        }
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 20)) {
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
            if (check(Player, 60000L)) {
                effect(Player.getLocation(), entityTarget);
                
                {       Player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1)); 
                }
                }
            }
        }
     }
