/*     */ package SkyCrafting.Main.Entyti;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import SkyCrafting.Main.Entyti.quest.Bosslistener;
/*     */ import SkyCrafting.Main.Entyti.quest.DropBoss;
/*     */ import SkyCrafting.Main.Levels;
/*     */ import SkyCrafting.Main.Main;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.minecraft.server.v1_8_R3.DamageSource;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityZombie;
/*     */ import net.minecraft.server.v1_8_R3.EnumParticle;
/*     */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*     */ import net.minecraft.server.v1_8_R3.Items;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
/*     */ import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.EntityEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.util.Vector;
 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPolty
/*     */   extends EntityZombie
/*     */ {
/*     */   Spawner spawner;
/*  55 */   int delay = 100;
/*  56 */   int hp_delay = 10;
/*  57 */   int debuff = 80;
/*  58 */   @SuppressWarnings("rawtypes")
HashMap<String, Integer> attackers = new HashMap();
/*  59 */   int shot_delay = 65;
/*  60 */   float accuracy = 0.1F;
/*  61 */   Random rand = new Random();
/*     */   
/*     */   @SuppressWarnings("rawtypes")
public EntityPolty(Spawner spawner)
/*     */   {
/*  65 */     super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
/*  66 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(2048.0D);
/*  67 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/*  68 */     getAttributeInstance(GenericAttributes.c).setValue(5.0D);
/*  69 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
/*  70 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(170.0D);
/*  71 */     setHealth(2048.0F);
/*     */     
/*  73 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 10.0D));
/*  74 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  75 */     this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 10, true, false, null));
/*  76 */     this.targetSelector.a(5, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  77 */     this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 64.0F));
/*  78 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.5D, true));
/*  79 */     this.spawner = spawner;
/*  80 */     this.fireProof = true;
/*  81 */     if (this.spawner != null) {
/*  82 */       this.spawner.register(this);
/*     */     }
/*  84 */     net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.CARPET, 1, (short)13));
/*  85 */     setEquipment(1, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_HELMET));
/*  86 */     setEquipment(2, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_CHESTPLATE));
/*  87 */     setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack(Items.IRON_LEGGINGS));
//this.setEquipment(3, new net.minecraft.server.v1_8_R3.ItemStack(Items.BOAT);
/*  88 */     setEquipment(4, testSword);
/*     */     
/*  90 */     setCustomName(ChatColor.RED + "§5§lПалпатин " + (int)getHealth() + "❤ ");
/*  91 */     setCustomNameVisible(true);
/*  92 */     this.canPickUpLoot = false;
/*  93 */     this.fireProof = true;
/*  94 */     this.persistent = true;
/*     */   }
/*     */   
/*     */   @SuppressWarnings("unlikely-arg-type")
public boolean damageEntity(DamageSource damagesource, float f) {
/*  98 */     if ((damagesource.getEntity() == this) || (damagesource == DamageSource.STUCK)) {
/*  99 */       return false;
/*     */     }
/* 101 */     setCustomName(ChatColor.RED + "§5§lПалпатин " + (int)getHealth() + "❤ ");
/* 102 */     net.minecraft.server.v1_8_R3.Entity entity = damagesource.i();
/* 103 */     if ((entity != null) && (entity.getBukkitEntity().getType() == EntityType.PLAYER)) {
/* 104 */       Player p = (Player)entity.getBukkitEntity();
/* 105 */       if (new Random().nextFloat() <= 0.1D) {
/* 106 */         p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
/*     */       }
/* 108 */       if (!this.attackers.containsKey(p)) {
/* 109 */         this.attackers.put(p.getName(), Integer.valueOf((int)f));
/*     */       }
/*     */     }
/* 112 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   private void lightning(Location target) {
/* 116 */     target.getWorld().strikeLightningEffect(target);
/*     */   }
/*     */   
/*     */   public void superJump() {
/* 120 */     if (this.rand.nextFloat() <= 0.1D) {
/* 121 */       this.attackers.clear();
/* 122 */       for (org.bukkit.entity.Entity entity : getBukkitEntity().getNearbyEntities(7.0D, 5.0D, 7.0D)) {
/* 123 */         if ((entity instanceof CraftLivingEntity)) {
/* 124 */           if ((entity instanceof Villager)) return;
/* 125 */           CraftLivingEntity target = (CraftLivingEntity)entity;
/* 126 */           Vector p = getBukkitEntity().getLocation().toVector();
/* 127 */           Vector e1 = target.getLocation().toVector();
/* 128 */           Vector v = e1.subtract(p).normalize().multiply(1.5D);
/* 129 */           target.damage(5.0D);
/*     */           
/* 131 */           v.setY(v.getY() + 1.5D);
/*     */           
/* 133 */           if (v.getY() > 2.0D) {
/* 134 */             v.setY(2);
/*     */           }
/* 136 */           target.setVelocity(v);
/* 137 */           lightning(target.getLocation());
/* 138 */           target.damage(3.0D);
/*     */         }
/*     */       }
/* 141 */     } else if (this.rand.nextFloat() <= 0.1D) {
/* 142 */       for (final org.bukkit.entity.Entity p : getBukkitEntity().getNearbyEntities(7.0D, 5.0D, 7.0D))
/* 143 */         if ((p instanceof CraftLivingEntity)) {
/* 144 */           BukkitRunnable runable = new BukkitRunnable() {
/* 145 */             int timer = 70;
/* 146 */             int timer5 = 20;
/*     */             
/*     */             public void run() {
/* 149 */               EntityPolty.CreateEffect(p.getLocation());
/* 150 */               if (this.timer-- >= 0) {
/* 151 */                 if (this.timer5-- < 0) {
/* 152 */                   p.setVelocity(new Vector(0.0D, 0.0025D, 0.0D));
/*     */                 }
/*     */                 else {
/* 155 */                   p.setVelocity(new Vector(0.0D, 0.3D, 0.0D));
/*     */                 }
/*     */               }
/*     */               else {
/* 159 */                 cancel();
/*     */               }
/*     */             }
/* 162 */           };
/* 163 */           runable.runTaskTimer(Main.instance, 0L, 1L);
/*     */         }
/* 165 */     } else if (this.rand.nextFloat() <= 0.1D) {
/* 166 */       for (org.bukkit.entity.Entity p : getBukkitEntity().getNearbyEntities(7.0D, 5.0D, 7.0D))
/* 167 */         if ((p instanceof CraftLivingEntity)) {
/* 168 */           CraftLivingEntity p1 = (CraftLivingEntity)p;
/* 169 */           p1.playEffect(EntityEffect.HURT);
/* 170 */           Bosslistener.effect(this.bukkitEntity.getLocation(), p1);
/* 171 */           p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
/* 172 */           p1.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
/* 173 */           p1.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
/* 174 */           p1.damage(3.0D);
/*     */         }
/* 176 */     } else if (this.rand.nextFloat() <= 0.1D) {
/* 177 */       for (org.bukkit.entity.Entity p : getBukkitEntity().getNearbyEntities(7.0D, 5.0D, 7.0D))
/* 178 */         if ((p instanceof CraftLivingEntity)) {
/* 179 */           CraftLivingEntity p1 = (CraftLivingEntity)p;
/* 180 */           double health = p1.getHealth() - 7.0D;
/* 181 */           if (health < 0.0D) {
/* 182 */             health = 0.0D;
/*     */           }
/* 184 */           p1.setHealth(health);
/* 185 */           p1.playEffect(EntityEffect.HURT);
/* 186 */           heal(5.0F, RegainReason.REGEN);
/* 187 */           Bosslistener.effect1(p1.getLocation(), this.bukkitEntity);
/*     */         }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void CreateEffect(Location loc) {
/* 193 */     PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float)(loc.getX() + 0.0D), (float)(loc.getY() + 0.0D), (float)(loc.getZ() + 0.0D), 0.1F, 0.3F, 0.3F, 0.0F, 10, new int[0]);
/* 194 */     for (Player online : Bukkit.getOnlinePlayers()) {
/* 195 */       ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void m() {
/* 200 */     if (this.hp_delay-- <= 0) {
/* 201 */       setCustomName(ChatColor.RED + "§5§lПалпатин " + (int)getHealth() + "❤ ");
/* 202 */       if (getHealth() < 5000.0F) {
/* 203 */         heal(1.0F, RegainReason.REGEN);
/*     */       }
/* 205 */       this.hp_delay = 35;
/*     */     }
/* 207 */     this.debuff -= 1;
/* 208 */     if (this.debuff <= 0) {
/* 209 */       this.debuff = 35;
/* 210 */       superJump();
/*     */     }
/* 212 */     super.m();
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
/*     */   public void droped(double q, Material q1)
/*     */   {
/* 225 */     if (this.rand.nextFloat() <= q) {
/* 226 */       this.bukkitEntity.getWorld().dropItemNaturally(this.bukkitEntity.getLocation(), new org.bukkit.inventory.ItemStack(q1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void die()
/*     */   {
/* 232 */     if (this.spawner != null) {
/* 233 */       this.spawner.iDead();
/*     */     }
/* 235 */     if (this.killer != null) {
/* 236 */       droped(0.01D, Material.SLIME_BALL);
/* 237 */       droped(0.01D, Material.SEEDS);
/*     */       
/* 239 */       DropBoss drop = new DropBoss(this.bukkitEntity);
/* 240 */       drop.add(new org.bukkit.inventory.ItemStack(Material.BOOK));
/* 241 */       drop.add(new org.bukkit.inventory.ItemStack(Material.QUARTZ));
/* 242 */       drop.add(new org.bukkit.inventory.ItemStack(Material.NETHER_BRICK_ITEM));
/* 243 */       drop.add(new org.bukkit.inventory.ItemStack(Material.BOW));
/* 244 */       drop.getlist().addAll(Bosslistener.List(4));
/* 245 */       drop.dropedBoss();
/* 246 */       int q = 0;
/* 247 */       for (String key : this.attackers.keySet()) {
/* 248 */         int money = 25000 / this.attackers.size();
/* 249 */         Main.econ.depositPlayer(key, money);
/* 250 */         if (Bukkit.getPlayer(key) != null) {
/* 251 */           q = money;
/* 252 */           Bukkit.getPlayer(key).sendMessage(ChatColor.GREEN + "На ваш счет было зачисленно " + "15 убийств,35 Опыта и " + ChatColor.GREEN + money + "$");
/* 253 */           Levels.kills.put(this.killer.getName(), Integer.valueOf(((Integer)Levels.kills.get(this.killer.getName())).intValue() + 15));
                    Levels.xp.put(this.killer.getName(), Integer.valueOf(((Integer)Levels.xp.get(this.killer.getName())).intValue() + 35));
 
/*     */         } }
/* 255 */       Bosslistener.HoloDiad((org.bukkit.entity.Entity)this.bukkitEntity, q, 15, 35);
/* 256 */       Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&5Палпатин &cбыл повержен, нападающие получают по " + q + "$!"));
/*     */     }
/* 258 */     super.die();
/*     */   }
/*     */ }

