package SkyCrafting.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Test implements Listener, CommandExecutor{
	@SuppressWarnings("unused")
	private Inventory inv;

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("loh")) {
        	final ItemStack quest1 = new ItemStack(Material.ENCHANTED_BOOK);
        	//(this.inv = Bukkit.getServer().createInventory((InventoryHolder)null, 18, "§3Квесты")).setItem(0, quest1); 
        	Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER,"§2§kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        	inv.setItem(0, quest1);
   
        	p.openInventory(inv);
        	
        }
		return false;
    }
    /*public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase("Я ебал ваших метерей")) {
            final Player p = (Player)e.getWhoClicked();
            
            e.setCancelled(true);
        }
    }*/

}
