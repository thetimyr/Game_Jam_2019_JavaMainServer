package SkyCrafting.Main;

import org.bukkit.entity.*;
import SkyCrafting.Main.Entyti.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.command.*;
import org.bukkit.event.inventory.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;

public class Bossinfo implements Listener, CommandExecutor
{
    static HashMap<Integer, Inventory> invs;
    String[] r;
    static WeakHashMap<Player, Long> cols;
    int[] smartSlots;
    
    static {
        Bossinfo.invs = new HashMap<Integer, Inventory>();
        Bossinfo.cols = new WeakHashMap<Player, Long>();
    }
    
    public Bossinfo() {
        this.r = new String[] { "§f§lБоссы планеты §c§lТатуин", "§f§lБоссы §7§lКосмоса", "§f§lБоссы планеты §a§lНабу", "§f§lБоссы планеты §7§lКамино", "§f§lБоссы планеты §4§lМустафар", "§f§lБоссы планеты §7§lХот", "§f§lБоссы планеты §a§lЕндор" };
        this.smartSlots = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
    
    @SuppressWarnings("rawtypes")
	private ItemStack Lore(final ItemStack item, final String name, final EntityTypes type, final int Hp, final int x, final int y, final int z) {
        final ArrayList<String> Lore = new ArrayList<String>();
        Lore.add("§7Хп:§a " + Hp);
        Lore.add("§a-----§7Координаты§a-----");
        Lore.add("§7x: §a " + x);
        Lore.add("§7y: §a " + y);
        Lore.add("§7z: §a " + z);
        new SmartMenu(type, Lore);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List)Lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (!command.getName().equalsIgnoreCase("bossinfo")) {
            return true;
        }
        final Inventory q0 = this.CreateInventory(this.r[0]);
        final ArrayList<String> list1 = new ArrayList<String>();
        list1.add("§aИщите по всей карте!");
        q0.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.SAPLING), "§6§lАйд-жи ", EntityTypes.Adge, 500, 1843, 8, 896));
        q0.setItem(this.smartSlots[11], this.Lore(new ItemStack(Material.IRON_DOOR), "§6§lТемный слизень ", EntityTypes.TemnuySlizen, 500, 733, 31, 1052));
        q0.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.IRON_SWORD), "§b§lЙода ", EntityTypes.Yoda, 500, 655, 30, 637));
        q0.setItem(this.smartSlots[2], this.Lore(new ItemStack(Material.CARPET), "§f§lШтурмовик ", EntityTypes.Shtorm, 500, -718, 81, 1323));
        q0.setItem(this.smartSlots[3], this.Lore(new ItemStack(Material.SLIME_BLOCK), "§4§lДжаба ", EntityTypes.Jabba, 500, 1752, 78, 1007));
       // q0.setItem(this.smartSlots[4], this.Lore(new ItemStack(Material.IRON_SWORD), "§4§lОби-ван Кеноби ", EntityTypes.Obivan, 500, -139, 28, 1574));
        q0.setItem(this.smartSlots[5], this.Lore(new ItemStack(Material.IRON_SWORD), "§4§lРей ", EntityTypes.Rey, 500, 297, 29, 1305));
        q0.setItem(this.smartSlots[4], this.Lore(new ItemStack(Material.DIAMOND_SWORD), "§4§lОтресшийся №1", EntityTypes.ZOMBIEOTRECK, 300, 6, 30, 732));
        q0.setItem(this.smartSlots[5], this.Lore(new ItemStack(Material.DIAMOND_SWORD), "§4§lОтресшийся №2", EntityTypes.ZOMBIEOTRECK2, 300, -135, 30, 930));
        q0.setItem(this.smartSlots[6], this.Lore(new ItemStack(Material.DIAMOND_SWORD), "§4§lОтресшийся №3", EntityTypes.ZOMBIEOTRECK3, 300, -5, 32, 656));
        q0.setItem(this.smartSlots[8], this.Lore(new ItemStack(Material.WEB), "§6Босс пауков ", EntityTypes.BOSSPAVUK, 800, 503, 8, 1224));
        q0.setItem(this.smartSlots[9], this.CreateItem(Material.LEATHER_HELMET, "§4§lПустынники ", list1));
        q0.setItem(this.smartSlots[10], this.CreateItem(Material.IRON_CHESTPLATE, "§4§lОхраники Джаббы ", list1));
        Bossinfo.invs.put(1, q0);
        final Inventory q2 = this.CreateInventory(this.r[1]);
        q2.setItem(this.smartSlots[0], this.CreateItem(Material.IRON_HELMET, "§4§lКлоны ", list1));
       // q2.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.NETHER_STAR), "§4§lГенерал Гривус", EntityTypes.R2D2, 1500, -593, 130, -5));
       // q2.setItem(this.smartSlots[2], this.Lore(new ItemStack(Material.BONE), "§4§lДарт Вейдер", EntityTypes.Dart, 1000, -593, 131, -2));
        Bossinfo.invs.put(2, q2);
        final Inventory q3 = this.CreateInventory(this.r[2]);
        q3.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.SLIME_BALL), "§4§lСлизень", EntityTypes.Slime, 500, 1279, 80, 1434));
        q3.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.STONE_SWORD), "§4§lДарт Мол", EntityTypes.Mol, 1000, 952, 65, 710));
       // q3.setItem(this.smartSlots[2], this.Lore(new ItemStack(Material.LEASH), "§4§lМейс Винду", EntityTypes.Vindy, 600, 1035, 59, 1390));
        q3.setItem(this.smartSlots[3], this.CreateItem(Material.IRON_HELMET, "§4§lКлоны ", list1));
        Bossinfo.invs.put(3, q3);
        final Inventory q4 = this.CreateInventory(this.r[3]);
        q4.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.BONE), "§4§lМастер", EntityTypes.Master, 650, 104, 115, 640));
        q4.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.CARPET, 1, (short)7), "§4§lБоба Фетт", EntityTypes.BobaFett, 1500, 212, 100, 748));
        q4.setItem(this.smartSlots[2], this.Lore(new ItemStack(Material.CARPET, 1, (short)9), "§4§lУсиленный Повстанец", EntityTypes.Povstanes, 900, 212, 100, 532));
        q4.setItem(this.smartSlots[3], this.CreateItem(Material.IRON_HELMET, "§4§lКлоны ", list1));
        Bossinfo.invs.put(4, q4);
        final Inventory q5 = this.CreateInventory(this.r[4]);
       // q5.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.GHAST_TEAR), "§5§lТемный Рыцарь", EntityTypes.Lucar, 1100, 725, 96, 135));
        q5.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.SULPHUR), "§4§lЛавовый куб", EntityTypes.Magma, 500, 51, 31, 408));
        q5.setItem(this.smartSlots[2], this.Lore(new ItemStack(Material.ROTTEN_FLESH), "§4§lЗомби-гигант №1", EntityTypes.Shudow, 1500, 107, 86, 121));
        q5.setItem(this.smartSlots[3], this.Lore(new ItemStack(Material.ROTTEN_FLESH), "§4§lЗомби-гигант №2", EntityTypes.Shudow1, 1500, 300, 13, 454));
        Bossinfo.invs.put(5, q5);
        final Inventory q6 = this.CreateInventory(this.r[5]);
        q6.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.SNOW_BLOCK), "§f§lЙети", EntityTypes.Golem, 810, -295, 114, -926));
       // q6.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.DIAMOND_HELMET), "§1§lОхотник за головами", EntityTypes.O, 500, -547, 74, -892));
        q6.setItem(this.smartSlots[2], this.CreateItem(Material.IRON_SPADE, "§4Повстанецы ", list1));
        Bossinfo.invs.put(6, q6);
        final Inventory q7 = this.CreateInventory(this.r[6]);
       // q7.setItem(this.smartSlots[0], this.Lore(new ItemStack(Material.NETHER_STAR), "§5§lПалпатин", EntityTypes.P1, 2048, 98, 111, 127));
        //q7.setItem(this.smartSlots[1], this.Lore(new ItemStack(Material.COAL), "§6§lЧуви", EntityTypes.Chubaka, 500, 201, 68, 178));
        q7.setItem(this.smartSlots[2], this.CreateItem(Material.IRON_SPADE, "§4Повстанецы ", list1));
        q7.setItem(this.smartSlots[3], this.CreateItem(Material.STICK, "§4Эвоки ", list1));
        Bossinfo.invs.put(7, q7);
        if (p.getWorld().getName().equals("world")) {
            p.openInventory((Inventory)Bossinfo.invs.get(1));
            return true;
        }
        if (p.getWorld().getName().equals("World1")) {
            p.openInventory((Inventory)Bossinfo.invs.get(1));
            return true;
        }
        if (p.getWorld().getName().equals("World_the_end") || p.getWorld().getName().equals("Space")) {
            p.openInventory((Inventory)Bossinfo.invs.get(2));
            return true;
        }
        if (p.getWorld().getName().equals("Space")) {
            p.openInventory((Inventory)Bossinfo.invs.get(2));
            return true;
        }
        if (p.getWorld().getName().equals("starwars")) {
            p.openInventory((Inventory)Bossinfo.invs.get(2));
            return true;
        }
        if (p.getWorld().getName().equals("Naboo")) {
            p.openInventory((Inventory)Bossinfo.invs.get(3));
            return true;
        }
        if (p.getWorld().getName().equals("Kamino")) {
            p.openInventory((Inventory)Bossinfo.invs.get(4));
            return true;
        }
        if (p.getWorld().getName().equals("Mustafar")) {
            p.openInventory((Inventory)Bossinfo.invs.get(5));
            return true;
        }
        if (p.getWorld().getName().equals("Hot")) {
            p.openInventory((Inventory)Bossinfo.invs.get(6));
            return true;
        }
        if (p.getWorld().getName().equals("bespin3")) {
            p.openInventory((Inventory)Bossinfo.invs.get(7));
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6На планете на которой вы находитесь нет боссов."));
        return true;
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Bossinfo.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cВы сможете повторить действия через &f[" + s / 1000L + "]" + " &cсекунд."));
            return false;
        }
        Bossinfo.cols.put(user, time);
        return true;
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[0])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(2));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.SLIME_BLOCK)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса §4§lДжаба Хат&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.CARPET)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &f&lШтурмовик&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_SWORD)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &b&lЙоду&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.SAPLING)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &6&lАйд-жи&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.DIAMOND_SWORD) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lОтресшийся №1")) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &4&lОтресшийся №1&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.DIAMOND_SWORD) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lОтресшийся №2")) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &4&lОтресшийся №2&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.DIAMOND_SWORD) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lОтресшийся №3")) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &4&lОтресшийся №3&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.LEATHER_HELMET)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Пустынников&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_CHESTPLATE) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Охраников Джабы&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[1])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(3));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(1));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_HELMET) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Клонов&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[2])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(4));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(2));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.SLIME_BALL)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aотправится в поход на босса &4&lСлизень&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STONE_SWORD)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lДарт Мол&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_HELMET) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Клонов&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[3])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(5));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(3));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.BONE)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lМастера&a."));
                }
                }
                else if (e.getCurrentItem().getType().equals((Object)Material.DIAMOND_CHESTPLATE)) {
                    if (check(p, 60000L)) {
                        Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lБобафетта&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_HELMET) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Клонов&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[4])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(6));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(4));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.GHAST_TEAR)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &5&lТемный Рыцарь&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.SULPHUR)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lЛавовый куб&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.ROTTEN_FLESH) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lЗомби-гигант №1")) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lЗомби-гигант №1&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.ROTTEN_FLESH) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lЗомби-гигант №2") && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &4&lЗомби-гигант №2&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[5])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(5));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 5) {
                p.openInventory((Inventory)Bossinfo.invs.get(7));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.SNOW_BLOCK)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &f&lЙети&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.DIAMOND_HELMET)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &1&lОхотник за головами&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_SPADE) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Повстанцах&a."));
            }
        }
        else if (e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(this.r[6])) {
            final Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getType().equals((Object)Material.STAINED_GLASS) && e.getCurrentItem().getDurability() == 14) {
                p.openInventory((Inventory)Bossinfo.invs.get(6));
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.NETHER_STAR)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &5&lПалпатина&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.IRON_SPADE)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Повстанцах&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.STICK)) {
                if (check(p, 60000L)) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aРешил высвободить свой гнев на &4Эвоках&a."));
                }
            }
            else if (e.getCurrentItem().getType().equals((Object)Material.COAL) && check(p, 60000L)) {
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes('&', "&c&l" + p.getName() + " &aрешил рискнуть жизнью и отправится в поход на босса &5&lЧуви&a."));
            }
        }
    }
    
    public void cageInventory(final Inventory inventory, final ItemStack b) {
        for (int j = 0; j < 9; ++j) {
            inventory.setItem(j, b);
        }
        for (int k = inventory.getSize() - 9; k < inventory.getSize(); ++k) {
            inventory.setItem(k, b);
        }
        final int n = inventory.getSize() / 9 - 2;
        if (n < 1) {
            return;
        }
        for (int l = 9; l < 9 * n + 1; l += 9) {
            inventory.setItem(l, b);
        }
        for (int n2 = 17; n2 < 9 * (n + 1); n2 += 9) {
            inventory.setItem(n2, b);
        }
    }
    
    @SuppressWarnings("rawtypes")
	public ItemStack CreateItem(final Material mat, final String name, final ArrayList<String> array) {
        final ItemStack item = new ItemStack(mat);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List)array);
        item.setItemMeta(meta);
        return item;
    }
    
    public ItemStack CreateItem(final Material mat, final String name, final int Durability) {
        final ItemStack item = new ItemStack(mat);
        item.setDurability((short)Durability);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
    
    public Inventory CreateInventory(final String mainName) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, mainName);
        this.cageInventory(inventory, this.CreateItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", 7));
        final ItemStack previous = this.CreateItem(Material.STAINED_GLASS, ChatColor.RED + "Предыдущая страница", 14);
        if (inventory.getName().equalsIgnoreCase(this.r[0])) {
            inventory.setItem(18, this.CreateItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", 7));
            inventory.setItem(27, this.CreateItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", 7));
        }
        else {
            inventory.setItem(18, previous);
            inventory.setItem(27, previous);
        }
        final ItemStack next = this.CreateItem(Material.STAINED_GLASS, ChatColor.GREEN + "Следущая страница", 5);
        if (inventory.getName().equalsIgnoreCase(this.r[6])) {
            inventory.setItem(26, this.CreateItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", 7));
            inventory.setItem(35, this.CreateItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GRAY + " ", 7));
        }
        else {
            inventory.setItem(26, next);
            inventory.setItem(35, next);
        }
        return inventory;
    }
}
