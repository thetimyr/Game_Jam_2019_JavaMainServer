package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.util.Vector;

 

import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;
import java.lang.reflect.*;

public class EntitySlimeCustom extends EntityMonster
{
    Spawner spawner;
    HashMap<String, Integer> attackers;
    int totalDamage;
    int hp_delay;
    int debuff;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntitySlimeCustom(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.hp_delay = 5;
        this.debuff = 80;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(700.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(3.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(20.0);
        final List<?> goalB = (List<?>)getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
        goalB.clear();
        final List<?> goalC = (List<?>)getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
        goalC.clear();
        final List<?> targetB = (List<?>)getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
        targetB.clear();
        final List<?> targetC = (List<?>)getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
        targetC.clear();
        this.setHealth(500.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, true));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        this.setCustomName(ChatColor.RED + "Слизень " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.setSize(9);
        this.spawner = spawner;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
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
        this.getBukkitEntity().setVelocity(new Vector(0.0, 0.3, 0.0).multiply(1.9));
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.SLIME_WALK2, 1.0f, 1.0f);
        for (org.bukkit.entity.Entity entity : getBukkitEntity().getNearbyEntities(7.0D, 4.0D, 7.0D)) {
        if (entity.getType() == EntityType.PLAYER) {
        Player p = (Player)entity;
        if (this.rand.nextFloat() <= 0.05D) {
        p.setVelocity(p.getVelocity().add(p.getLocation().getDirection()).multiply(-3));          
        EntityTypes.spawnEntity(new EntitySlimeTEMP(getBukkitEntity().getWorld()), getBukkitEntity().getLocation());
        EntityTypes.spawnEntity(new EntitySlimeTEMP(getBukkitEntity().getWorld()), getBukkitEntity().getLocation());
        EntityTypes.spawnEntity(new EntitySlimeTEMP(getBukkitEntity().getWorld()), getBukkitEntity().getLocation());
        }
        }
        }
    }
    @SuppressWarnings("unlikely-arg-type")
	public boolean damageEntity(final DamageSource source, final float a) {
        if (source.getEntity() == this.passenger || source == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "Слизень " + (int)this.getHealth() + "❤ ");
        final Entity entity = source.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            if (new Random().nextFloat() <= 0.1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
            }
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)a);
            }
        }
        return super.damageEntity(source, a);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Слизень " + (int)this.getHealth() + "❤ ");
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
            drop.add(new ItemStack(Material.PAPER));
            drop.add(new ItemStack(Material.RECORD_5));
            drop.add(new ItemStack(Material.IRON_INGOT,20));
            drop.add(new ItemStack(Material.FIREWORK_CHARGE,4));
            drop.getlist().addAll(Bosslistener.List(2));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 5300 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "11 убийств 10 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 11);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 10);
 
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 11, 10);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&4Слизень &cбыл повержен, нападающие получают по " + q + "$!"));
        }
        super.die();
    }
    
    protected float bB() {
        return 0.4f * this.getSize();
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
