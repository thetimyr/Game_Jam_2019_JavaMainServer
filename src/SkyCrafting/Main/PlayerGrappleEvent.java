package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class PlayerGrappleEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private Player player;
    private Entity entity;
    private Location pullLocation;
    private ItemStack hookItem;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerGrappleEvent(final Player p, final Entity e, final Location l) {
        this.cancelled = false;
        this.player = p;
        this.entity = e;
        this.pullLocation = l;
        this.hookItem = p.getItemInHand();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Entity getPulledEntity() {
        return this.entity;
    }
    
    public Location getPullLocation() {
        return this.pullLocation;
    }
    
    public ItemStack getHookItem() {
        return this.hookItem;
    }
    
    public HandlerList getHandlers() {
        return PlayerGrappleEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerGrappleEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean set) {
        this.cancelled = set;
    }
}
