package SkyCrafting.Main;

import org.bukkit.plugin.java.*;

import net.milkbowl.vault.economy.*;
import com.sk89q.worldguard.bukkit.*;
import org.bukkit.configuration.file.*;
import java.io.*;
import java.util.logging.*;
import org.bukkit.configuration.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import SkyCrafting.Main.Entyti.quest.*;
import SkyCrafting.Main.Skills.*;
import SkyCrafting.Main.Commands.*;
import com.shampaggon.crackshot.*;
import com.sk89q.worldguard.protection.regions.*;


import com.sk89q.worldguard.protection.managers.*;
import org.bukkit.util.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import SkyCrafting.Main.Entyti.*;
import org.bukkit.command.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class Main extends JavaPlugin
{
	
	static Logger log = Logger.getLogger("[LWGET] ");
	 
    public static Economy econ;
    File levels;
    FileConfiguration levelsConfig;
    @SuppressWarnings("unused")
	private boolean useHolographicDisplays;
    public static Main instance;
    static int ScoreboardUpdater;
    File levels1;
    File levels2;
    static FileConfiguration levelsConfig1;

	public static FileConfiguration c;
    public WorldGuardPlugin wg;
    String Jedi;
    String Sith;
    
    static {
        Main.econ = null;
    }
    
    
    
    public Main() {
        this.levels = new File(this.getDataFolder() + "/levels.yml");
        this.levelsConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.levels);
        this.levels1 = new File(this.getDataFolder() + File.separator + "spawners.yml");
        this.levels2 = new File(this.getDataFolder() + File.separator + "locs.yml");
        this.wg = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
        this.Jedi = "Вы не ситх!";
        this.Sith = "Вы не джедай!";
    }
    
    @SuppressWarnings("rawtypes")
	private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        Main.econ = (Economy)rsp.getProvider();
        return Main.econ != null;
    }
    
    public static void saveCustomYml(final FileConfiguration ymlConfig, final File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void Loadconfig() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            Levels.levels.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level"));
            Levels.kills.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".kills"));
            Levels.xp.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".xp"));
            Levels.crystal.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".crystal"));
            Levels.deaths.put(p.getName(), Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".deaths"));
            Levels.Saber.put(p.getName(), Main.instance.levelsConfig.getString(String.valueOf(p.getName()) + ".Saber"));
            Levels.Items.put(p.getName(), (ArrayList<String>)Main.instance.levelsConfig.getStringList(String.valueOf(p.getName()) + ".ListItems"));
            Levels.Cap.put(p.getName(), Main.instance.levelsConfig.getString(String.valueOf(p.getName()) + ".Cap"));
          
            if (Main.instance.levelsConfig.contains(String.valueOf(String.valueOf(p.getName())) + ".faction")) {
                Levels.faction.put(p.getName(), Main.instance.levelsConfig.getString(String.valueOf(String.valueOf(p.getName())) + ".faction"));
            }
        }
    }
    public void onEnable() {
        
        Main.instance = this;
        if (!this.setupEconomy()) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        Loadconfig();
        //this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.scoreboardUpdater();
        //Listeners
        Bukkit.getPluginManager().registerEvents(new JoinEVENT(),this);
        Bukkit.getPluginManager().registerEvents((Listener)new Bosslistener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Factions(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Levels(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Staff(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Comands2(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Kamino(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Mustafar(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Bossinfo(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new questmenu(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new RandomTeleport(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new questinventory(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Zapret(), (Plugin)this);
        //Skills
        Bukkit.getPluginManager().registerEvents((Listener)new Shitni(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Vostonov(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new VspyshkaSilu(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ShtormMolnii(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Bosdyjenai(), (Plugin)this);        
        Bukkit.getPluginManager().registerEvents((Listener)new Ocep(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Jumping(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new UpgradeJump(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Lightning(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ForcePuch(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Drainlife(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Telekines(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Zalick(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new SlomatOrjyi(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Test(), (Plugin)this);
        //Commands
        this.getCommand("level").setExecutor((CommandExecutor)new Levels());
        this.getCommand("loh").setExecutor((CommandExecutor)new Test());
        this.getCommand("quests").setExecutor((CommandExecutor)new questinventory());
        this.getCommand("status").setExecutor((CommandExecutor)new stats());
        this.getCommand("jcinfo").setExecutor((CommandExecutor)new SmartSaber());
        this.getCommand("lwget").setExecutor((CommandExecutor)new Setlvl());
        this.getCommand("naboo").setExecutor((CommandExecutor)new Naboo());
        this.getCommand("endor").setExecutor((CommandExecutor)new Endor());
        this.getCommand("hot").setExecutor((CommandExecutor)new Hoth());
        this.getCommand("pvp").setExecutor((CommandExecutor)new Pvp());
        this.getCommand("arena").setExecutor((CommandExecutor)new ArenaPlanet());
        this.getCommand("kamino").setExecutor((CommandExecutor)new Kamino());
        this.getCommand("mustafar").setExecutor((CommandExecutor)new Mustafar());
        this.getCommand("lotal").setExecutor((CommandExecutor)new Lotal());
        this.getCommand("hatcape").setExecutor((CommandExecutor)new Comands2());
        this.getCommand("space").setExecutor((CommandExecutor)new Space());
        this.getCommand("base").setExecutor((CommandExecutor)new Factions());
        this.getCommand("bossinfo").setExecutor((CommandExecutor)new Bossinfo());
        this.getCommand("side").setExecutor((CommandExecutor)new Factions());
        this.getCommand("sc").setExecutor((CommandExecutor)new Staff());
        this.getCommand("ilum").setExecutor((CommandExecutor)new planetMINE());
        this.getCommand("gift").setExecutor((CommandExecutor)new GiftCommand());
        this.getCommand("score").setExecutor((CommandExecutor)new score());
        this.getCommand("qvestskill").setExecutor((CommandExecutor)new questmenu());
        this.getCommand("mudak").setExecutor((CommandExecutor)new mudak());
        this.getCommand("rtp").setExecutor((CommandExecutor)new RandomTeleport());
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 781.0, 31.0, 1078.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 893.0, 29.0, 1239.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 852.0, 29.0, 1460.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1072.0, 31.0, 1499.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1108.0, 45.0, 1413.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1114.0, 29.0, 1281.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1210.0, 31.0, 1215.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1299.0, 29.0, 1117.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1253.0, 29.0, 1284.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1387.0, 29.0, 1273.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1466.0, 29.0, 1286.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1705.0, 33.0, 1366.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1762.0, 31.0, 1656.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1702.0, 29.0, 1749.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 1510.0, 30.0, 1860.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 79.0, 28.0, 1885.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -161.0, 31.0, 1769.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -512.0, 30.0, 1833.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -645.0, 38.0, 1699.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -641.0, 31.0, 1466.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -591.0, 30.0, 1216.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -600.0, 30.0, 1101.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -545.0, 30.0, 1014.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -573.0, 30.0, 929.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -581.0, 30.0, 877.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -681.0, 30.0, 877.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 535.0, 31.0, 838.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 499.0, 31.0, 737.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 461.0, 16.0, -378.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 324.0, 16.0, -346.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 253.0, 20.0, -223.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 67.0, 16.0, -319.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -57.0, 41.0, -291.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -79.0, 20.0, -147.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -122.0, 13.0, -57.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -360.0, 16.0, -44.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 302.0, 30.0, 1132.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 32.0, 30.0, 1109.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -151.0, 30.0, 1010.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -130.0, 30.0, 802.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -254.0, 30.0, 545.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 594.0, 28.0, 1173.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 439.0, 30.0, 1168.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 297.0, 29.0, 1271.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), 64.0, 29.0, 1116.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -47.0, 29.0, 996.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -90.0, 41.0, 793.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -303.0, 29.0, 781.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -507.0, 29.0, 761.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -658.0, 29.0, 964.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -620.0, 29.0, 1104.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -436.0, 29.0, 1218.0));
        RandomTeleport.locs.add(new Location(Bukkit.getWorld("world"), -283.0, 29.0, 1113.0));
        Main.levelsConfig1 = (FileConfiguration)YamlConfiguration.loadConfiguration(this.levels1);
        loadSpawners();
        new SpawnerUpdater().runTaskTimer((Plugin)this, 20L, 20L);
        Main.saveCustomYml(Main.levelsConfig1, Main.instance.levels1);
        Main.c = this.getConfig();
    }
    
    public void onDisable() {
        this.saveConfig();
        for (final Player p : Bukkit.getOnlinePlayers()) {
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".level", (Object)Levels.levels.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".kills", (Object)Levels.kills.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".xp", (Object)Levels.xp.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".crystal", (Object)Levels.crystal.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".deaths", (Object)Levels.deaths.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".Saber", (Object)Levels.Saber.get(p.getName()));
            Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".ListItems", (Object)Levels.Items.get(p.getName()));
          
            if (Levels.faction.containsKey(p.getName())) {
                Main.instance.levelsConfig.set(String.valueOf(String.valueOf(p.getName())) + ".faction", (Object)Levels.faction.get(p.getName()));
            }
            Levels.levels.remove(p.getName());
            Levels.kills.remove(p.getName());
            Levels.xp.remove(p.getName());
            Levels.crystal.remove(p.getName());
            Levels.deaths.remove(p.getName());
            Levels.faction.remove(p.getName());
            Levels.Saber.remove(p.getName());
            Levels.Items.remove(p.getName());
        }
        Main.saveCustomYml(this.levelsConfig, this.levels);
        for (final World w : Bukkit.getWorlds()) {
            for (final LivingEntity e : w.getLivingEntities()) {
                if (!(e instanceof Player) && e.getCustomName() != "") {
                    e.remove();
                }
            }
        }
        Main.saveCustomYml(Main.levelsConfig1, Main.instance.levels1);
        for (final Spawner cSpawner : Spawner.spawners.values()) {
            if (cSpawner.getCurrent() == null) {
                continue;
            }
            cSpawner.getCurrent().getBukkitEntity().remove();
        }
    }
    
    public void GiveGuns(final Player p, final String name) {
        final CSUtility crackshot = new CSUtility();
        final PlayerInventory playerInv = p.getInventory();
        final ItemStack gun = crackshot.generateWeapon(name);
        playerInv.addItem(gun);  
        p.sendMessage("§eВам был выдан световой меч");
        p.sendMessage("§aПроверьте свои инвентарь!");
        p.updateInventory();
    }
    
    public boolean CheckregionPlaeyr(final Player p) {
    	 final String q = "§cВы не можете использовать это на спавне!";
         final RegionManager manager = this.wg.getRegionManager(p.getLocation().getWorld());
        for (final ProtectedRegion region : manager.getApplicableRegions(p.getLocation())) {
            if (region.getId().equalsIgnoreCase("Spawn")) {
                p.sendTitle("",q);
                return true;
            }
            if (region.getId().equalsIgnoreCase("Spawn1")) {
                p.sendTitle("",q);
                return true;
            }
            if (region.getId().equalsIgnoreCase("Spawn2")) {
                p.sendTitle("",q);
                return true;
            }
        }
        return false;
    }
    
    public boolean CheckregionLivingEntity(final Entity entity) {
        final RegionManager manager = this.wg.getRegionManager(entity.getLocation().getWorld());
        for (final ProtectedRegion region : manager.getApplicableRegions(entity.getLocation())) {
            if (region.getId().equalsIgnoreCase("Spawn")) {
                return true;
            }
            if (region.getId().equalsIgnoreCase("Spawn1")) {
                return true;
            }
            if (region.getId().equalsIgnoreCase("Spawn2")) {
                return true;
            }
        }
        return false;
    }
    
    public LivingEntity rayTraceEntity(final Player player, final int range) {
        final BlockIterator iterator = new BlockIterator(player.getWorld(), player.getEyeLocation().toVector(), player.getEyeLocation().getDirection(), 0.0, range);
        Chunk chunk = null;
        Entity[] entities = null;
        while (iterator.hasNext()) {
            final Location l = iterator.next().getLocation();
            if (chunk != l.getChunk()) {
                chunk = l.getChunk();
                entities = chunk.getEntities();
            }
            if (entities != null && entities.length > 0) {
                final Entity[] arr$ = entities;
                for (int len$ = entities.length, i$ = 0; i$ < len$; ++i$) {
                    final Entity entity = arr$[i$];
                    if (entity != player && entity instanceof LivingEntity && entity.getType() != EntityType.SQUID && l.getWorld() == entity.getLocation().getWorld() && l.distance(entity.getLocation()) < 1.5) {
                        return (LivingEntity)entity;
                    }
                }
            }
        }
        return null;
    }
    
    public LivingEntity rayTraceEntity(final CraftEntity bukkitEntity, final int range) {
        final BlockIterator iterator = new BlockIterator(bukkitEntity.getWorld(), ((LivingEntity)bukkitEntity).getEyeLocation().toVector(), ((LivingEntity)bukkitEntity).getEyeLocation().getDirection(), 0.0, range);
        Chunk chunk = null;
        Entity[] entities = null;
        while (iterator.hasNext()) {
            final Location l = iterator.next().getLocation();
            if (chunk != l.getChunk()) {
                chunk = l.getChunk();
                entities = chunk.getEntities();
            }
            if (entities != null && entities.length > 0) {
                final Entity[] arr$ = entities;
                for (int len$ = entities.length, i$ = 0; i$ < len$; ++i$) {
                    final Entity entity = arr$[i$];
                    if (entity != bukkitEntity && entity instanceof Player && entity.getType() != EntityType.SQUID && l.getWorld() == entity.getLocation().getWorld() && l.distance(entity.getLocation()) < 1.5) {
                        return (LivingEntity)entity;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean chekJedi(final Player p) {
        if (Levels.getFaction(p).equalsIgnoreCase("Jedi")) {
            p.sendMessage(this.Jedi);
            return true;
        }
        return false;
    }
    
    public boolean chekSith(final Player p) {
        if (Levels.getFaction(p).equalsIgnoreCase("Sith")) {
            p.sendMessage(this.Sith);
            return true;
        }
        return false;
    }
    
    public boolean mesagges(final Player p, final int a) {
        if (!Levels.getFaction(p).equalsIgnoreCase("Jedi") && !Levels.getFaction(p).equalsIgnoreCase("Sith") && !Levels.getFaction(p).equalsIgnoreCase("Ali")) {
            p.sendMessage("Выберите фракцию, что бы использовать эту способность!");
            return true;
        }
        if (Levels.getLevel(p) < a) {
            p.sendMessage("У вас нет " + a + " уровня");
            return true;
        }
        return false;
    }
    
    public boolean checEntytiJedi(final LivingEntity p) {
        return p instanceof Player && Levels.getFaction1((Entity)p).equalsIgnoreCase("Jedi");
    }
    
    public boolean checEntytiSith(final LivingEntity p) {
        return p instanceof Player && Levels.getFaction1((Entity)p).equalsIgnoreCase("Sith");
    }
    
    private static void loadSpawners() {
        for (final String cSpawn : Main.levelsConfig1.getKeys(false)) {
            final ConfigurationSection path = Main.levelsConfig1.getConfigurationSection(cSpawn);
            final EntityTypes type = EntityTypes.valueOf(path.getString("type"));
            final double x = path.getDouble("x");
            final double y = path.getDouble("y");
            final double z = path.getDouble("z");
            final int interval = path.getInt("interval");
            final World world = Bukkit.getWorld(path.getString("world"));
            new Spawner(new Location(world, x, y, z), type, interval).update();
        }
        Main.instance.getLogger().log(Level.INFO, "Loaded " + Spawner.spawners.size() + " mob spawners");
    }
    
    boolean tryParseInt(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }
    
    private void sendAllTypes(final CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "Доступные типы:");
        for (final EntityTypes cType : EntityTypes.values()) {
            sender.sendMessage("§d" + String.valueOf(cType) + " - " + cType.getName());
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
            if (sender.getName().equals("SkyCrafting_")){
                if (command.getName().equalsIgnoreCase("addspawner")) {
                    if (args.length <= 0) {
                        sender.sendMessage(ChatColor.RED + "Вы не ввели имя точки спавна.");
                        sender.sendMessage(ChatColor.RED + "Добавить точку /addspawner [название точки] [название босса] [интервал спавна].");
                        return true;
                    }
                    if (args.length <= 1) {
                        sender.sendMessage(ChatColor.RED + "Вы не ввели тип точки спавна.");
                        this.sendAllTypes(sender);
                        return true;
                    }
                    if (args.length <= 2) {
                        sender.sendMessage(ChatColor.RED + "Вы не ввели интервал.");
                        return true;
                    }
                    EntityTypes type;
                    try {
                        type = EntityTypes.valueOf(args[1]);
                    }
                    catch (IllegalArgumentException e4) {
                        sender.sendMessage(ChatColor.RED + "Неизвестный тип!");
                        this.sendAllTypes(sender);
                        return true;
                    }
                    if (!this.tryParseInt(args[2])) {
                        sender.sendMessage(ChatColor.RED + "Интервал введен неверно!");
                        return true;
                    }
                    final int interval = Integer.parseInt(args[2]);
                    final String path = args[0].toLowerCase();
                    final Location loc = ((Player)sender).getLocation();
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".type", (Object)String.valueOf(type));
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".interval", (Object)interval);
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".x", (Object)loc.getX());
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".y", (Object)loc.getY());
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".z", (Object)loc.getZ());
                    Main.levelsConfig1.set(String.valueOf(String.valueOf(path)) + ".world", (Object)loc.getWorld().getName());
                    Main.saveCustomYml(Main.levelsConfig1, Main.instance.levels1);
                    final Spawner spawner = new Spawner(loc, type, interval);
                    spawner.update();
                    sender.sendMessage(ChatColor.GREEN + "Точка спавна успешно добавлена.");
                    return true;
                }
                else if (command.getName().equalsIgnoreCase("resetbosses")) {
                    for (final Spawner cSpawner : Spawner.spawners.values()) {
                        cSpawner.reset();
                    }
                    sender.sendMessage(ChatColor.GREEN + "Все монстры были сброшены!");
                    return true;
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Ты кто ?");
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Ты кто ?");
            return true;
        } 
    public ItemStack createItem(final String r, final Material s) {
        final ItemStack glass = new ItemStack(s);
        final ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(r);
        glass.setItemMeta(meta);
        return glass;
    }
    
    public ItemStack createItem(final String r, final Material s, final int value) {
        final ItemStack glass = new ItemStack(s, value);
        final ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(r);
        glass.setItemMeta(meta);
        return glass;
    }
    
    public void scoreboardUpdater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    JediScoreBoard.UpdateList(p);
                    JediScoreBoard.updateScoreboard(p);
                }
            }
        }, 1L, 250L);
    }
}
