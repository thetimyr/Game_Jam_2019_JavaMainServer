package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import com.google.common.base.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import com.sk89q.worldguard.protection.regions.*;

 

import com.sk89q.worldguard.protection.managers.*;
import SkyCrafting.Main.Entyti.quest.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.Material;

import SkyCrafting.Main.*;

public class EntityVindy extends EntityMonster
{
    Spawner spawner;
    HashMap<String, Integer> attackers;
    int delay;
    int hp_delay;
    int shot_delay;
    float accuracy;
    Random rand;
    EntityTypes r;
    Main main;
    
    @SuppressWarnings("rawtypes")
	public EntityVindy(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.attackers = new HashMap<String, Integer>();
        this.delay = 100;
        this.hp_delay = 10;
        this.shot_delay = 65;
        this.accuracy = 0.1f;
        this.rand = new Random();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(600.0);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        this.getAttributeInstance(GenericAttributes.c).setValue(5.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.04);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(20.0);
        this.setHealth(600.0f);
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 10.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.targetSelector.a(4, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, 10, true, false, (Predicate)null));
        this.targetSelector.a(5, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.goalSelector.a(9, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, (Class)EntityHuman.class, 64.0f));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 9.0, true));
        this.spawner = spawner;
        this.fireProof = true;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_CHESTPLATE));
        this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_LEGGINGS));
        this.setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack((Item)Items.GOLDEN_BOOTS));
        this.setEquipment(0, new ItemStack(Items.SHEARS));
        this.setCustomName(ChatColor.RED + "Мейс Винду " + (int)this.getHealth() + "❤ ");
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() == this || damagesource == DamageSource.STUCK) {
            return false;
        }
        final Entity entity = damagesource.i();
        if (entity != null && entity.getBukkitEntity().getType() == EntityType.PLAYER) {
            final Player p = (Player)entity.getBukkitEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 300, 1));
            this.getBukkitEntity().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.ZOMBIE_PIG_ANGRY, 1.0f, 1.0f);
            if (!this.attackers.containsKey(p)) {
                this.attackers.put(p.getName(), (int)f);
            }
        }
        this.setCustomName(ChatColor.RED + "Мейс Винду " + (int)this.getHealth() + "❤ ");
        return super.damageEntity(damagesource, f);
    }
    
    public void m() {
        if (this.hp_delay-- <= 0) {
            this.setCustomName(ChatColor.RED + "Мейс Винду " + (int)this.getHealth() + "❤ ");
            if (this.getHealth() < 500.0f) {
                this.heal(2.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
            this.hp_delay = 15;
        }
        super.m();
    }
    
    public boolean CheckregionPlaeyr(final Player p, final String a, final long q) {
        final RegionManager manager = this.main.wg.getRegionManager(p.getLocation().getWorld());
        for (final ProtectedRegion region : manager.getApplicableRegions(p.getLocation())) {
            if (region.getId().equalsIgnoreCase(a)) {
                p.sendMessage(String.valueOf(q) + " ");
                return true;
            }
        }
        return false;
    }
    
    public void die() {
        if (this.spawner != null) {
            this.spawner.iDead();
        }
        if (this.killer != null) {
            final DropBoss drop = new DropBoss((org.bukkit.entity.Entity)this.bukkitEntity);
            drop.add(new org.bukkit.inventory.ItemStack(Material.FIREWORK_CHARGE,4));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_HELMET));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_CHESTPLATE));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_LEGGINGS));
            drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_BOOTS));
            drop.add(new org.bukkit.inventory.ItemStack(Material.GOLD_INGOT,20));
            drop.getlist().addAll(Bosslistener.List(2));
            drop.dropedBoss();
            int q = 0;
            for (final String key : this.attackers.keySet()) {
                final int money = 2400 / this.attackers.size();
                Main.econ.depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) == null) {
                    continue;
                }
                q = money;
                Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "12 убийств, 11 Опыта и " + ChatColor.GREEN + money + "$");
                Levels.kills.put(this.killer.getName(), Levels.kills.get(this.killer.getName()) + 5);
                Levels.xp.put(this.killer.getName(), Levels.xp.get(this.killer.getName()) + 7);
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    JediScoreBoard.UpdateList(p);
                    JediScoreBoard.updateScoreboard(p);
                    //  final Player p = (Player)this.killer.getBukkitEntity();
 
                    }
            }
            Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 12, 11);
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&5&lМейс Винду &cбыл повержен, нападающие получают по " + q + "$!"));
        }
        super.die();
    }
}