package SkyCrafting.Main.Entyti.quest;

import org.bukkit.util.Vector;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

import SkyCrafting.Main.Entyti.*;
import org.bukkit.event.world.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;

import SkyCrafting.Main.*;
import org.bukkit.plugin.*;

public class Bosslistener implements Listener
{
    static ArrayList<String> list;
    public static String rr2d2;
    static String r1;
    static Random rand;
    static final HashMap<String, Vector> center;
    static final HashMap<String, Integer> last;
    
    static {
        Bosslistener.list = new ArrayList<String>();
        Bosslistener.rr2d2 = "R2D2";
        Bosslistener.r1 = "§5§lПотерянный световой меч";
        Bosslistener.rand = new Random();
        center = new HashMap<String, Vector>();
        last = new HashMap<String, Integer>();
    }
    
    public static ArrayList<String> list1(final Player p, final String q, final boolean r) {
        Bosslistener.list.add("§fЗдраствуй путник! §fМеня зовут R2D2, я дроид.");
        Bosslistener.list.add("§fДело в том что Люк, мой хозяин, доверил мне охранять");
        Bosslistener.list.add("§fего световой меч, но я потерял его. Можешь помочь мне");
        Bosslistener.list.add("§fнайти его?");
        if (r) {
            p.playSound(p.getLocation(), Sound.CAT_MEOW, 1.0f, 1.0f);
            if (q == "1") {
                Bosslistener.list.add("§aКликните что бы взять задание...");
            }
            else {
                if (q != "0") {
                    return Bosslistener.list;
                }
                Bosslistener.list.add("§aКликните что бы сдать задание...");
            }
            return Bosslistener.list;
        }
        return Bosslistener.list;
    }
    
    
    public static void cust(final Entity p) {
        Vector loc = Bosslistener.center.get(p.getName());
        Vector current;
        if (loc == null) {
            loc = p.getLocation().toVector();
            Bosslistener.center.put(p.getName(), loc);
            current = getCurrent(-1);
            Bosslistener.last.put(p.getName(), 0);
        }
        else {
            final int last1 = Bosslistener.last.get(p.getName());
            current = getCurrent(last1);
            Bosslistener.last.put(p.getName(), (last1 + 1) % 4);
        }
        p.setVelocity(current);
    }
    
    public static void playDamageEffect(final Entity rd, final Location l) {
        l.getWorld().playEffect(l, Effect.STEP_SOUND, 152);
        l.setY(l.getY() + 1.0);
        l.getWorld().playEffect(l, Effect.STEP_SOUND, 152);
        l.getWorld().playSound(l, Sound.HURT_FLESH, 1.0f, 1.0f);
    }
    
    public static ArrayList<ItemStack> List(final int Enchantmen) {
        final ArrayList<ItemStack> a1 = new ArrayList<ItemStack>();
        a1.add(Creat(Material.DIAMOND_HELMET, Enchantmen));
        a1.add(Creat(Material.DIAMOND_BOOTS, Enchantmen));
        a1.add(Creat(Material.DIAMOND_CHESTPLATE, Enchantmen));
        a1.add(Creat(Material.DIAMOND_LEGGINGS, Enchantmen));
        return a1;
    }
    
    public static ItemStack Creat(final Material t, final int e) {
        final ItemStack r = new ItemStack(t);
        if (e == 0) {
            return r;
        }
        r.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, e);
        return r;
    }
    public static ArrayList<ItemStack> ListIron(final int Enchantmen) {
        final ArrayList<ItemStack> a1 = new ArrayList<ItemStack>();
        a1.add(Creatiron(Material.IRON_HELMET, Enchantmen));
        a1.add(Creatiron(Material.IRON_BOOTS, Enchantmen));
        a1.add(Creatiron(Material.IRON_CHESTPLATE, Enchantmen));
        a1.add(Creatiron(Material.IRON_LEGGINGS, Enchantmen));
        return a1;
    }
    
    public static ItemStack Creatiron(final Material t, final int e) {
        final ItemStack r = new ItemStack(t);
        if (e == 0) {
            return r;
        }
        r.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, e);
        return r;
    }
    
    private static Vector getCurrent(final int last) {
        switch (last) {
            case 0: {
                return new Vector(0.0, 0.25, 1.0);
            }
            case 1: {
                return new Vector(-1.0, 0.25, 0.0);
            }
            case 2: {
                return new Vector(0.0, 0.25, -1.0);
            }
            default: {
                return new Vector(1.0, 0.25, 0.0);
            }
        }
    }
    
    public static void droped(final double amaut, final Material material, final CraftEntity r, final int count) {
        if (Bosslistener.rand.nextFloat() <= amaut) {
            r.getWorld().dropItemNaturally(r.getLocation(), new ItemStack(material, count));
        }
    }
    
    public static void HoloDiad(final Entity type, final int cost, final int Diad, final int skill) {
        if (cost == 0) {
            new GologramsDiad(type, 1.0).SetName("§6 +0$");
            new GologramsDiad(type, 1.3).SetName("§6+0 Убийство");
            new GologramsDiad(type, 1.6).SetName("§6+0 Опыт");
        }
        else {
            new GologramsDiad(type, 1.0).SetName("§6 +" + cost + "$");
            new GologramsDiad(type, 1.3).SetName("§6+" + Diad + " Убийство");
            new GologramsDiad(type, 1.6).SetName("§6+" + skill + " Опыт");
        }
    }
    
    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent event) {
        for (final Entity cEntity : event.getChunk().getEntities()) {
            if (cEntity.getType() != EntityType.PLAYER) {
                event.setCancelled(true);
                break;
            }
        }
    }
    
    @EventHandler
    public void slime(final SlimeSplitEvent e) {
        e.setCancelled(true);
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
                    Bosslistener.CreateEffect(loc1);
                    if (this.current.distanceSquared(targetVector) < 4.0) {
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
    
    @SuppressWarnings("rawtypes")
	public static void CreateEffect1(final Location loc) {
        final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float)(loc.getX() + 0.0), (float)(loc.getY() + 1.0), (float)(loc.getZ() + 0.0), 0.0f, 0.0f, 0.0f, 0.0f, 10, new int[0]);
        for (final Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
        }
    }
    
    public static void effect1(final Location loc, final CraftEntity bukkitEntity) {
        final BukkitRunnable runable = new BukkitRunnable() {
            Vector current = loc.toVector();
            World world = bukkitEntity.getWorld();
            int timer = 100;
            
            public void run() {
                if (this.timer-- >= 0) {
                    final Vector targetVector = bukkitEntity.getLocation().toVector();
                    final Vector tempvector = this.current.clone();
                    tempvector.subtract(bukkitEntity.getLocation().toVector()).normalize();
                    final Vector diff = this.current.subtract(tempvector);
                    final Location loc1 = diff.toLocation(this.world);
                    Bosslistener.CreateEffect1(loc1);
                    if (this.current.distanceSquared(targetVector) < 4.0) {
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
}
