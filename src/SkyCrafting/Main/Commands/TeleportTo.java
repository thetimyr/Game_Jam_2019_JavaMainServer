package SkyCrafting.Main.Commands;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;

import SkyCrafting.Main.Main;

import org.bukkit.event.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
public class TeleportTo implements Listener{
	    public static ArrayList<Player> tp;
	    
	    static {
	        TeleportTo.tp = new ArrayList<Player>();
	    }
	    
	    public static void teleport(final Player p, final Location loc, int sec) {
	        if (TeleportTo.tp.contains(p)) {
	        	p.sendTitle(null, ChatColor.DARK_RED + "Вы уже телепортируетесь!");
	            return;
	        }
	        p.sendTitle(null, ChatColor.GOLD + "Телепортация началась! Подождите " + sec + " секунд.");
	        p.sendMessage(ChatColor.GOLD + "Старайтесь не двигаться и не получать урон!");
	        TeleportTo.tp.add(p);
	        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.instance, () -> {
	            if (TeleportTo.tp.contains(p)) {
	                p.teleport(loc);
	                TeleportTo.tp.remove(p);
	                p.sendTitle(null,"§6Телепортация...");
	            }
	        }, sec * 20L);
	    }
	    
	    @EventHandler
	    public void damage(final EntityDamageEvent e) {
	        if (e.getEntity() instanceof Player) {
	            final Player p = (Player)e.getEntity();
	            if (TeleportTo.tp.contains(p)) {
	                TeleportTo.tp.remove(p);
	                p.sendMessage(ChatColor.GOLD + "Телепортация не совершена, вы получали урон ;C");
	            }
	        }
	    }
	    
	    @EventHandler
	    public void move(final PlayerMoveEvent e) {
	        final Player p = e.getPlayer();
	        if (!e.getFrom().getBlock().equals(e.getTo().getBlock()) && TeleportTo.tp.contains(p)) {
	            TeleportTo.tp.remove(p);
	            p.sendMessage(ChatColor.GOLD + "Телепортация не совершена, вы двигались ;C");
	        }
	    }
	}
