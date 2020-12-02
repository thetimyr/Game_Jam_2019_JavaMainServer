package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;
import java.lang.reflect.*;

public class EntitySlimeJabba extends EntityMonster
{
    Spawner spawner;
    HashMap<String, Integer> attackers;
    int totalDamage;
    int hp_delay;
    int debuff;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntitySlimeJabba(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.hp_delay = 5;
        this.debuff = 80;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(500.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
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
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 0.0, true));
        this.setCustomName(ChatColor.RED + "Джабба " + (int)this.getHealth() + "❤ ");
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
        if (this.rand.nextFloat() <= 0.1) {
            this.attackers.clear();
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
	public boolean damageEntity(final DamageSource source, final float a) {
        if (source.getEntity() == this.passenger || source == DamageSource.STUCK) {
            return false;
        }
        this.setCustomName(ChatColor.RED + "Джабба " + (int)this.getHealth() + "❤ ");
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.SLIME_WALK2, 1.0f, 1.0f);
        final Entity entity = source.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            if (new Random().nextFloat() <= 0.1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
                p.damage(1.0);
            }
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)a);
            }
        }
        return super.damageEntity(source, a);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Джабба " + (int)this.getHealth() + "❤ ");
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
    
    public int getkill() {
        if (this.attackers.size() == 0) {
            return 0;
        }
        final int a = 5000 / this.attackers.size();
        return a;
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        if (this.killer != null) {
            final DropBoss drop = new DropBoss((org.bukkit.entity.Entity)this.bukkitEntity);
            drop.add(new ItemStack(Material.RECORD_5));
            drop.add(new ItemStack(Material.SLIME_BALL));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_HELMET));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_CHESTPLATE));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_BOOTS));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_INGOT,20));
            drop.add(new org.bukkit.inventory.ItemStack(Material.RECORD_3));
            drop.add(new org.bukkit.inventory.ItemStack(Material.RECORD_3));
            drop.getlist().addAll(Bosslistener.List(1));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 2000 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                {
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "10 убийств, 10 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 10);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 10);
     			if(Levels.getCrystal(Bukkit.getPlayer(key)) == 7) {
        			Levels.crystal.put(Bukkit.getPlayer(key).getName(), Levels.crystal.get(Bukkit.getPlayer(key).getName()) + 1);
        			Main.econ.depositPlayer(Bukkit.getPlayer(key), (double)12000);
        			Bukkit.getPlayer(key).getInventory().addItem(new ItemStack(Material.GOLD_INGOT,2));
        			Bukkit.getPlayer(key).sendMessage("§aВы успешно выполнили 8 квест!");
        			JediScoreBoard.UpdateList(Bukkit.getPlayer(key));
        			JediScoreBoard.updateScoreboard(Bukkit.getPlayer(key));
        			
    			}
                }
              }   
            
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 10, 10);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&4Джабба &cбыл повержен, нападающие получают по " + q + "$!"));
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
