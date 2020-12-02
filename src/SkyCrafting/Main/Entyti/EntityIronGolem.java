package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.scheduler.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.util.Vector;

 
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import SkyCrafting.Main.Entyti.quest.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import SkyCrafting.Main.*;

public class EntityIronGolem extends EntityMonster
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
	public EntityIronGolem(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.delay = 100;
        this.debuff = 80;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1800.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(40.0);
        this.setHealth(1800.0f);
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
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&l\u0419\u0435\u0442\u0438 " + (int)this.getHealth() + " \u2764"));
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
                final Vector v = e1.subtract(p).normalize().multiply(3);
                target.damage(5.0);
                v.setY(v.getY() + 1.5);
                if (v.getY() > 2.0) {
                    v.setY(2);
                }
                target.setVelocity(v);
            }
        }
        else if (this.rand.nextFloat() <= 0.1) {
            for (final org.bukkit.entity.Entity entity : this.getBukkitEntity().getNearbyEntities(7.0, 5.0, 7.0)) {
                if (!(entity instanceof LivingEntity)) {
                    continue;
                }
                if (entity instanceof Villager) {
                    return;
                }
                final BukkitRunnable runable = new BukkitRunnable() {
                    int timer = 30;
                    
                    @SuppressWarnings("rawtypes")
					public void run() {
                        if (this.timer-- >= 0) {
                            Bosslistener.cust(entity);
                            final Location loc = entity.getLocation();
                            final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.DRIP_WATER, true, (float)(loc.getX() + 0.0), (float)(loc.getY() + 1.0), (float)(loc.getZ() + 0.0), 0.0f, 0.0f, 0.0f, 0.0f, 10, new int[0]);
                            for (final Player online : Bukkit.getOnlinePlayers()) {
                                ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)packet);
                            }
                        }
                        else {
                            this.cancel();
                        }
                    }
                };
                runable.runTaskTimer((Plugin)Main.instance, 0L, 5L);
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
        this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&l\u0419\u0435\u0442\u0438 " + (int)this.getHealth() + " \u2764"));
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&l\u0419\u0435\u0442\u0438 " + (int)this.getHealth() + " \u2764"));
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
            drop.add(new ItemStack(Material.RECORD_5));
            drop.add(new ItemStack(Material.GOLD_BARDING));
            drop.getlist().addAll(Bosslistener.List(3));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 8000 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "\u041d\u0430 \u0432\u0430\u0448 \u0441\u0447\u0435\u0442 \u0431\u044b\u043b\u043e \u0437\u0430\u0447\u0438\u0441\u043b\u0435\u043d\u043d\u043e " + "15 \u0443\u0431\u0438\u0439\u0441\u0442\u0432 \u0438 35 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 15);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 35);
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    JediScoreBoard.UpdateList(p);
                    JediScoreBoard.updateScoreboard(p);
                  //  final Player p = (Player)this.killer.getBukkitEntity();
 
                    
                    }
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 15, 35);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&f&l\u0419\u0435\u0442\u0438 &c\u0431\u044b\u043b \u043f\u043e\u0432\u0435\u0440\u0436\u0435\u043d, \u043d\u0430\u043f\u0430\u0434\u0430\u044e\u0449\u0438\u0435 \u043f\u043e\u043b\u0443\u0447\u0430\u044e\u0442 \u043f\u043e " + q + "$!"));
        }
        super.die();
    }
}
