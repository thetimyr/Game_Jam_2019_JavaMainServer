package SkyCrafting.Main.Skills;

import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Item;

import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;

public class Telekines implements Listener
{
    static WeakHashMap<Player, Long> cols;
    List<String> itemjedi;
    List<String> itemsith;
    List<String> itemsali;
    List<String> Level6;
    List<String> alince;
    List<String> Level10;
    List<String> Level15;
    List<String> Level20;
    List<String> Level22;
    
    static {
        Telekines.cols = new WeakHashMap<Player, Long>();
    }
    
    public Telekines() {
        this.itemjedi = (List<String>)Main.instance.getConfig().getStringList("jedi.ZapretItems");
        this.itemsith = (List<String>)Main.instance.getConfig().getStringList("sith.ZapretItems");
        this.itemsali = (List<String>)Main.instance.getConfig().getStringList("ali.ZapretItems");
        this.Level6 = (List<String>)Main.instance.getConfig().getStringList("levels.6.DostupItem");
        this.alince = (List<String>)Main.instance.getConfig().getStringList("Loacation");
        this.Level10 = (List<String>)Main.instance.getConfig().getStringList("levels.10.DostupItem");
        this.Level15 = (List<String>)Main.instance.getConfig().getStringList("levels.15.DostupItem");
        this.Level20 = (List<String>)Main.instance.getConfig().getStringList("levels.20.DostupItem");
        this.Level22 = (List<String>)Main.instance.getConfig().getStringList("levels.22.DostupItem");
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Telekines.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезаряжаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        Telekines.cols.put(user, time);
        return true;
    }
    
    @SuppressWarnings("rawtypes")
	public static void CreateEffect(final Location loc) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float)(loc.getX() + 0.0), (float)(loc.getY() + 0.0), (float)(loc.getZ() + 0.0), 0.1f, 0.3f, 0.3f, 0.0f, 10, new int[0]);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void effect(final LivingEntity p) {
        final BukkitRunnable runable = new BukkitRunnable() {
            int timer = 70;
            int timer5 = 20;
            
            public void run() {
                Telekines.CreateEffect(p.getLocation());
                if (this.timer-- >= 0) {
                    if (this.timer5-- < 0) {
                        p.setVelocity(new Vector(0.0, 0.0025, 0.0));
                    }
                    else {
                        p.setVelocity(new Vector(0.0, 0.3, 0.0));
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
    public void damage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player damager = (Player)e.getDamager();
            if (!Levels.getSide(damager).equalsIgnoreCase("Jedi") && !Levels.getSide(damager).equalsIgnoreCase("Sith")) {
                final String itemd = damager.getItemInHand().getType().toString();
                if (this.alince.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Вы не можете использовать этот предмет, выбирите фракцию");
                }
                return;
            }
            if (Levels.faction.get(damager.getName()).equalsIgnoreCase("Jedi")) {
                final String itemd = damager.getItemInHand().getType().toString();
                if (this.itemjedi.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет предмет не принадлежит вашей фракции!");
                    return;
                }
                if (Levels.getLevel(damager) < 10 && this.Level10.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 10 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 6 && this.Level6.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 6 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 15 && this.Level15.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 15 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 20 && this.Level20.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 20 уровня");
                }
                if (Levels.getLevel(damager) < 22 && this.Level22.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 22 уровня");
                }
            }
            if (Levels.faction.get(damager.getName()).equalsIgnoreCase("Ali")) {
                final String itemd = damager.getItemInHand().getType().toString();
                if (this.itemsali.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет предмет не принадлежит вашей фракции!");
                    return;
                }
                if (Levels.getLevel(damager) < 6 && this.Level6.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 6 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 10 && this.Level10.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 10 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 15 && this.Level15.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 15 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 20 && this.Level20.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 20 уровня");
                }
                if (Levels.getLevel(damager) < 22 && this.Level22.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 22 уровня");
                }
            }
            if (Levels.faction.get(damager.getName()).equalsIgnoreCase("Sith")) {
                final String itemd = damager.getItemInHand().getType().toString();
                if (this.itemsith.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет предмет не принадлежит вашей фракции!");
                    return;
                }
                if (Levels.getLevel(damager) < 6 && this.Level6.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 6 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 10 && this.Level10.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 10 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 15 && this.Level15.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 15 уровня");
                    return;
                }
                if (Levels.getLevel(damager) < 20 && this.Level20.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 20 уровня");
                }
                if (Levels.getLevel(damager) < 22 && this.Level22.contains(itemd)) {
                    e.setCancelled(true);
                    damager.sendMessage(ChatColor.RED + "Данный предмет доступен с 22 уровня");
                }
               
            }
        }
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player Player = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.QUARTZ) {
            return;
        }
        final LivingEntity entityTarget = Main.instance.rayTraceEntity(Player, 16);
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.chekSith(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 14)) {
            return;
        }
        if (entityTarget == null || entityTarget.getPassenger() != null || entityTarget instanceof Villager || entityTarget instanceof Item || entityTarget.isOp()) {
            Player.sendMessage("Цель не обнаружена!");
            return;
        }
        if (Main.instance.checEntytiJedi(entityTarget)) {
            return;
        }
        if (Main.instance.CheckregionLivingEntity((Entity)entityTarget)) {
            Player.sendMessage("Вы не можете использовать это рядом со спавном!");
            return;
        }
        if (check(Player, 20000L)) {
            effect(entityTarget);
        }
    }
}
