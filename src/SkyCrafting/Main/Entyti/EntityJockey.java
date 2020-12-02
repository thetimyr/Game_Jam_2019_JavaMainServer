package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;

 

import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.util.Vector;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.projectiles.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;
import SkyCrafting.Main.Entyti.quest.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Item;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityJockey extends EntityMonster
{
    Spawner spawner;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    
    @SuppressWarnings("rawtypes")
	public EntityJockey(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0);
        this.setHealth(30.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 100.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 5.0, true));
        final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)11));
        final net.minecraft.server.v1_8_R3.ItemStack testSword2 = CraftItemStack.asNMSCopy(new ItemStack(Material.CARPET, 1, (short)6));
        if (this.rand.nextFloat() <= 0.5) {
            this.setEquipment(4, testSword2);
            this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_HOE));
        }
        else if (this.rand.nextFloat() <= 0.5) {
            this.setEquipment(4, testSword);
            this.setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_SHOVEL));
        }
        this.setCustomName(ChatColor.RED + "\u041a\u043b\u043e\u043d " + (int)this.getHealth() + "\u2764 ");
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
        this.setCustomName(ChatColor.RED + "\u041a\u043b\u043e\u043d " + (int)this.getHealth() + "\u2764 ");
        return super.damageEntity(damagesource, f);
    }
    
    private void shoot() {
        final Location loc1 = this.getBukkitEntity().getLocation();
        if (this.getGoalTarget() == null) {
            return;
        }
        final Location loc2 = this.getGoalTarget().getBukkitEntity().getLocation();
        if (loc1 == null || loc2 == null) {
            return;
        }
        loc1.add(0.0, 1.5, 0.0);
        loc2.add(0.0, 1.0, 0.0);
        if (loc1.getWorld().getName() != loc2.getWorld().getName()) {
            return;
        }
        final double dist = loc1.distance(loc2);
        Vector vector = new Vector(loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc1.getZ());
        vector = new Vector(vector.getX() / dist, vector.getY() / dist, vector.getZ() / dist);
        final Snowball fb = (Snowball)this.getBukkitEntity().getWorld().spawnEntity(loc1.add(vector), EntityType.SNOWBALL);
        fb.setMetadata("damage", (MetadataValue)new FixedMetadataValue((Plugin)Main.instance, (Object)5));
        fb.setMetadata("force", (MetadataValue)new FixedMetadataValue((Plugin)Main.instance, (Object)1));
        fb.getLocation().setDirection(vector);
        fb.setVelocity(vector);
        this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.BURP, 2.0f, 1.0f);
    }
    
    public void granade() {
        if (this.getGoalTarget() == null) {
            return;
        }
        if (this.getBukkitEntity().getLocation() == null || this.getGoalTarget().getBukkitEntity().getLocation() == null) {
            return;
        }
        if (!(this.getGoalTarget() instanceof LivingEntity)) {
            return;
        }
        Location loc2 = this.getGoalTarget().getBukkitEntity().getLocation();
        loc2 = this.getBukkitEntity().getLocation();
        if (!loc2.getWorld().getName().equals(loc2.getWorld().getName())) {
            return;
        }
        loc2.add(0.0, 1.5, 0.0);
        loc2.add(0.0, 1.0, 0.0);
        final double dist = loc2.distance(loc2);
        Vector vector = new Vector(loc2.getX() - loc2.getX(), loc2.getY() - loc2.getY(), loc2.getZ() - loc2.getZ());
        vector = new Vector(vector.getX() / dist, vector.getY() / dist, vector.getZ() / dist);
        final Item grenade = this.getBukkitEntity().getWorld().dropItem(new Location((org.bukkit.World)this.world.getWorld(), this.locX, this.locY + 0.5, this.locZ), this.getUniGrenadeItem());
        grenade.setItemStack(this.getUniGrenadeItem());
        grenade.setVelocity(vector);
        grenade.getLocation().setDirection(vector);
        Bukkit.getScheduler().runTaskLater((Plugin)Main.instance, (Runnable)new Runnable() {
            @Override
            public void run() {
                final Location loc = grenade.getLocation();
                grenade.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3.5f, false, false);
                grenade.remove();
            }
        }, 100L);
    }
    
    @SuppressWarnings("rawtypes")
	private ItemStack getUniGrenadeItem() {
        final ItemStack is = new ItemStack(Material.SLIME_BALL).clone();
        final ItemMeta im = is.getItemMeta();
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(UUID.randomUUID().toString());
        im.setLore((List)lore);
        im.setDisplayName("clone_grenade");
        is.setItemMeta(im);
        return is;
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
            this.setCustomName(ChatColor.RED + "\u041a\u043b\u043e\u043d " + (int)this.getHealth() + "\u2764 ");
            this.hp_delay = 10;
        }
        --this.shot_delay;
        if (this.shot_delay <= 0) {
            this.shot_delay = 20;
            this.shoot();
            this.granade();
        }
        if (new Random().nextFloat() > 0.8) {
            this.granade();
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
        Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, 100, 0, 1);
        final ItemStack a1 = new ItemStack(Material.FLINT, 1);
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a1);
        }
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(Material.INK_SACK, 5, (short)1));
        }
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(Material.INK_SACK, 1, (short)10));
        }
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(Material.INK_SACK, 1, (short)4));
        }
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(Material.INK_SACK, 1, (short)2));
        }
        if (this.rand.nextFloat() <= 0.1) {
            this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new ItemStack(Material.INK_SACK, 1, (short)3));
        }
        Bosslistener.droped(0.1, Material.STRING, this.bukkitEntity, 5);
        Bosslistener.droped(0.1, Material.SUGAR, this.bukkitEntity, 5);
        Bosslistener.droped(0.1, Material.IRON_AXE, this.bukkitEntity, 1);
        if (this.killer instanceof EntityPlayer) {
            final Player p = (Player)this.killer.getBukkitEntity();
            Main.econ.depositPlayer((OfflinePlayer)p, 20.0);
            Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 0);
            Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 1);
            p.sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно 20$ и 1 опыт");
            
            
 
                JediScoreBoard.UpdateList(p);
                JediScoreBoard.updateScoreboard(p);
                
        }
        super.die();
    }
}
