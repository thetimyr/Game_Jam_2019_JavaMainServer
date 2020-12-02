package SkyCrafting.Main.Entyti;

import java.util.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;


import org.bukkit.event.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import SkyCrafting.Main.*;
import SkyCrafting.Main.Entyti.quest.Bosslistener;

public class EntityVoin extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityVoin(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(75.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10.0);
        this.setHealth(75.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        this.spawner = spawner;
        this.fireProof = true;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.setEquipment(1, new ItemStack((Item)Items.LEATHER_HELMET));
        this.setEquipment(2, new ItemStack((Item)Items.CHAINMAIL_CHESTPLATE));
        this.setEquipment(3, new ItemStack((Item)Items.LEATHER_LEGGINGS));
        this.setEquipment(4, new ItemStack((Item)Items.LEATHER_BOOTS));
        this.setEquipment(0, new ItemStack(Items.SLIME));
        this.setCustomName(ChatColor.RED + "Джава 2 lvl " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "Джава 2 lvl " + (int)this.getHealth() + "❤ ");
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Джава 2 lvl " + (int)this.getHealth() + "❤ ");
            if (this.getHealth() < 500.0f) {
                this.heal(2.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
            this.hp_delay = 15;
        }
        super.m();
        if (this.getGoalTarget() != null && this.passenger != null) {
            final double distance = this.getBukkitEntity().getLocation().distance(this.getGoalTarget().getBukkitEntity().getLocation());
            if (distance < 9.0) {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
            }
            else {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.01);
            }
        }
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
        final org.bukkit.inventory.ItemStack a2 = new org.bukkit.inventory.ItemStack(Material.COOKED_BEEF, 15);
        final org.bukkit.inventory.ItemStack a3 = new org.bukkit.inventory.ItemStack(Material.CHAINMAIL_CHESTPLATE);
        final org.bukkit.inventory.ItemStack a4 = new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS);
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a2);
        }
        if (this.rand.nextFloat() <= 0.05) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a3);
        }
        if (this.rand.nextFloat() <= 0.05) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a4);
        }
        this.droped(0.02, Material.SEEDS);
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 50.0);
            Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 0);
            Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 2);
            p.sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно 50$, 2 опыта");
            
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, 50, 1, 2);
            //  final Player p = (Player)this.killer.getBukkitEntity();
        }
        super.die();
    }
}