package SkyCrafting.Main.Entyti;

import java.util.concurrent.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Item;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class PooCow extends EntityCow
{
    int pooTimer;
    CopyOnWriteArrayList<Item> log;
    Spawner spawner;
    
    public PooCow(final World world) {
        super((net.minecraft.server.v1_8_R3.World)((CraftWorld)world).getHandle());
        this.pooTimer = 1200 + this.random.nextInt(240);
        this.log = new CopyOnWriteArrayList<Item>();
    }
    
    public PooCow(final Spawner spawner) {
        super((net.minecraft.server.v1_8_R3.World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.pooTimer = 1200 + this.random.nextInt(240);
        this.log = new CopyOnWriteArrayList<Item>();
        spawner.register((Entity)this);
    }
    
    public boolean damageEntity(final DamageSource source, final float a) {
        return false;
    }
    
    public void poo() {
        for (final Item poo : this.log) {
            if (poo != null && ((org.bukkit.entity.Entity) poo).isValid()) {
                continue;
            }
            this.log.remove(poo);
        }
        if (this.log.size() <= 3) {
            this.log.add((Item) this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), new ItemStack(Material.APPLE)));
        }
    }
    
    public void m() {
        super.m();
        if (this.pooTimer-- <= 0) {
            this.poo();
            this.pooTimer = 1200 + this.random.nextInt(240);
        }
    }
}
