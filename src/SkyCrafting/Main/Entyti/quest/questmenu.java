package SkyCrafting.Main.Entyti.quest;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import SkyCrafting.Main.Levels;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class questmenu implements Listener,CommandExecutor
{
	private Inventory inv;
	String iname = "§cQuests";
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] a) {
		Player p = (Player)sender;
		String bost = "§60 уровня";
		String lore1 = "";
		String lore2 = "";
		String lore3 = "";
		String nextbost = "0";
		if(PermissionsEx.getUser(p).getPlayer().hasPermission("boster1")) {
			bost = "§61 уровня";
			lore1 = "§7Каждые 15 минут вам будет начислятся:";
			lore2 = "§b10$";
			lore3 = "";
			nextbost = "1";
		}
		if(PermissionsEx.getUser(p).getPlayer().hasPermission("boster2")) {
			bost = "§62 уровня";
			lore1 = "§7Каждые 12 минуты вам будет начислятся:";
			lore2 = "§b100$";
			lore3 = "§7";
			nextbost = "2";
		}
		if(PermissionsEx.getUser(p).getPlayer().hasPermission("boster3")) {
			bost = "§63 уровня";
			lore1 = "§7Каждые 5 минуты вам будет начислятся:";
			lore2 = "§b1000$";
			lore3 = "§7";
			nextbost = "4";
		}
		if(PermissionsEx.getUser(p).getPlayer().hasPermission("boster4")) {
			bost = "§63 уровня";
			lore1 = "§7Каждые 5 минуты вам будет начислятся:";
			lore2 = "§b1000$";
			lore3 = "§7";
			nextbost = "5";
		}
		if(PermissionsEx.getUser(p).getPlayer().hasPermission("boster5")) {
			bost = "§63 уровня";
			lore1 = "§7Каждые 5 минуты вам будет начислятся:";
			lore2 = "§b1000$";
			lore3 = "§7";
			nextbost = "6";
		}
		if (command.getName().equalsIgnoreCase("qvestskill")) {
			final ItemStack glass = new ItemStack(Material.SULPHUR);
	        final ItemMeta meta = glass.getItemMeta();
	        meta.setDisplayName("§cБустер денег");
	        final ArrayList<String> lore = new ArrayList<String>();
	        lore.add(lore1);
	        lore.add(lore2);
	        lore.add(lore3);
	        lore.add("§cВаш бустер: " + bost);
	        lore.add("§сСледующий уровень: §6" + nextbost);	        
	        lore.add("§eНажмите чтобы апгрейднуть!");
	        meta.setLore(lore);
	        glass.setItemMeta(meta);
	        
			inv = Bukkit.createInventory(null, 27, iname);
			inv.setItem(4, glass);
			p.openInventory(inv);
		}		
    	return true;
    }

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getName().equalsIgnoreCase(iname)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals((Object)Material.SULPHUR)) {
				if(Levels.getCrystal(p) == 0) {
					if(Levels.getXp(p) >= 1000) {
						p.sendTitle("§aУспешно!","");						
						Levels.xp.put(p.getName(), Integer.valueOf(((Integer)Levels.xp.get(p.getName())).intValue() - 1000));
						PermissionsEx.getUser(p).addPermission("boster2");	
						Levels.crystal.put(p.getName(), Integer.valueOf(((Integer)Levels.crystal.get(p.getName())).intValue() + 1));
						p.closeInventory();
					}else {
						int xp = Levels.getXp(p);
						String x = String.valueOf(xp);						
						p.sendTitle("§cОшибка ^_^", "§eНе хватает опыта §6"+ x + "/1000");
						p.closeInventory();
					}
					p.closeInventory();
				}
				if(Levels.getCrystal(p) == 1) {
					if(Levels.getXp(p) >= 7000) {
						p.sendTitle("§aУспешно!","");						
						Levels.xp.put(p.getName(), Integer.valueOf(((Integer)Levels.xp.get(p.getName())).intValue() - 7000));
						PermissionsEx.getUser(p).addPermission("boster3");
						Levels.crystal.put(p.getName(), Integer.valueOf(((Integer)Levels.crystal.get(p.getName())).intValue() + 1));
						p.closeInventory();
					}else {
						int xp = Levels.getXp(p);
						String x = String.valueOf(xp);						
						p.sendTitle("§cОшибка ^_^", "§eНе хватает опыта §6"+ x + "/7000");
						p.closeInventory();
					}
					p.closeInventory();
				}
				if(Levels.getCrystal(p) == 2) {
					if(Levels.getXp(p) >= 12000) {
						p.sendTitle("§aУспешно!","");						
						Levels.xp.put(p.getName(), Integer.valueOf(((Integer)Levels.xp.get(p.getName())).intValue() - 12000));
						PermissionsEx.getUser(p).addPermission("boster4");	
						Levels.crystal.put(p.getName(), Integer.valueOf(((Integer)Levels.crystal.get(p.getName())).intValue() + 1));
						p.closeInventory();
					}else {
						int xp = Levels.getXp(p);
						String x = String.valueOf(xp);						
						p.sendTitle("§cОшибка ^_^", "§eНе хватает опыта §6"+ x + "/12000");
						p.closeInventory();
					}
					p.closeInventory();
				}

			}
		}
	}
}
