package SkyCrafting.Main.Entyti;

import org.bukkit.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;

public class Spawner
{
    public static Map<UUID, Spawner> spawners;
    Location location;
    EntityTypes type;
    Entity current;
    long deathtime;
    int interval;
    UUID uid;
    
    static {
        Spawner.spawners = new HashMap<UUID, Spawner>();
    }
    
    public Spawner(final Location location, final EntityTypes type, final int interval) {
        this.location = location;
        this.current = null;
        this.deathtime = -1L;
        this.uid = UUID.randomUUID();
        this.type = type;
        this.interval = interval;
        Spawner.spawners.put(this.uid, this);
    }
    
    public void spawn() {
        if (this.location.getChunk().isLoaded()) {
            EntityTypes.spawnEntity(this.type, this.location.clone().add(0.0, 1.0, 0.0), this);
        }
    }
    
    public void iDead() {
        this.current = null;
        this.deathtime = System.currentTimeMillis() / 1000L;
    }
    
    public Location getSpawnLocation() {
        return this.location;
    }
    
    public long getTime() {
        return this.interval - (System.currentTimeMillis() / 1000L - this.deathtime);
    }
    
    public void update() {
        if (this.current == null) {
            if (System.currentTimeMillis() / 1000L - this.deathtime >= this.interval) {
                this.spawn();
            }
        }
        else if (this.current.getBukkitEntity().getLocation().distance(this.location) > 64.0) {
            this.reset();
        }
    }
    
    public void register(final Entity me) {
        this.current = me;
    }
    
    public void reset() {
        if (this.current != null) {
            if (this.current.passenger != null) {
                this.current.passenger.getBukkitEntity().remove();
            }
            this.current.getBukkitEntity().remove();
        }
        this.deathtime = -1L;
        this.spawn();
    }
    
    public UUID getUid() {
        return this.uid;
    }
    
    public Entity getCurrent() {
        return this.current;
    }
    
    public EntityTypes getType() {
        return this.type;
    }
}
