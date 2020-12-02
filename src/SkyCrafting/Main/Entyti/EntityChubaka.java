package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;

 

import org.bukkit.plugin.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.*;
import SkyCrafting.Main.*;
import org.bukkit.scheduler.*;
import java.util.*;

public class EntityChubaka extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    
    @SuppressWarnings("rawtypes")
	public EntityChubaka(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
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
        this.setEquipment(4, new ItemStack(Items.ACACIA_DOOR));
        this.setEquipment(2, new ItemStack((Item)Items.IRON_CHESTPLATE));
        this.setEquipment(3, new ItemStack((Item)Items.IRON_LEGGINGS));
        this.setEquipment(1, new ItemStack((Item)Items.IRON_BOOTS));
        this.setEquipment(0, new ItemStack(Items.COAL));
        this.setCustomName(ChatColor.RED + "\u0427\u0443\u0431\u0430\u043a\u0430 " + (int)this.getHealth() + "\u2764 ");
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
        this.setCustomName(ChatColor.RED + "\u0427\u0443\u0431\u0430\u043a\u0430 " + (int)this.getHealth() + "\u2764 ");
        return super.damageEntity(damagesource, f);
    }
    
    public void shot(final EntityLiving entityliving, final float f) {
        final HashSet<EntityArrow> arrows = new HashSet<EntityArrow>();
        for (int a = 0; a < 1; ++a) {
            final EntityArrow entityarrow = new EntityArrow(this.world, (EntityLiving)this, entityliving, 1.6f, (float)(14 - this.world.getDifficulty().a() * 4));
            final int i = 3;
            final int j = 1;
            entityarrow.b(f * 2.0f + this.random.nextGaussian() * 0.75 + this.world.getDifficulty().a() * 0.11f);
            if (i > 0) {
                entityarrow.b(entityarrow.j() + i * 0.5 + 0.5);
            }
            if (j > 0) {
                entityarrow.setKnockbackStrength(j);
            }
            arrows.add(entityarrow);
            this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.BURP, 2.0f, 1.0f);
            this.world.addEntity((Entity)entityarrow);
        }
        new ArrowRemoverTask(arrows).runTaskLater((Plugin)Main.instance, 100L);
    }
    
    public void a(final EntityLiving entityliving, final float f) {
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.RED + "\u0427\u0443\u0431\u0430\u043a\u0430 " + (int)this.getHealth() + "\u2764 ");
            this.hp_delay = 10;
        }
        --this.hp_delay;
        super.m();
        if (this.getGoalTarget() == null) {
            return;
        }
        if (this.getGoalTarget() != null && this.getGoalTarget().world != this.world) {
            this.setGoalTarget((EntityLiving)null);
            return;
        }
        --this.shot_delay;
        if (this.shot_delay <= 0) {
            this.shot_delay = 15;
            this.shot(this.getGoalTarget(), 1.0f);
            final LivingEntity entityTarget = Main.instance.rayTraceEntity(this.bukkitEntity, 25);
            if (entityTarget == null) {
                return;
            }
            entityTarget.setFireTicks(15);
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
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(1.0);
        }
        if (!(this.getGoalTarget() instanceof EntityPlayer)) {
            this.setGoalTarget((EntityLiving)null);
        }
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 100.0);
            Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 1);
            p.sendMessage(ChatColor.GREEN + "\u041d\u0430 \u0432\u0430\u0448 \u0441\u0447\u0435\u0442 \u0431\u044b\u043b\u043e \u0437\u0430\u0447\u0438\u0441\u043b\u0435\u043d\u043d\u043e 100$");
                JediScoreBoard.UpdateList(p);
                JediScoreBoard.updateScoreboard(p);
               // final Player p = (Player)this.killer.getBukkitEntity();
 
                
        }
        super.die();
    }
    
    class ArrowRemoverTask extends BukkitRunnable
    {
        Collection<EntityArrow> arrows;
        
        public ArrowRemoverTask(final Collection<EntityArrow> arrows) {
            this.arrows = arrows;
        }
        
        public void run() {
            for (final EntityArrow cArrow : this.arrows) {
                cArrow.getBukkitEntity().remove();
            }
        }
    }
}
