package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;
import org.bukkit.util.Vector;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import SkyCrafting.Main.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.scheduler.*;
import java.util.*;
import org.bukkit.plugin.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;

import java.lang.reflect.*;

public class EntitySlimeTEMP extends EntityMonster
{
    int totalDamage;
    int hp_delay;
    int debuff;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntitySlimeTEMP(final World world) {
        super((net.minecraft.server.v1_8_R3.World)((CraftWorld)world).getHandle());
        this.hp_delay = 5;
        this.debuff = 80;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.03);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5.0);
        this.setHealth(100.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 15.0, true));
        this.setCustomName(ChatColor.RED + "Миньйон " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.setSize(3);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
        this.expToDrop = 0;
        this.bukkitEntity = this.getBukkitEntity();
        this.totalDamage = 0;
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
    
    public void superJump() {
        this.getBukkitEntity().getWorld().playEffect(this.getBukkitEntity().getLocation(), Effect.SMALL_SMOKE, 5);
        this.getBukkitEntity().setVelocity(new Vector(0.0, 0.3, 0.0).multiply(1.5));
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.SLIME_WALK2, 1.0f, 1.0f);
    }
    
    public boolean damageEntity(final DamageSource source, final float a) {
        if (source.getEntity() == this.passenger || source == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "Миньйон " + (int)this.getHealth() + "❤ ");
        if (this.passenger != null) {
            return source != DamageSource.projectile((Entity)this, this.passenger) && this.passenger.damageEntity(source, a);
        }
        final Entity entity = source.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            if (new Random().nextFloat() <= 0.1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
            }
        }
        return super.damageEntity(source, a);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Миньйон " + (int)this.getHealth() + "❤ ");
            this.hp_delay = 5;
        }
        --this.debuff;
        if (this.debuff <= 0) {
            this.debuff = 35;
            this.superJump();
        }
        super.m();
    }
    
    public void die() {
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 20.0);
            p.sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно 20$, за убийство миньйона");
            Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 1);
        }
        EntityTypes.spawnEntity((Entity)new EntitySlimeTEMP1(this.getBukkitEntity().getWorld()), this.getBukkitEntity().getLocation());
        EntityTypes.spawnEntity((Entity)new EntitySlimeTEMP1(this.getBukkitEntity().getWorld()), this.getBukkitEntity().getLocation());
        EntityTypes.spawnEntity((Entity)new EntitySlimeTEMP1(this.getBukkitEntity().getWorld()), this.getBukkitEntity().getLocation());
        super.die();
    }
    
    public void debuff() {
        new BukkitRunnable() {
            public void run() {
                for (final org.bukkit.entity.Entity entity : EntitySlimeTEMP.this.getBukkitEntity().getNearbyEntities(7.0, 5.0, 7.0)) {
                    if (entity.getType() != EntityType.PLAYER) {
                        continue;
                    }
                    final Player p = (Player)entity;
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 10));
                    p.damage(6.0);
                }
            }
        }.runTaskLater((Plugin)Main.instance, 25L);
    }
    
    public static Object getPrivateField(final String fieldName, final Class<PathfinderGoalSelector> clazz, final Object object) {
        Object o = null;
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return o;
    }
}
