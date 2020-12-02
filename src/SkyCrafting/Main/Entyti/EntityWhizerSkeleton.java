package SkyCrafting.Main.Entyti;

import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import java.lang.reflect.*;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;

public class EntityWhizerSkeleton extends EntitySkeleton
{
    Spawner spawner;
    
    public EntityWhizerSkeleton(final Spawner spawner) {
        super((World)((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.spawner = spawner;
        if (this.spawner != null) {
            this.spawner.register((Entity)this);
        }
        this.bukkitEntity = this.getBukkitEntity();
        this.changeIntoWither((Skeleton)this.bukkitEntity);
    }
    
    public void changeIntoWither(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton)skeleton).getHandle();
        try {
            ent.setSkeletonType(1);
            final Field selector = EntitySkeleton.class.getDeclaredField("goalSelector");
            selector.setAccessible(true);
            final Field e = EntitySkeleton.class.getDeclaredField("e");
            e.setAccessible(true);
            final PathfinderGoalSelector goals = (PathfinderGoalSelector)selector.get(ent);
            goals.a(4, (PathfinderGoal)e.get(ent));
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    public boolean damageEntity(final DamageSource source, final float a) {
        return false;
    }
    
    public void m() {
        super.m();
    }
}
