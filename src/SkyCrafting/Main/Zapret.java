package SkyCrafting.Main;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.*;
import org.bukkit.event.*;
//import org.bukkit.potion.*;
//import SkyCrafting.Main.Entyti.*;
//import SkyCrafting.Main.donats.*;
//import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
//import SkyCrafting.Main.*;
import org.bukkit.entity.*;

public class Zapret implements Listener
{
    @EventHandler
    public void onPlayerItemChang1(final PlayerItemHeldEvent event) {
        final ArrayList<Material> nodrop = new ArrayList<Material>();
        Player p = event.getPlayer();
        /*nodrop.add(Material.IRON_SWORD);
        nodrop.add(Material.DIAMOND_SWORD);
        nodrop.add(Material.MAGMA_CREAM);
        nodrop.add(Material.GOLD_SWORD);
        nodrop.add(Material.BONE);
        nodrop.add(Material.BOWL);
        nodrop.add(Material.RECORD_11);
        nodrop.add(Material.SHEARS);
        nodrop.add(Material.RABBIT);
        nodrop.add(Material.WOOD_SWORD);
        nodrop.add(Material.STONE_SWORD);
        nodrop.add(Material.RABBIT_FOOT);
        nodrop.add(Material.MONSTER_EGG);
        nodrop.add(Material.MONSTER_EGGS);
        nodrop.add(Material.FIREBALL);*/
        nodrop.add(Material.IRON_DOOR);
        try
        {
          for (Material q : nodrop)
            if ((event.getNewSlot() != 0) && (p.getInventory().getItem(event.getNewSlot()).getType() == q)) {
              p.getInventory().setItem(event.getNewSlot(), new ItemStack(Material.APPLE, 5));
              p.sendTitle("§cПредмет запрещён!", "");
            }
        }
        catch (Exception e) {
          try {
            for (Material q : nodrop)
              if ((event.getNewSlot() != 0) && (p.getInventory().getItem(event.getNewSlot()).getType() == q)) {
                p.getInventory().setItem(event.getNewSlot(), new ItemStack(Material.APPLE, 5));
                p.sendTitle("§cПредмет запрещён!", "");
              }
          }
          catch (Exception localException2)
          {
          }
        }
      }
    @EventHandler
    public void onPlayerItemChange(final PlayerItemHeldEvent event,PlayerDropItemEvent e) {
        final Player p = event.getPlayer();
        final Player p2 = e.getPlayer();
        final PotionEffectType type = PotionEffectType.SPEED;
        final ArrayList<Material> nodrop = new ArrayList<Material>();
        nodrop.add(Material.IRON_SWORD);
        nodrop.add(Material.DIAMOND_SWORD);
        nodrop.add(Material.MAGMA_CREAM);
        nodrop.add(Material.GOLD_SWORD);
        nodrop.add(Material.BONE);
        nodrop.add(Material.BOWL);
        nodrop.add(Material.RECORD_11);
        nodrop.add(Material.SHEARS);
        nodrop.add(Material.RABBIT);
        nodrop.add(Material.WOOD_SWORD);
        nodrop.add(Material.STONE_SWORD);
        nodrop.add(Material.RABBIT_FOOT);
        final ArrayList<Material> nodrop2 = new ArrayList<Material>();
        nodrop2.add(Material.IRON_SWORD);
        nodrop2.add(Material.DIAMOND_SWORD);
        nodrop2.add(Material.MAGMA_CREAM);
        nodrop2.add(Material.GOLD_SWORD);
        nodrop2.add(Material.BONE);
        nodrop2.add(Material.BOWL);
        nodrop2.add(Material.RECORD_11);
        nodrop2.add(Material.SHEARS);
        nodrop2.add(Material.RABBIT);
        nodrop2.add(Material.WOOD_SWORD);
        nodrop2.add(Material.STONE_SWORD);
        nodrop2.add(Material.RABBIT_FOOT);
        try {
            for (final Material q : nodrop) {
                if (p.getInventory().getItem(event.getNewSlot()).getType() == q) {
                    final PotionEffect effect = new PotionEffect(type, 999999999, 0, false, false);
                    p.addPotionEffect(effect);
                    e.setCancelled(true);
                    //SpawnerUpdater.sendActionBar(p, "§aНажмите на Q что бы бросить этот меч!");
                }
                else {
                    if (p.getInventory().getItem(event.getPreviousSlot()).getType() != q) {
                        continue;
                    }
                    p.removePotionEffect(type);
                }
            }
            for (final Material q : nodrop2) {
                if (p2.getInventory().getItem(event.getNewSlot()).getType() == q) {
                    e.setCancelled(true);
                }
            }
        }
        catch (Exception e1) {
            try {
                for (final Material q2 : nodrop) {
                    if (p.getInventory().getItem(event.getNewSlot()).getType() == q2) {
                        final PotionEffect effect2 = new PotionEffect(type, 999999999, 0, false, false);
                        p.addPotionEffect(effect2);
                        //SpawnerUpdater.sendActionBar(p, "§aНажмите на Q что бы бросить этот меч!");
                        return;
                    }
                }
            }
            catch (Exception e2) {
                p.removePotionEffect(type);
            }
            p.removePotionEffect(type);
        }
    }
}
