package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class SmartSaber implements Listener, CommandExecutor
{
    public static HashMap<Integer, Inventory> invs;
    static ItemStack item;
    
    static {
        SmartSaber.invs = new HashMap<Integer, Inventory>();
        SmartSaber.item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String arg2, final String[] arg3) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("PIDORAS")) {
            final Inventory inv = this.createProfile(p, Levels.getSaber(p));
            p.openInventory(inv);
        }
        return true;
    }
    
    public static Inventory CreateInvs(final String nameInv, final int size) {
        final Inventory q0 = Bukkit.getServer().createInventory((InventoryHolder)null, 9 * size, nameInv);
        return q0;
    }
    
    @SuppressWarnings("rawtypes")
	public static ItemStack CreateItem(final String name, final Material material, final int Durability, final ArrayList<String> array) {
        final ItemStack item = new ItemStack(material);
        item.setDurability((short)Durability);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (array != null) {
            meta.setLore((List)array);
        }
        item.setItemMeta(meta);
        return item;
    }
    
    public static void Fill(final Inventory inv) {
        for (int j = 0; j < 9; ++j) {
            inv.setItem(j, SmartSaber.item);
        }
        for (int k = inv.getSize() - 10; k < inv.getSize(); ++k) {
            inv.setItem(k, SmartSaber.item);
        }
    }
    
    @SuppressWarnings("rawtypes")
	public Inventory createProfile(final Player p, final ItemStack owner) {
        final Inventory inv = CreateInvs(ChatColor.DARK_GREEN + "Ваш профиль: ", 3);
        Fill(inv);
        final ItemStack item1 = CreateItem(" ", Material.STAINED_GLASS_PANE, 10, null);
        final ArrayList<String> aray = new ArrayList<String>();
        aray.clear();
        final ItemMeta lore1 = owner.getItemMeta();
        lore1.setDisplayName(ChatColor.DARK_GREEN + "Ваш текущий меч...");
        aray.add("§6====================================================");
        aray.add("§aНажмите ЛКМ что бы выбрать свой основной меч");
        aray.add("§aНажмите ПКМ что бы обновить свой основной меч");
        aray.add("§6====================================================");
        lore1.setLore((List)aray);
        owner.setItemMeta(lore1);
        inv.setItem(9, SmartSaber.item);
        inv.setItem(10, item1);
        inv.setItem(13, item1);
        inv.setItem(11, owner);
        inv.setItem(12, item1);
        aray.clear();
        aray.add("§6=====§aВаша статистика§6=====");
        final double eco = Main.econ.getBalance(p.getName());
        final int a1 = (int)eco;
        aray.add("§7Ваш ник:§a " + p.getName());
        aray.add("§7Количество денег:§a " + a1);
        aray.add("§7Убийств:§a " + Levels.kills.get(p.getName()) + " ");
        aray.add("§7Опыт:§a " + Levels.xp.get(p.getName()) + " ");
        aray.add("§7Смертей:§a " + Levels.deaths.get(p.getName()) + " ");
        inv.setItem(13, Skull(p.getName(), null, 1, aray));
        inv.setItem(14, item1);
        inv.setItem(17, SmartSaber.item);
        SmartSaber.invs.put(1, inv);
        return inv;
    }
    
    @SuppressWarnings("rawtypes")
	public static ItemStack Skull(final String skullOwner, final String displayName, final int quantity, final ArrayList<String> array) {
        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, quantity, (short)(byte)SkullType.PLAYER.ordinal());
        final SkullMeta skullMeta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        skullMeta.setOwner(skullOwner);
        if (displayName != null) {
            skullMeta.setDisplayName(displayName.replace("Голова", ""));
        }
        skullMeta.setLore((List)array);
        skull.setItemMeta((ItemMeta)skullMeta);
        return skull;
    }
}
