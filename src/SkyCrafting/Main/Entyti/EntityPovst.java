package SkyCrafting.Main.Entyti;

import java.util.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;

 

import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.projectiles.*;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;
import SkyCrafting.Main.Entyti.quest.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityPovst extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityPovst(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(50.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10.0);
        this.setHealth(50.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 100.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)9));
        final net.minecraft.server.v1_8_R3.ItemStack testSword2 = CraftItemStack.asNMSCopy(new ItemStack(Material.SAPLING, 1, (short)2));
        final net.minecraft.server.v1_8_R3.ItemStack testSword3 = CraftItemStack.asNMSCopy(new ItemStack(Material.GOLD_SPADE, 1, (short)0));
        if (this.rand.nextFloat() <= 0.5) {
            this.setEquipment(4, testSword2);
            this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_HOE));
        }
        else {
            this.setEquipment(4, testSword);
            this.setEquipment(0, testSword3);
        }
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.CHAINMAIL_BOOTS));
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.CHAINMAIL_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.CHAINMAIL_CHESTPLATE));
        this.setCustomName(ChatColor.RED + "\u041f\u043e\u0432\u0441\u0442\u0430\u043d\u0435\u0446 " + (int)this.getHealth() + "\u2764 ");
        this.setCustomNameVisible(true);
        this.spawner = spawner;
        this.fireProof = true;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.canPickUpLoot = false;
        this.bukkitEntity = this.getBukkitEntity();
        this.fireProof = true;
        this.persistent = true;
        this.expToDrop = 0;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "\u041f\u043e\u0432\u0441\u0442\u0430\u043d\u0435\u0446 " + (int)this.getHealth() + "\u2764 ");
        return super.damageEntity(damagesource, f);
    }
    
    @SuppressWarnings("rawtypes")
	public void shot() {
        Vector velocity = null;
        Snowball snowball = null;
        final LivingEntity entityTarget = Main.instance.rayTraceEntity(this.bukkitEntity, 25);
        if (entityTarget == null) {
            return;
        }
        entityTarget.damage(3.0);
        final Vector v = entityTarget.getLocation().getDirection();
        v.setY(0).multiply(-0.3);
        entityTarget.setVelocity(v);
        snowball = (Snowball)((ProjectileSource)this.bukkitEntity).launchProjectile((Class)Snowball.class);
        velocity = this.bukkitEntity.getLocation().getDirection();
        velocity.add(new Vector(Math.random() * this.accuracy - this.accuracy, Math.random() * this.accuracy - this.accuracy, Math.random() * this.accuracy - this.accuracy));
        snowball.setVelocity(velocity);
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.BURP, 2.0f, 1.0f);
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.RED + "\u041f\u043e\u0432\u0441\u0442\u0430\u043d\u0435\u0446 " + (int)this.getHealth() + "\u2764 ");
            this.hp_delay = 10;
        }
        --this.shot_delay;
        if (this.shot_delay <= 0) {
            this.shot_delay = 20;
            this.shot();
        }
        super.m();
        if (this.getGoalTarget() == null) {
            return;
        }
        if (this.getGoalTarget() != null && this.getGoalTarget().world != this.world) {
            this.setGoalTarget((EntityLiving)null);
            return;
        }
        final double distance = this.getBukkitEntity().getLocation().distance(this.getGoalTarget().getBukkitEntity().getLocation());
        if (this.getGoalTarget() != null && distance > 16.0) {
            this.setGoalTarget((EntityLiving)null);
            return;
        }
        if (distance < 7.0) {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
        }
        else {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        }
        if (!(this.getGoalTarget() instanceof EntityPlayer)) {
            this.setGoalTarget((EntityLiving)null);
        }
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        final ItemStack a1 = new ItemStack(Material.FLINT);
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a1);
        }
        Bosslistener.droped(0.1, Material.COOKED_BEEF, this.bukkitEntity, 5);
        Bosslistener.droped(0.1, Material.GOLD_HELMET, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_CHESTPLATE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_LEGGINGS, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_BOOTS, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.DIAMOND_AXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.IRON_AXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.GOLD_PICKAXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.DIAMOND_PICKAXE, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.SEEDS, this.bukkitEntity, 1);
        Bosslistener.droped(0.1, Material.STICK, this.bukkitEntity, 1);
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 100.0);
            Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 1);
            //  final Player p = (Player)this.killer.getBukkitEntity();
 
            p.sendMessage(ChatColor.GREEN + "\u041d\u0430 \u0432\u0430\u0448 \u0441\u0447\u0435\u0442 \u0431\u044b\u043b\u043e \u0437\u0430\u0447\u0438\u0441\u043b\u0435\u043d\u043d\u043e 100$");
        }
        super.die();
    }
}
