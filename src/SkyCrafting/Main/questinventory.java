package SkyCrafting.Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class questinventory implements Listener, CommandExecutor{
	@SuppressWarnings("unused")
	private Inventory inv;

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("quests")) {
            String status = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 1) {status = "§aВыполнено!";}
            String status2 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 2) {status2 = "§aВыполнено!";}
            String status3 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 3) {status3 = "§aВыполнено!";}
            String status4 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 4) {status4 = "§aВыполнено!";}
            String status5 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 5) {status5 = "§aВыполнено!";}
            String status6 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 6) {status6 = "§aВыполнено!";}
            String status7 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 7) {status7 = "§aВыполнено!";}
            String status8 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 8) {status8 = "§aВыполнено!";}
            String status9 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 9) {status9 = "§aВыполнено!";}
            String status10 = "§cНе выполнено!";
        	if(Levels.getCrystal(p) >= 10) {status10 = "§aВыполнено!";}
		            final ArrayList<String> quest1lore = new ArrayList<String>();
	            	quest1lore.add(("§7Привет задания лёгкое."));
	            	quest1lore.add(("§7Найди Апгрейд"));
	            	quest1lore.add((""));
	            	quest1lore.add(("§7Оплата за задание:"));
	            	quest1lore.add(("§7+ §6$5000"));
	            	quest1lore.add(("§7+ 3 Железный кредит"));
	            	quest1lore.add(("§7Статус: " + status));
	            	quest1lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest1 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta1 = quest1.getItemMeta();
		            meta1.setLore(quest1lore);
		            meta1.setDisplayName("§5Квест 1");
		            quest1.setItemMeta(meta1);	

		            final ArrayList<String> quest2lore = new ArrayList<String>();
		            quest2lore.add(("§7Скрафти меч джедая"));
		            quest2lore.add((""));
		            quest2lore.add(("§7Оплата за задание:"));
		            quest2lore.add(("§7+ §6$12000"));
		            quest2lore.add(("§7+ 5 Железный кредит"));
		            quest2lore.add(("§7Статус: " + status2));
		            quest2lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest2 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta2 = quest2.getItemMeta();
		            meta2.setLore(quest2lore);
		            meta2.setDisplayName("§5Квест 2");
		            quest2.setItemMeta(meta2);	
		            
		            final ArrayList<String> quest3lore = new ArrayList<String>();
		            quest3lore.add(("§7Привет! Я хотел сделать мечь но потерял свою рукоядку ;(."));
		            quest3lore.add(("§7Сможешь помочь и найти для меня рукоядку?"));
		            quest3lore.add((""));
		            quest3lore.add(("§7Оплата за задание:"));
		            quest3lore.add(("§7+ §6$14000"));
		            quest3lore.add(("§7+ 15 Железный кредит"));
		            quest3lore.add(("§7Статус: " + status3));
		            quest3lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest3 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta3 = quest3.getItemMeta();
		            meta3.setLore(quest3lore);
		            meta3.setDisplayName("§5Квест 3");
		            quest3.setItemMeta(meta3);	
		            
		            final ArrayList<String> quest4lore = new ArrayList<String>();
		            quest4lore.add(("§7Привет! Я случайно уронил энергоячейку ;("));
		            quest4lore.add(("§7Сможешь помочь и принести для меня её?"));
		            quest4lore.add((""));
		            quest4lore.add(("§7Оплата за задание:"));
		            quest4lore.add(("§7+ §6$4000"));
		            quest4lore.add(("§7+ 5 Апгрейдов"));
		            quest4lore.add(("§7Статус: " + status4));
		            quest4lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest4 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta4 = quest4.getItemMeta();
		            meta4.setLore(quest4lore);
		            meta4.setDisplayName("§5Квест 4");
		            quest4.setItemMeta(meta4);	
		            
		            final ArrayList<String> quest5lore = new ArrayList<String>();
		            quest5lore.add(("§7Привет! Сможешь принести мне темную ауру?"));
		            quest5lore.add((""));
		            quest5lore.add(("§7Оплата за задание:"));
		            quest5lore.add(("§7+ §6$16000"));
		            quest5lore.add(("§7+ 1 Золотой кредит"));
		            quest5lore.add(("§7Статус: " + status5));
		            quest5lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest5 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta5 = quest5.getItemMeta();
		            meta5.setLore(quest5lore);
		            meta5.setDisplayName("§5Квест 5");
		            quest5.setItemMeta(meta5);	
		            
		            final ArrayList<String> quest6lore = new ArrayList<String>();
		            quest6lore.add(("§7Привет говорят что РЕЙ очень сложный босс."));
		            quest6lore.add(("§7Сможешь убить его?"));
		            quest6lore.add((""));
		            quest6lore.add(("§7Оплата за задание:"));
		            quest6lore.add(("§7+ §6$10000"));
		            quest6lore.add(("§7+ 3 Золотой кредит"));
		            quest6lore.add(("§7Статус: " + status6));
		            quest6lore.add(("§7Для выполнения вы должны убить босса Рей!"));
		            final ItemStack quest6 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta6 = quest6.getItemMeta();
		            meta6.setLore(quest6lore);
		            meta6.setDisplayName("§5Квест 6");
		            quest6.setItemMeta(meta6);	
		            
		            final ArrayList<String> quest7lore = new ArrayList<String>();
		            quest7lore.add(("§7Привет сможешь убить Айд-жи?"));
		            quest7lore.add((""));
		            quest7lore.add(("§7Оплата за задание:"));
		            quest7lore.add(("§7+ §6$15000"));
		            quest7lore.add(("§7+ 3 Золотой кредит"));
		            quest7lore.add(("§7Статус: " + status7));
		            quest7lore.add(("§7Для выполнения вы должны убить босса Айд-жи!"));
		            final ItemStack quest7 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta7 = quest7.getItemMeta();
		            meta7.setLore(quest7lore);
		            meta7.setDisplayName("§5Квест 7");
		            quest7.setItemMeta(meta7);	
		            
		            final ArrayList<String> quest8lore = new ArrayList<String>();
		            quest8lore.add(("§7Привет, говорят что Джабба слишком зелёная"));
		            quest8lore.add(("§7Сможешь убить её?"));
		            quest8lore.add((""));
		            quest8lore.add(("§7Оплата за задание:"));
		            quest8lore.add(("§7+ §6$12000"));
		            quest8lore.add(("§7+ 2 Золотой кредит"));
		            quest8lore.add(("§7Статус: " + status8));
		            quest8lore.add(("§7Для выполнения вы должны убить босса Джабба!"));
		            final ItemStack quest8 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta8 = quest8.getItemMeta();
		            meta8.setLore(quest8lore);
		            meta8.setDisplayName("§5Квест 8");
		            quest8.setItemMeta(meta8);	
		            
		            final ArrayList<String> quest9lore = new ArrayList<String>();
		            quest9lore.add(("§7Скоро..."));
		            quest9lore.add(("§7Скоро..."));
		            quest9lore.add((""));
		            quest9lore.add(("§7Оплата за задание:"));
		            quest9lore.add(("§7+ §6$5000"));
		            quest9lore.add(("§7+ 3 Железный кредит"));
		            quest9lore.add(("§7Статус: " + status9));
		            quest9lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!!"));
		            final ItemStack quest9 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta9 = quest9.getItemMeta();
		            meta9.setLore(quest9lore);
		            meta9.setDisplayName("§5Квест 9");
		            quest9.setItemMeta(meta9);	
		            
		            final ArrayList<String> quest10lore = new ArrayList<String>();
		            quest10lore.add(("§7Скоро...."));
		            quest10lore.add(("§7Скоро..."));
		            quest10lore.add((""));
		            quest10lore.add(("§7Оплата за задание:"));
		            quest10lore.add(("§7+ §6$5000"));
		            quest10lore.add(("§7+ 3 Железный кредит"));
		            quest10lore.add(("§7Статус: " + status10));
		            quest10lore.add(("§7Для выполнения вы должны держать предмет в руке,в количестве 1 шт!"));
		            final ItemStack quest10 = new ItemStack(Material.ENCHANTED_BOOK);
		            final ItemMeta meta10 = quest10.getItemMeta();
		            meta10.setLore(quest10lore);
		            meta10.setDisplayName("§5Квест 10");
		            quest10.setItemMeta(meta10);	
		            
                    (this.inv = Bukkit.getServer().createInventory((InventoryHolder)null, 18, "§3Квесты")).setItem(0, quest1); 
                    inv.setItem(1, quest2);
                    inv.setItem(2, quest3);
                    inv.setItem(3, quest4);
                    inv.setItem(4, quest5);
                    inv.setItem(5, quest6);
                    inv.setItem(6, quest7);
                    inv.setItem(7, quest8);
                    inv.setItem(8, quest9);
                    inv.setItem(9, quest10);
                    p.openInventory(this.inv);
                    return true;
            }
		return false;
        }
    @SuppressWarnings({ "unused" })
	@EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase("§3Квесты")) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
        	if(e.getCurrentItem().getType().equals((Object)Material.ENCHANTED_BOOK) && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Квест 1"){
        		if(p.getInventory().getItemInHand().getType().equals(Material.FIREWORK_CHARGE)) {
        			if(Levels.getCrystal(p) == 0) {
            			p.setItemInHand(new ItemStack(Material.getMaterial(0), 1));        
            			Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            			Main.econ.depositPlayer(p, (double)5000);
            			p.getInventory().addItem(new ItemStack(Material.getMaterial(265), 3));
            			p.sendMessage("§aВы успешно выполнили 1 квест!");
            			p.closeInventory();
            			JediScoreBoard.UpdateList(p);
            			JediScoreBoard.updateScoreboard(p);
        			}
        		}else {
	        		p.closeInventory();
	        		p.sendMessage("§cНе все требования были выполнены");
        		}
        	}
        	if(e.getCurrentItem().getType().equals((Object)Material.ENCHANTED_BOOK) && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Квест 2"){
        		if(p.getInventory().getItemInHand().getType().equals(Material.IRON_SWORD)) {
        			if(Levels.getCrystal(p) == 1) {
            			p.setItemInHand(new ItemStack(Material.getMaterial(0), 1)); 
            			p.getInventory().addItem(new ItemStack(267));
            			Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            			Main.econ.depositPlayer(p, (double)12000);
            			p.getInventory().addItem(new ItemStack(Material.getMaterial(265), 5));//кредиты
            			p.sendMessage("§aВы успешно выполнили 2 квест!");
            			p.closeInventory();
            			JediScoreBoard.UpdateList(p);
            			JediScoreBoard.updateScoreboard(p);
        			}
        		}else {
	        		p.closeInventory();
	        		p.sendMessage("§cНе все требования были выполнены");
        		}
        	}
        	if(e.getCurrentItem().getType().equals((Object)Material.ENCHANTED_BOOK) && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Квест 3"){
        		if(p.getInventory().getItemInHand().getType().equals(Material.GLOWSTONE_DUST)) {
        			if(Levels.getCrystal(p) == 2) {
            			p.setItemInHand(new ItemStack(Material.getMaterial(0), 1)); 
            			Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            			Main.econ.depositPlayer(p, (double)14000);
            			p.getInventory().addItem(new ItemStack(Material.getMaterial(265), 15));//кредиты
            			p.sendMessage("§aВы успешно выполнили 3 квест!");
            			p.closeInventory();
            			JediScoreBoard.UpdateList(p);
            			JediScoreBoard.updateScoreboard(p);
        			}
        		}else {
	        		p.closeInventory();
	        		p.sendMessage("§cНе все требования были выполнены");
        		}
        	}
        	if(e.getCurrentItem().getType().equals((Object)Material.ENCHANTED_BOOK) && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Квест 4"){
        		if(p.getInventory().getItemInHand().getType().equals(Material.SEEDS)) {
        			if(Levels.getCrystal(p) == 3) {
            			p.setItemInHand(new ItemStack(Material.getMaterial(0), 1)); 
            			Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            			Main.econ.depositPlayer(p, (double)4000);
            			p.getInventory().addItem(new ItemStack(Material.getMaterial(402), 5));//кредиты
            			p.sendMessage("§aВы успешно выполнили 4 квест!");
            			p.closeInventory();
            			JediScoreBoard.UpdateList(p);
            			JediScoreBoard.updateScoreboard(p);
        			}
        		}else {
	        		p.closeInventory();
	        		p.sendMessage("§cНе все требования были выполнены");
        		}
        	}
        	if(e.getCurrentItem().getType().equals((Object)Material.ENCHANTED_BOOK) && e.getCurrentItem().getItemMeta().getDisplayName() == "§5Квест 5"){
        		if(p.getInventory().getItemInHand().getType().equals(Material.RECORD_3)) {
        			if(Levels.getCrystal(p) == 4) {
            			p.setItemInHand(new ItemStack(Material.getMaterial(0), 1)); 
            			Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            			Main.econ.depositPlayer(p, (double)16000);
            			p.getInventory().addItem(new ItemStack(Material.getMaterial(266), 1));//кредиты
            			p.sendMessage("§aВы успешно выполнили 5 квест!");
            			p.closeInventory();
            			JediScoreBoard.UpdateList(p);
            			JediScoreBoard.updateScoreboard(p);
        			}
        		}else {
	        		p.closeInventory();
	        		p.sendMessage("§cНе все требования были выполнены");
        		}
        	}
        }
    }

}
