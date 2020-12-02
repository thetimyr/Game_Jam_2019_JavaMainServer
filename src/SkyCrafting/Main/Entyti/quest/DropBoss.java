package SkyCrafting.Main.Entyti.quest;

import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class DropBoss
{
    ArrayList<ItemStack> Item;
    Entity type;
    static Random rand;
    
    static {
        DropBoss.rand = new Random();
    }
    
    public DropBoss(final Entity type) {
        this.Item = new ArrayList<ItemStack>();
        this.type = type;
        this.Item.add(new ItemStack(Material.IRON_INGOT, DropBoss.rand.nextInt(25)));
        this.Item.add(new ItemStack(Material.COOKED_BEEF, DropBoss.rand.nextInt(15)));
        this.Item.add(new ItemStack(Material.GOLD_NUGGET, 1));
    }
    
    public void add(final ItemStack e) {
        this.Item.add(e);
    }
    
    public void remove(final ItemStack e) {
        this.Item.remove(e);
    }
    
    public ArrayList<ItemStack> getlist() {
        return this.Item;
    }
    
    public Item dropedBoss() {
        final int a = DropBoss.rand.nextInt(this.Item.size());
        return this.type.getWorld().dropItemNaturally(this.type.getLocation(), (ItemStack)this.Item.get(a));
    }
}
