/*     */ package SkyCrafting.Main.Entyti;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import SkyCrafting.Main.Entyti.quest.Bosslistener;
/*     */ import SkyCrafting.Main.Entyti.quest.DropBoss;
import SkyCrafting.Main.JediScoreBoard;
/*     */ import SkyCrafting.Main.Levels;
/*     */ import SkyCrafting.Main.Main;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.minecraft.server.v1_8_R3.DamageSource;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityZombie;
/*     */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*     */ import net.minecraft.server.v1_8_R3.Items;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.Vector;

/*     */ public class EntityYoda
/*     */   extends EntityZombie
/*     */ {
/*     */   Spawner spawner;
/*  47 */   int delay = 100;
/*  48 */   int hp_delay = 10;
/*  49 */   int debuff = 80;
@SuppressWarnings("rawtypes")
/*  50 */   HashMap<String, Integer> attackers = new HashMap();
/*  51 */   int shot_delay = 65;
/*  52 */   float accuracy = 0.1F;
/*  53 */   Random rand = new Random();
/*     */   
/*     */   @SuppressWarnings("rawtypes")
public EntityYoda(Spawner spawner)
/*     */   {
/*  57 */     super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
/*  58 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(600.0D);
/*  59 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/*  60 */     getAttributeInstance(GenericAttributes.c).setValue(5.0D);
/*  61 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.05D);
/*  62 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(15.0D);
/*  63 */     setHealth(600.0F);
/*     */     
/*  65 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 10.0D));
/*  66 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  67 */     this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 10, true, false, null));
/*  68 */     this.targetSelector.a(5, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  69 */     this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 64.0F));
/*  70 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 5.0D, true));
/*  71 */     this.spawner = spawner;
/*  72 */     this.fireProof = true;
/*  73 */     if (this.spawner != null) {
/*  74 */       this.spawner.register(this);
/*     */     }
/*  76 */     //setBaby(true);
               final net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new ItemStack(Material.SAPLING, 1, (short)4));
               this.setEquipment(4, testSword);
/*  81 */     setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_SWORD));
              setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_HELMET));
              setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_CHESTPLATE));
              setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_LEGGINGS));
              setEquipment(4, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_BOOTS));
/*  82 */     setCustomName(ChatColor.RED + "§bЙода " + (int)getHealth() + "❤ ");
/*  83 */     setCustomNameVisible(true);
/*  84 */     this.canPickUpLoot = false;
/*  85 */     this.fireProof = true;
/*  86 */     this.persistent = true;
/*     */   }
/*     */   
/*     */   @SuppressWarnings("unlikely-arg-type")
            public boolean damageEntity(DamageSource damagesource, float f) {
/*  90 */     if ((damagesource.getEntity() == this) || (damagesource == DamageSource.STUCK)) {
/*  91 */       return false;
/*     */     }
/*  93 */     setCustomName(ChatColor.RED + "§bЙода " + (int)getHealth() + "❤ ");
/*  94 */     net.minecraft.server.v1_8_R3.Entity entity = damagesource.i();
/*  95 */     if ((entity != null) && (entity.getBukkitEntity().getType() == EntityType.PLAYER)) {
/*  96 */       Player p = (Player)entity.getBukkitEntity();
/*  97 */       if (new Random().nextFloat() <= 0.1D) {
/*  98 */         p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
/*     */       }
/* 100 */       if (!this.attackers.containsKey(p)) {
/* 101 */         this.attackers.put(p.getName(), Integer.valueOf((int)f));
/*     */       }
/*     */     }
/* 104 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   public void superJump()
/*     */   {
/* 109 */     if (this.rand.nextFloat() <= 0.1D) {
/* 110 */       this.attackers.clear();
/* 111 */       for (org.bukkit.entity.Entity entity : getBukkitEntity().getNearbyEntities(7.0D, 5.0D, 7.0D))
/* 112 */         if ((entity instanceof LivingEntity)) {
/* 113 */           if ((entity instanceof Villager)) return;
/* 114 */           LivingEntity target = (LivingEntity)entity;
/* 115 */           Vector p = getBukkitEntity().getLocation().toVector();
/* 116 */           Vector e1 = target.getLocation().toVector();
/* 117 */           Vector v = e1.subtract(p).normalize().multiply(1.5D);
/* 118 */           target.damage(5.0D);
/*     */           
/* 120 */           v.setY(v.getY() + 1.5D);
/*     */           
/* 122 */           if (v.getY() > 2.0D) {
/* 123 */             v.setY(2);
/*     */           }
/* 125 */           target.setVelocity(v);
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public void m() {
/* 131 */     if (this.hp_delay-- <= 0) {
/* 132 */       setCustomName(ChatColor.RED + "§bЙода " + (int)getHealth() + "❤ ");
/* 133 */       if (getHealth() < 600.0F) {
/* 134 */         heal(0.0F, RegainReason.REGEN);
/*     */       }
/* 136 */       this.hp_delay = 15;
/*     */     }
/* 138 */     this.debuff -= 1;
/* 139 */     if (this.debuff <= 0) {
/* 140 */       this.debuff = 35;
/* 141 */       superJump();
/*     */     }
/* 143 */     super.m();
/*     */   }
/*     */   public void droped(double q, Material q1)
/*     */   {
/* 156 */     if (this.rand.nextFloat() <= q) {
/* 157 */       this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new org.bukkit.inventory.ItemStack(q1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void die()
/*     */   {
/* 163 */     if (this.spawner != null) {
/* 164 */       this.spawner.iDead();
/*     */     }
/* 166 */     if (this.killer != null)
/*     */     {
/* 202 */       DropBoss drop = new DropBoss(this.bukkitEntity);
/* 205 */       drop.add(new org.bukkit.inventory.ItemStack(Material.PUMPKIN_SEEDS));
				drop.add(new org.bukkit.inventory.ItemStack(Material.IRON_INGOT,5));
                drop.getlist().addAll(Bosslistener.ListIron(2));
/* 207 */       drop.dropedBoss();
/* 208 */       int q = 0;
/* 209 */       for (String key : this.attackers.keySet()) {
/* 210 */         int money = 1000 / this.attackers.size();
/* 211 */         Main.econ.depositPlayer(key, money);
/* 212 */         if (Bukkit.getPlayer(key) != null) {
/* 213 */           q = money;
                   
/* 214 */           Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "3 убийств, 4 опыта и " + ChatColor.GREEN + money + "$");
                    {
    // опыт
                  Levels.xp.put(this.killer.getName(), Integer.valueOf(((Integer)Levels.xp.get(this.killer.getName())).intValue() + 4));
    // Киллы
                  Levels.kills.put(this.killer.getName(), Integer.valueOf(((Integer)Levels.kills.get(this.killer.getName())).intValue() + 3));
                    }

                   } }
/* 217 */       Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 3, 4);
/* 218 */       Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&bЙода &cбыла повержена, нападающие получают по " + q + "$!"));
/*     */         for (final Player p : Bukkit.getOnlinePlayers()) {
                  JediScoreBoard.UpdateList(p);
                  JediScoreBoard.updateScoreboard(p);
                  }
}
/* 220 */     super.die();
/*     */   }
/*     */ }