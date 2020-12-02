package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import com.sk89q.worldguard.protection.regions.*;

 

import com.sk89q.worldguard.protection.managers.*;
import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntytiDronLOH extends EntityMonster
{
    Spawner spawner;
    HashMap<String, Integer> attackers;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    EntityTypes r;
    Main main;
    String NameBoss = "Главарь точки ";//ТУТ КАРОЧ НАЗВАНИЕ БОССА (В конце обязательно пробел, иначе буде слипание ник и текста)
    
    
    @SuppressWarnings("rawtypes")
	public EntytiDronLOH(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(800.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.02);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(0.9);
        this.setHealth(800.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 9.0, true));
        this.spawner = spawner;
        this.fireProof = true;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_CHESTPLATE));
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_BOOTS));
        //this.setEquipment(0, new ItemStack(Blocks.BARRIER));
        this.setCustomName(ChatColor.RED + NameBoss  + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        final Entity entity = damagesource.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 1));
            this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.ZOMBIE_PIG_ANGRY, 1.0f, 1.0f);
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)f);
            }
        }
        this.setCustomName(ChatColor.RED + NameBoss + (int)this.getHealth() + "❤ ");
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + NameBoss + (int)this.getHealth() + "❤ ");
            if (this.getHealth() < 500.0f) {
                this.heal(2.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
            this.hp_delay = 15;
        }
        super.m();
        if (this.getGoalTarget() != null && this.passenger != null) {
            final double distance = this.getBukkitEntity().getLocation().distance(this.getGoalTarget().getBukkitEntity().getLocation());
            if (distance < 2.0) {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
            }
            else {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.01);
            }
        }
    }
    
    public boolean CheckregionPlaeyr(final Player p, final String a, final long q) {
        final RegionManager manager = this.main.wg.getRegionManager(p.getLocation().getWorld());
        for (final ProtectedRegion region : manager.getApplicableRegions(p.getLocation())) {
            if (region.getId().equalsIgnoreCase(a)) {
                p.sendMessage(String.valueOf(q) + " ");
                return true;
            }
        }
        return false;
    }
    public void droped(final double q, final Material q1) {
        if (this.rand.nextFloat() <= q) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new org.bukkit.inventory.ItemStack(q1));
        }
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        this.droped(0.09, Material.DIAMOND_LEGGINGS);
        this.droped(0.05, Material.IRON_BOOTS);
        this.droped(0.08, Material.DIAMOND_HELMET);
        this.droped(0.07, Material.IRON_CHESTPLATE);
        this.droped(1, Material.MELON_SEEDS);
        this.droped(1, Material.PUMPKIN_SEEDS);
        this.droped(1, Material.GOLD_NUGGET);
        if (this.killer != null) {
        	String Killerast = "NULL";
            final DropBoss drop = new DropBoss((org.bukkit.entity.Entity)this.bukkitEntity);
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_INGOT,20));
            drop.add(new org.bukkit.inventory.ItemStack(Material.PAPER));
            drop.getlist().addAll(Bosslistener.ListIron(2));
            drop.dropedBoss();
                 final Player p2 = (Player)this.killer.getBukkitEntity(); 
                 
				if(Levels.getFaction(p2).equalsIgnoreCase("Jedi"))
                	{
					  Killerast = "§3Джедаи§d";
                    	   
                       for (final Player p3 : Bukkit.getOnlinePlayers())
                       		{
	                           if (Levels.getFaction(p3).equalsIgnoreCase("Jedi")) 
	                           	{
	                               Main.econ.depositPlayer(p3.getName(),200);
	                               JediScoreBoard.UpdateList(p3);
	                               JediScoreBoard.updateScoreboard(p3);
	                           	}
                       		}
                           
                    }else {
                    	
                    }
				
				if(Levels.getFaction(p2).equalsIgnoreCase("Sith"))
            	   {
					Killerast = "§cСитхи§d";					
					  for (final Player p3  : Bukkit.getOnlinePlayers())
                   		{
                           if (Levels.getFaction(p3).equalsIgnoreCase("Sith")) 
                           	{
                               Main.econ.depositPlayer(p3.getName(),200);
                               JediScoreBoard.UpdateList(p3);
                               JediScoreBoard.updateScoreboard(p3);
                           	}
                   		}
                       
                }else {
                	
                }
				
				if(Levels.getFaction(p2).equalsIgnoreCase("Ali"))
         	   {
					Killerast = "§7Альянсеры§d";					
					  for (final Player p3  : Bukkit.getOnlinePlayers())
                		{
                        if (Levels.getFaction(p3).equalsIgnoreCase("Ali")) 
                        	{
                            Main.econ.depositPlayer(p3.getName(),200);
                            JediScoreBoard.UpdateList(p3);
                            JediScoreBoard.updateScoreboard(p3);
                        	}
                		}
                    
             }else {
             	
             }
			
			
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 9);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 9);
			Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', NameBoss + "&dбыл повержен игроком " + p2.getName() +"&d, все " + Killerast + " получают по 200$"));
			Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, 200, 9, 9);
        }
        
        super.die();
    }
}