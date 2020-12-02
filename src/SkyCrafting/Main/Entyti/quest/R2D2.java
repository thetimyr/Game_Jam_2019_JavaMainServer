package SkyCrafting.Main.Entyti.quest;

import java.util.Random;
import SkyCrafting.Main.Entyti.Spawner;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class R2D2 extends EntityMonster
{
  Spawner spawner;
  int delay = 100;
  int hp_delay = 10;
  int shot_delay = 65;
  float accuracy = 0.1F;
  Random rand = new Random();

  public R2D2(Spawner spawner)
  {
    super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
    getAttributeInstance(GenericAttributes.maxHealth).setValue(0.0D);
    getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
    getAttributeInstance(GenericAttributes.c).setValue(5.0D);
    getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.03D);
    setHealth(50.0F);

    this.spawner = spawner;
    this.fireProof = true;
    if (this.spawner != null) {
      this.spawner.register(this);
    }
    org.bukkit.inventory.ItemStack p = new org.bukkit.inventory.ItemStack(Material.STAINED_GLASS, 1, (short)8);
    ItemMeta p1 = p.getItemMeta();
    p1.setDisplayName("§6Спидер");
    p.setItemMeta(p1);
    net.minecraft.server.v1_8_R3.ItemStack testSword = CraftItemStack.asNMSCopy(p);
    setEquipment(4, testSword);
    setCustomName("2DR2D2");
    setCustomNameVisible(true);
    this.canPickUpLoot = false;
    this.fireProof = true;
    this.persistent = true;
    buff(this.bukkitEntity = getBukkitEntity());
    NBTTagCompound compound = new NBTTagCompound();
    c(compound);
    compound.setByte("NoAI", (byte)1);
    f(compound);
  }

  public void buff(CraftEntity bukkitEntity) {
    PotionEffectType type = PotionEffectType.INVISIBILITY;
    PotionEffect effect = new PotionEffect(type, 999999999, 3, false, false);
    ((LivingEntity)bukkitEntity).addPotionEffect(effect);
  }

  public boolean damageEntity(DamageSource damagesource, float f) {
    return false;
  }
}