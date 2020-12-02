package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;

import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.potion.*;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityTemnuySlizen extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int debuff;
    HashMap<String, Integer> attackers;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityTemnuySlizen(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.debuff = 80;
        this.attackers = new HashMap<String, Integer>();
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(3500.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.07);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(120.0);
        this.setHealth(3500.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        this.spawner = spawner;
        this.fireProof = true;
        this.setSize(5);
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.setCustomName(ChatColor.RED + "Тёмный слизень " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    public void setSize(final int i) {
        this.datawatcher.a(16, (Object)new Byte((byte)i));
        this.a(new float[] { 0.6f * i, 0.6f * i });
        this.setPosition(this.locX, this.locY, this.locZ);
        this.setHealth(this.getMaxHealth());
        this.b_ = i;
    }
    
    public int getSize() {
        return this.datawatcher.getByte(16);
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "Тёмный слизень " + (int)this.getHealth() + "❤ ");
        final Entity entity = damagesource.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            if (new Random().nextFloat() <= 0.1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
            }
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)f);
            }
        }
        return super.damageEntity(damagesource, f);
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
                final Vector v = e1.subtract(p).normalize().multiply(5);
                target.damage(5.0);
                v.setY(v.getY() + 1.5);
                if (v.getY() > 2.0) {
                    v.setY(2);
                }
                target.setVelocity(v);
            }
        }
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Тёмный слизень " + (int)this.getHealth() + "❤ ");
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
            drop.add(new ItemStack(Material.IRON_INGOT,20));
            drop.getlist().addAll(Bosslistener.ListIron(2));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 3000 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "11 убийств, 50 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 11);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 50);
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 11, 50);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&4Тёмный слизень &cбыл повержен, нападающие получают по " + q + "$!"));
        }
        super.die();
    }
}
