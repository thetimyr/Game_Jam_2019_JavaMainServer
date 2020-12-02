package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.projectiles.*;
import org.bukkit.util.Vector;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;
import SkyCrafting.Main.Entyti.quest.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class EntityChubaka1 extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    HashMap<String, Integer> attackers;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityChubaka1(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.attackers = new HashMap<String, Integer>();
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(2500.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(130.0);
        this.setHealth(2500.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 100.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)12));
        this.setEquipment(4, testSword);
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.IRON_CHESTPLATE));
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.IRON_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.IRON_BOOTS));
        this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.COAL));
        this.setCustomName(ChatColor.RED + "\u0427\u0443\u0432\u0438 " + (int)this.getHealth() + "\u2764 ");
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
        this.setCustomName(ChatColor.RED + "\u0427\u0443\u0432\u0438 " + (int)this.getHealth() + "\u2764 ");
        return super.damageEntity(damagesource, f);
    }
    
    @SuppressWarnings("rawtypes")
	public void shot() {
        Vector velocity = null;
        Arrow snowball = null;
        final LivingEntity entityTarget = Main.instance.rayTraceEntity(this.bukkitEntity, 25);
        if (entityTarget == null) {
            return;
        }
        entityTarget.damage(5.0);
        final Vector v = entityTarget.getLocation().getDirection();
        v.setY(0).multiply(-0.3);
        entityTarget.setVelocity(v);
        entityTarget.setFireTicks(3);
        snowball = (Arrow)((ProjectileSource)this.bukkitEntity).launchProjectile((Class)Arrow.class);
        velocity = this.bukkitEntity.getLocation().getDirection();
        velocity.add(new Vector(Math.random() * this.accuracy - this.accuracy, Math.random() * this.accuracy - this.accuracy, Math.random() * this.accuracy - this.accuracy));
        snowball.setVelocity(velocity);
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.BURP, 2.0f, 1.0f);
    }
    
    public void m() {
        if (this.hp_delay <= 0) {
            this.setCustomName(ChatColor.RED + "\u0427\u0443\u0432\u0438 " + (int)this.getHealth() + "\u2764 ");
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
        if (this.killer != null) {
            final DropBoss drop = new DropBoss((org.bukkit.entity.Entity)this.bukkitEntity);
            drop.getlist().clear();
            final ItemStack t = new ItemStack(Material.RECORD_3);
            final ItemMeta meta = t.getItemMeta();
            meta.setDisplayName("§7§l\u0422\u0451\u043c\u043d\u044b\u0439 \u043a\u0440\u0438\u0441\u0442\u0430\u043b");
            t.setItemMeta(meta);
            drop.add(t);
            drop.add(new ItemStack(Material.COOKED_BEEF, 15));
            drop.add(new ItemStack(Material.NETHER_BRICK_ITEM));
            drop.getlist().addAll(Bosslistener.List(3));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 7000 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "\u041d\u0430 \u0432\u0430\u0448 \u0441\u0447\u0435\u0442 \u0431\u044b\u043b\u043e \u0437\u0430\u0447\u0438\u0441\u043b\u0435\u043d\u043d\u043e " + "10 \u0443\u0431\u0438\u0439\u0441\u0442\u0432 \u0438 25 опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 10);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 25);
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    JediScoreBoard.UpdateList(p);
                    JediScoreBoard.updateScoreboard(p);
                    }
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 10, 25);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&4\u0427\u0443\u0432\u0438 &c\u0431\u044b\u043b \u043f\u043e\u0432\u0435\u0440\u0436\u0435\u043d, \u043d\u0430\u043f\u0430\u0434\u0430\u044e\u0449\u0438\u0435 \u043f\u043e\u043b\u0443\u0447\u0430\u044e\u0442 \u043f\u043e " + q + "$!"));
        }
        super.die();
    }
}
