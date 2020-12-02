package SkyCrafting.Main;

import org.bukkit.command.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

import java.util.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class Factions implements Listener, CommandExecutor
{
    private Inventory inv;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("side")) {
            if ((args.length == 1) && (args[0].equalsIgnoreCase("leave"))) {
                if (Main.econ.getBalance(p) >= 120000.0D) {
                  ItemStack jedi = new ItemStack(Material.CLAY_BRICK);
                  ItemStack sith = new ItemStack(Material.SUGAR_CANE);
                  ItemStack ali = new ItemStack(Material.LEATHER);
                  
                  
                  ItemMeta jediMeta = jedi.getItemMeta();
                  ItemMeta sithMeta = sith.getItemMeta();
                  ItemMeta aliMeta = sith.getItemMeta();

                  sithMeta.setDisplayName("§cСитхи");
                  aliMeta.setDisplayName("§7Альянс");
                  jediMeta.setDisplayName("§3Джедаи");                                  
                                   
                  jedi.setItemMeta(jediMeta);
                  sith.setItemMeta(sithMeta);
                  ali.setItemMeta(aliMeta);                  
                  
                  (this.inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9, "§dВыбор фракции")).setItem(6, sith);                
                  this.inv.setItem(2, jedi);
                  this.inv.setItem(4, ali);
                  
                  
	              final ArrayList<String> lorejedi = new ArrayList<String>();
	               	  lorejedi.add("§aНажмите чтобы выбрать!");
	              final ArrayList<String> loreali = new ArrayList<String>();
	               	  loreali.add("§aНажмите чтобы выбрать!");                    
	              final ArrayList<String> loresith = new ArrayList<String>();
	               	  loresith.add("§aНажмите чтобы выбрать!");                    
	              
	              jediMeta.setLore(lorejedi);
	              jediMeta.setLore(loreali);
	              jediMeta.setLore(loresith);                 
                  p.sendMessage("§cПри смене или выборе фракции вы будите кикнуты, это необходимо для добавления вас в базу данных");
                  p.openInventory(this.inv);
                  p.sendTitle("§4Вас может кикнуть!", "");
                  return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lУ вас нет на щету 120к!"));
                return true;
              }
            else {
                if (Levels.getLevel(p) < 6) {
                    p.sendMessage(ChatColor.GREEN + "Для выбора стороны вам необходимо достичь 6 лвл.");
                    return true;
                }
                if (!Main.instance.levelsConfig.contains(String.valueOf(p.getName()) + ".faction")) {
                    ItemStack jedi = new ItemStack(Material.CLAY_BRICK);
                    ItemStack sith = new ItemStack(Material.SUGAR_CANE);
                    ItemStack ali = new ItemStack(Material.LEATHER);
                    
                    
                    ItemMeta jediMeta = jedi.getItemMeta();
                    ItemMeta sithMeta = sith.getItemMeta();
                    ItemMeta aliMeta = sith.getItemMeta();

                    sithMeta.setDisplayName("§cСитхи");
                    aliMeta.setDisplayName("§7Альянс");
                    jediMeta.setDisplayName("§3Джедаи");                                  
                                     
                    jedi.setItemMeta(jediMeta);
                    sith.setItemMeta(sithMeta);
                    ali.setItemMeta(aliMeta);   
                    
                    final ArrayList<String> lorejedi = new ArrayList<String>();
                     	lorejedi.add("§aНажмите чтобы выбрать!");
                    final ArrayList<String> loreali = new ArrayList<String>();
                     	loreali.add("§aНажмите чтобы выбрать!");                    
                    final ArrayList<String> loresith = new ArrayList<String>();
                     	loresith.add("§aНажмите чтобы выбрать!");                    
                    
                    jediMeta.setLore(lorejedi);
                    aliMeta.setLore(loreali);
                    sithMeta.setLore(loresith);
                    
                    (this.inv = Bukkit.getServer().createInventory((InventoryHolder)null, 9, "§dВыбор фракции")).setItem(6, sith);               
                    this.inv.setItem(2, jedi);
                    this.inv.setItem(4, ali);
                    
                    
                    p.sendMessage("§cПри смене или выборе фракции вы будите кикнуты, это необходимо для добавления вас в базу данных");
                    p.openInventory(this.inv);
                    p.sendTitle("§4Вас может кикнуть!", "");
                    return true;
                }
                p.sendMessage(ChatColor.GREEN + "Вы уже выбрали свою сторону!");
                return true;
            }
        }
        else {
            if (!command.getName().equalsIgnoreCase("base") || !Levels.faction.containsKey(p.getName())) {
                return true;
            }
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedi.world")), 
            		(double)Main.instance.getConfig().getInt("jedi.x"), 
            		(double)Main.instance.getConfig().getInt("jedi.y"), 
            		(double)Main.instance.getConfig().getInt("jedi.z"));
            
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sith.world")), 
            		(double)Main.instance.getConfig().getInt("sith.x"), 
            		(double)Main.instance.getConfig().getInt("sith.y"), 
            		(double)Main.instance.getConfig().getInt("sith.z"));
            
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("ali.world")), 
            		(double)Main.instance.getConfig().getInt("ali.x"), 
            		(double)Main.instance.getConfig().getInt("ali.y"), 
            		(double)Main.instance.getConfig().getInt("ali.z"));
            
            
            if (Levels.faction.get(p.getName()).equalsIgnoreCase("Jedi")) {
                p.teleport(jediLoc);
                return true;
            }
            if (Levels.faction.get(p.getName()).equalsIgnoreCase("Sith")) {
                p.teleport(sithLoc);
                return true;
            }
            if (Levels.faction.get(p.getName()).equalsIgnoreCase("Ali")) {
                p.teleport(aliLoc);
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону. Пропишите /side для выбора строны");
            return true;
        }
    }
    
    public ArrayList<String> CangeStoron(final Player p, final ArrayList<String> getListJediPlaeyr) {
        final ArrayList<String> Jedi = new ArrayList<String>();
        Jedi.add("IRON_SWORD");
        Jedi.add("IRON_SWORD1");
        Jedi.add("WOOD_SWORD");
        Jedi.add("MAGMA_CREAM");
        Jedi.add("MAGMA_CREAM1");
        Jedi.add("MAGMA_CREAM3");
        final ArrayList<String> Sith = new ArrayList<String>();
        Sith.add("GOLD_SWORD");
        Sith.add("GOLD_SWORD1");
        Sith.add("STONE_SWORD");
        Sith.add("BONE");
        Sith.add("BONE1");
        Sith.add("BONE3");
        if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
            for (int a = 0; a < getListJediPlaeyr.size(); ++a) {
                for (int a2 = 0; a2 < Sith.size(); ++a2) {
                    if (Sith.get(a2).equalsIgnoreCase(getListJediPlaeyr.get(a))) {
                        getListJediPlaeyr.remove(a);
                        getListJediPlaeyr.add(Jedi.get(a2));
                    }
                }
            }
        }
        if (Levels.getSide(p).equalsIgnoreCase("Ali")) {
            for (int a = 0; a < getListJediPlaeyr.size(); ++a) {
                for (int a2 = 0; a2 < Sith.size(); ++a2) {
                    if (Sith.get(a2).equalsIgnoreCase(getListJediPlaeyr.get(a))) {
                        getListJediPlaeyr.remove(a);
                        getListJediPlaeyr.add(Jedi.get(a2));
                        getListJediPlaeyr.add(Sith.get(a2));
                    }
                }
            }
        }
        else if (Levels.getSide(p).equalsIgnoreCase("Sith")) {
            for (int a = 0; a < getListJediPlaeyr.size(); ++a) {
                for (int a2 = 0; a2 < Jedi.size(); ++a2) {
                    if (Jedi.get(a2).equalsIgnoreCase(getListJediPlaeyr.get(a))) {
                        getListJediPlaeyr.remove(a);
                        getListJediPlaeyr.add(Sith.get(a2));
                    }
                }
            }
        }
        return getListJediPlaeyr;
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase("§dВыбор фракции")) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            
            if (e.getCurrentItem().getType().equals((Object)Material.CLAY_BRICK)) {
                if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lВы уже играете за Светлую сторону!"));
                    p.closeInventory();
                    return;
                }
                if (Levels.getSide(p).equalsIgnoreCase("Sith")) {
                    Main.econ.withdrawPlayer((OfflinePlayer)p, 120000.0);
                    Levels.faction.put(p.getName(), "Jedi");
                    p.sendMessage(ChatColor.GREEN + "Вы примкнули к Светлой стороне!");
                    p.sendMessage(ChatColor.RED + "С вашего счета списано 120к");
                    p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                    JediScoreBoard.updateScoreboard(p);
                    p.closeInventory();
                    Levels.Items.put(p.getName(), this.CangeStoron(p, this.CangeStoron(p, Levels.Items.get(p.getName()))));
                    return;
                }
                Levels.faction.put(p.getName(), "Jedi");
                p.sendMessage(ChatColor.GREEN + "Вы примкнули к Светлой стороне!");
                JediScoreBoard.updateScoreboard(p);
                p.closeInventory();
                Levels.SetSaberList(p, "IRON_SWORD");
                Levels.SetSaber(p, "IRON_SWORD");
                Main.instance.GiveGuns(p, "NewPlayer");
                p.sendMessage("Вы получили свой первый меч!");
                p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                //if(p.getInventory())
            }
            if (e.getCurrentItem().getType().equals((Object)Material.SUGAR_CANE)) {
                if (Levels.getSide(p).equalsIgnoreCase("Sith")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lВы уже играете за Темную сторону!"));
                    p.closeInventory();
                    return;
                }
                if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
                    Main.econ.withdrawPlayer((OfflinePlayer)p, 120000.0);
                    Levels.faction.put(p.getName(), "Sith");
                    p.sendMessage(ChatColor.RED + "Вы примкнули к Темной стороне!");
                    p.sendMessage(ChatColor.RED + "С вашего счета списано 120к");
                    p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                    JediScoreBoard.updateScoreboard(p);
                    p.closeInventory();
                    Levels.Items.put(p.getName(), this.CangeStoron(p, this.CangeStoron(p, Levels.Items.get(p.getName()))));
                    return;
                }
                Levels.faction.put(p.getName(), "Sith");
                p.sendMessage(ChatColor.GREEN + "Вы примкнули к темной стороне!");
                JediScoreBoard.updateScoreboard(p);
                p.closeInventory();
                Levels.SetSaberList(p, "GOLD_SWORD");
                Levels.SetSaber(p, "GOLD_SWORD");
                Main.instance.GiveGuns(p, "NewPlayer");
                p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                p.sendMessage(ChatColor.YELLOW + "Вы получили свой первый меч!");
            }
            if (e.getCurrentItem().getType().equals((Object)Material.LEATHER)) {
                if (Levels.getSide(p).equalsIgnoreCase("Ali")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Вы уже играете за Альянс!"));
                    p.closeInventory();
                    return;
                }
                if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
                    Main.econ.withdrawPlayer((OfflinePlayer)p, 120000.0);
                    Levels.faction.put(p.getName(), "Ali");
                    p.sendMessage(ChatColor.RED + "Вы примкнули к Альянсу!");
                    p.sendMessage(ChatColor.RED + "С вашего счета списано 120к");
                    p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                    JediScoreBoard.updateScoreboard(p);
                    p.closeInventory();
                    Levels.Items.put(p.getName(), this.CangeStoron(p, this.CangeStoron(p, Levels.Items.get(p.getName()))));
                    return;
                }
                if (Levels.getSide(p).equalsIgnoreCase("Sith")) {
                    Main.econ.withdrawPlayer((OfflinePlayer)p, 120000.0);
                    Levels.faction.put(p.getName(), "Ali");
                    p.sendMessage(ChatColor.RED + "Вы примкнули к Альянсу!");
                    p.sendMessage(ChatColor.RED + "С вашего счета списано 120к");
                    p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                    JediScoreBoard.updateScoreboard(p);
                    p.closeInventory();
                    Levels.Items.put(p.getName(), this.CangeStoron(p, this.CangeStoron(p, Levels.Items.get(p.getName()))));
                    return;
                }
                Levels.faction.put(p.getName(), "Ali");
                p.sendMessage(ChatColor.GREEN + "Вы примкнули к Альянсу!");
                JediScoreBoard.updateScoreboard(p);
                p.closeInventory();
                Levels.SetSaberList(p, "GOLD_SWORD");
                Levels.SetSaber(p, "GOLD_SWORD");
                Main.instance.GiveGuns(p, "NewPlayer");
                p.kickPlayer("§bОбновление конфигов,перезаход обязателен!");
                p.sendMessage(ChatColor.YELLOW + "Вы получили свой первый меч!");
            }
        }
    }
    
    @EventHandler
    public void onAttack(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            final Player player = (Player)e.getEntity();
            final Player player2 = (Player)e.getDamager();
            if ((Levels.getSide(player).equalsIgnoreCase("Jedi") || Levels.getSide(player2).equalsIgnoreCase("Sith") || Levels.getSide(player2).equalsIgnoreCase("Ali")) && Levels.getFaction(player).equalsIgnoreCase(Levels.getFaction(player2))) {
                e.setCancelled(true);
            }
            if (Levels.getLevel(player) < 4) {
                player2.sendMessage(ChatColor.RED + "Вы не можете атоковать игроков не достигнувшив/х 4 лвл!");
                e.setCancelled(true);
            }
            if (Levels.getLevel(player2) < 4) {
                player2.sendMessage(ChatColor.RED + "Вы не можете атоковать игроков не достигнувшив/х 4 лвл!");
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Projectile && e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            Player player3 = null;
            final Projectile projectile = (Projectile)e.getDamager();
            if (projectile.getShooter() instanceof Player) {
                player3 = (Player)projectile.getShooter();
            }
            if (Levels.getFaction(player).equalsIgnoreCase(Levels.getFaction(player3))) {
                e.setCancelled(true);
            }
            if (Levels.getLevel(player) < 4) {
                player.sendTitle(null,ChatColor.YELLOW + "Вы не атоковать игроков не достигнувших 4 лвл!");
                e.setCancelled(true);
            }
            if (Levels.getLevel(player3) < 4) {
                player3.sendTitle(null,ChatColor.YELLOW + "Вы не можете атоковать игроков не достигнув 4 лвл!");
                e.setCancelled(true);
            }
        }
    }
}
