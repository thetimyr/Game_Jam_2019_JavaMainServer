package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.util.Vector;

 
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityOxotnik extends EntityMonster
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
	public EntityOxotnik(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.delay = 100;
        this.debuff = 80;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(800.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(128.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(2.147483647E9);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.09);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(90.0);
        this.setHealth(800.0f);
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 128.0f));
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
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)2));
        this.setEquipment(4, testSword);
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_CHESTPLATE));
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.DIAMOND_BOOTS));
        this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.RECORD_MELLOHI));
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&1&lОхотник за головами " + (int)this.getHealth() + " ❤"));
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
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
                final Vector v = e1.subtract(p).normalize().multiply(1.5);
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
            if (new Random().nextFloat() <= 0.1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 5));
            }
        }
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&1&lОхотник за головами " + (int)this.getHealth() + " ❤"));
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&1&lОхотник за головами " + (int)this.getHealth() + " ❤"));
            if (this.getHealth() < 500.0f) {
                this.heal(2.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
            this.hp_delay = 10;
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
            drop.add(new ItemStack(Material.RECORD_7));
            drop.add(new ItemStack(Material.NETHER_BRICK_ITEM));
            drop.getlist().addAll(Bosslistener.List(3));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 7500 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "20 убийств,40 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 20);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 40);
 
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 20, 40);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&1&lОхотник за головами &cбыл повержен, нападающие получают по " + q + "$!"));
        }
        super.die();
    }
}
