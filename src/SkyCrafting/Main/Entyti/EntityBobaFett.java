package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

 

import org.bukkit.plugin.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityBobaFett extends EntityMonster
{
    Spawner spawner;
    HashMap<String, Integer> attackers;
    int delay;
    int debuff;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityBobaFett(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.delay = 100;
        this.debuff = 80;
        this.hp_delay = 20;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1500.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(4.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.095);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(40.0);
        this.setHealth(1500.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, true));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        this.spawner = spawner;
        this.fireProof = true;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)7));
        this.setEquipment(4, testSword);
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_CHESTPLATE));
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_BOOTS));
        this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.STONE_SHOVEL));
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lБоба Фетт " + (int)this.getHealth() + " ❤"));
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    public static void effect(final LivingEntity p) {
        final BukkitRunnable runable = new BukkitRunnable() {
            int timer = 70;
            int timer5 = 20;
            
            public void run() {
                if (this.timer-- >= 0) {
                    if (this.timer5-- < 0) {
                        p.setVelocity(new Vector(0.0, 0.01, 0.0));
                    }
                }
                else {
                    p.setVelocity(p.getVelocity().add(p.getLocation().getDirection()).multiply(-3));
                    this.cancel();
                }
            }
        };
        runable.runTaskTimer((Plugin)Main.instance, 0L, 1L);
    }
    
    public void superJump() {
        if (this.rand.nextFloat() <= 0.1) {
            for (final org.bukkit.entity.Entity entity : this.getBukkitEntity().getNearbyEntities(7.0, 5.0, 7.0)) {
                if (!(entity instanceof LivingEntity)) {
                    continue;
                }
                if (entity instanceof Villager) {
                    return;
                }
                final LivingEntity target = (LivingEntity)entity;
                final Vector p = this.getBukkitEntity().getLocation().toVector();
                final Vector e1 = target.getLocation().toVector();
                final Vector v = e1.subtract(p).normalize().multiply(3);
                target.damage(5.0);
                v.setY(v.getY() + 1.5);
                if (v.getY() > 2.0) {
                    v.setY(2);
                }
                target.setVelocity(v);
            }
        }
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        final Entity entity = damagesource.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)f);
            }
        }
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&lБоба Фетт " + (int)this.getHealth() + " ❤"));
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&lБоба Фетт " + (int)this.getHealth() + " ❤"));
            if (this.getHealth() < 500.0f) {
                this.heal(2.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
            this.hp_delay = 15;
        }
        --this.debuff;
        if (this.debuff <= 0) {
            this.debuff = 35;
            this.superJump();
        }
        super.m();
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        if (this.killer != null) {
            final DropBoss drop = new DropBoss((org.bukkit.entity.Entity)this.bukkitEntity);
            drop.add(new ItemStack(Material.SULPHUR));
            drop.add(new ItemStack(Material.GOLD_INGOT,2));
            drop.getlist().addAll(Bosslistener.List(3));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 6000 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "10 убийств,13 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 10);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 15);
 
 
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 10, 13);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&lБоба Фетт &cбыл повержен, нападающие получают по " + q + "$!"));
        }
        super.die();
    }
}
