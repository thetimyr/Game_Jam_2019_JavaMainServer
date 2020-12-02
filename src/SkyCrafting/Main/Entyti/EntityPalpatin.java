package SkyCrafting.Main.Entyti;

import java.util.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.event.entity.*;
import SkyCrafting.Main.Entyti.quest.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityPalpatin extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityPalpatin(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(150.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(10.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10.0);
        this.setHealth(150.0f);
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
        final ItemStack p = new ItemStack(Material.LEATHER_CHESTPLATE);
        final ItemMeta p2 = p.getItemMeta();
        final LeatherArmorMeta lam = (LeatherArmorMeta)p.getItemMeta();
        lam.setColor(Color.fromRGB(255, 0, 0));
        p.setItemMeta(p2);
        p.setItemMeta((ItemMeta)lam);
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(p);
        final net.minecraft.server.v1_8_R3.ItemStack testSword2 = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)4));
        final net.minecraft.server.v1_8_R3.ItemStack testSword3 = CraftItemStack.asNMSCopy(new ItemStack(Material.FIREBALL));
        this.setEquipment(1, testSword);
        this.setEquipment(2, testSword);
        this.setEquipment(3, testSword);
        this.setEquipment(4, testSword2);
        this.setEquipment(0, testSword3);
        this.setCustomName(ChatColor.YELLOW + "Охраник палпатина " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.YELLOW + "Охраник палпатина " + (int)this.getHealth() + "❤ ");
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.YELLOW + "Охраник палпатина " + (int)this.getHealth() + "❤ ");
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
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(q1));
        }
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        final ItemStack a2 = new ItemStack(Material.COOKED_BEEF, 15);
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a2);
        }
        this.droped(0.02, Material.SEEDS);
        Bosslistener.droped(0.1, Material.GRILLED_PORK, this.bukkitEntity, 20);
        Bosslistener.droped(0.1, Material.DIAMOND_AXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.IRON_AXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.IRON_SPADE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.STRING, this.bukkitEntity, 3);
        Bosslistener.droped(0.1, Material.STONE_HOE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_HOE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.DIAMOND_HOE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_AXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.IRON_PICKAXE, this.bukkitEntity, 1);
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 150.0);
            Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 1);
            p.sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно 150$");
            //  final Player p = (Player)this.killer.getBukkitEntity();
            
        }
        super.die();
    }
}
