/*     */ package SkyCrafting.Main.Entyti;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import SkyCrafting.Main.Levels;
/*     */ import SkyCrafting.Main.Main;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.minecraft.server.v1_8_R3.DamageSource;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.EntityZombie;
/*     */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*     */ import net.minecraft.server.v1_8_R3.Items;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;

/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityVuki
/*     */   extends EntityZombie
/*     */ {
/*     */   Spawner spawner;
/*  42 */   int delay = 100;
/*  43 */   int hp_delay = 10;
/*  44 */   int debuff = 80;
/*  45 */   @SuppressWarnings("rawtypes")
HashMap<String, Integer> attackers = new HashMap();
/*  46 */   int shot_delay = 65;
/*  47 */   float accuracy = 0.1F;
/*  48 */   Random rand = new Random();
/*     */   
/*     */   @SuppressWarnings("rawtypes")
public EntityVuki(Spawner spawner)
/*     */   {
/*  52 */     super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
/*  53 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(135.0D);
/*  54 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/*  55 */     getAttributeInstance(GenericAttributes.c).setValue(5.0D);
/*  56 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.09D);
/*  57 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(15.0D);
/*  58 */     setHealth(135.0F);
/*     */     
/*  60 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 10.0D));
/*  61 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  62 */     this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 10, true, false, null));
/*  63 */     this.targetSelector.a(5, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  64 */     this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 64.0F));
/*  65 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 5.0D, true));
/*  66 */     this.spawner = spawner;
/*  67 */     this.fireProof = true;
/*  68 */     if (this.spawner != null) {
/*  69 */       this.spawner.register(this);
/*     */     }
/*  71 */     setBaby(true);
/*  72 */     setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack(Items.LEATHER_HELMET));
/*  73 */     setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack(Items.LEATHER_CHESTPLATE));
/*  74 */     setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack(Items.LEATHER_LEGGINGS));
/*  75 */     setEquipment(4, new net.minecraft.server.v1_8_R3.ItemStack(Items.LEATHER_BOOTS));
/*  76 */     setEquipment(0, new net.minecraft.server.v1_8_R3.ItemStack(Items.STICK));
/*  77 */     setCustomName(ChatColor.AQUA + "Эвок " + (int)getHealth() + "❤ ");
/*  78 */     setCustomNameVisible(true);
/*  79 */     this.canPickUpLoot = false;
/*  80 */     this.fireProof = true;
/*  81 */     this.persistent = true;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  85 */     if ((damagesource.getEntity() == this) || (damagesource == DamageSource.STUCK)) {
/*  86 */       return false;
/*     */     }
/*  88 */     setCustomName(ChatColor.AQUA + "Эвок " + (int)getHealth() + "❤ ");
/*  89 */     Entity entity = damagesource.i();
/*  90 */     if ((entity != null) && (entity.getBukkitEntity().getType() == EntityType.PLAYER)) {
/*  91 */       Player p = (Player)entity.getBukkitEntity();
/*  92 */       if (new Random().nextFloat() <= 0.1D) {
/*  93 */         p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
/*     */       }
/*     */     }
/*  96 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   public void m() {
/* 100 */     if (this.hp_delay-- <= 0) {
/* 101 */       setCustomName(ChatColor.AQUA + "Эвок " + (int)getHealth() + "❤ ");
/* 102 */       if (getHealth() < 135.0F) {
/* 103 */         heal(2.0F, RegainReason.REGEN);
/*     */       }
/* 105 */       this.hp_delay = 15;
/*     */     }
/* 107 */     super.m();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void die()
/*     */   {
/* 120 */     if (this.spawner != null) {
/* 121 */       this.spawner.iDead();
/*     */     }
/* 123 */     org.bukkit.inventory.ItemStack a1 = new org.bukkit.inventory.ItemStack(Material.FLINT);
/* 124 */     if (this.rand.nextFloat() <= 0.1D) {
/* 125 */       this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a1);
/*     */     }
/* 127 */     org.bukkit.inventory.ItemStack a5 = new org.bukkit.inventory.ItemStack(Material.SLIME_BALL);
/* 128 */     if (this.rand.nextFloat() <= 0.1D) {
/* 129 */       this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), a5);
/*     */     }
/* 131 */     if ((this.killer instanceof EntityPlayer)) {
/* 132 */       Player p = (Player)this.killer.getBukkitEntity();
/* 133 */       Main.econ.depositPlayer(p, 150.0D);
/* 134 */       Levels.kills.put(this.killer.getName(), Integer.valueOf(((Integer)Levels.kills.get(this.killer.getName())).intValue() + 1));
//           final Player p = (Player)this.killer.getBukkitEntity();
/* 135 */       p.sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно 150$");
/*     */     }
/* 137 */     super.die();
/*     */   }
/*     */ }