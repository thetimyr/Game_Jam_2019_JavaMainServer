package SkyCrafting.Main.Entyti;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import SkyCrafting.Main.*;
import org.bukkit.plugin.*;

public class GologramsDiad
{
    String name;
    double Y;
    Entity boss;
    public static ArrayList<Entity> holo;
    
    static {
        GologramsDiad.holo = new ArrayList<Entity>();
    }
    
    public ArmorStand Hologram() {
        final ArmorStand as = (ArmorStand)this.boss.getWorld().spawnEntity(this.boss.getLocation().add(0.0, this.Y, 0.0), EntityType.ARMOR_STAND);
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setGravity(false);
        as.setMarker(true);
        return as;
    }
    
    public void time(final ArmorStand as) {
        GologramsDiad.holo.add((Entity)as);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.instance, () -> {
            as.remove();
            GologramsDiad.holo.remove(as);
        }, 100L);
    }
    
    public GologramsDiad(final Entity boss, final double Y) {
        this.Y = Y;
        this.boss = boss;
    }
    
    public GologramsDiad SetName(final String name) {
        this.addline(this.name = name);
        return this;
    }
    
    public void addline(final String name) {
        if (this.boss != null && this.boss.getLocation() != null) {
            final ArmorStand as = this.Hologram();
            as.setCustomName(name);
            this.time(as);
        }
    }
}
