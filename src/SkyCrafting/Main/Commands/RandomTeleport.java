package SkyCrafting.Main.Commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.potion.*;

import SkyCrafting.Main.Main;

import org.bukkit.*;
import java.util.*;

public class RandomTeleport implements Listener,CommandExecutor{
	
    private static final HashMap<String, Long> cd;
    public static List<Location> locs;
    
    static {
        cd = new HashMap<String, Long>();
        RandomTeleport.locs = new ArrayList<Location>();
    }
    
    public RandomTeleport() {
    	super();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("rtp")) {
            if (args.length == 0) {
                if (RandomTeleport.cd.get(p.getName().toLowerCase()) == null || RandomTeleport.cd.get(sender.getName().toLowerCase()) + 20000L < System.currentTimeMillis()) {
                    if (RandomTeleport.locs.size() == 0) {
                        p.sendTitle(null, "&4Локации не найдены! Сообщите skycrafting_!");
                        return true;
                    }
                    if (p.getWorld().getName().equalsIgnoreCase("world")) {
                    	RandomTeleport.cd.put(p.getName().toLowerCase(), System.currentTimeMillis());
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0, true));
                        TeleportTo.teleport(p, RandomTeleport.locs.get(new Random().nextInt(RandomTeleport.locs.size())), 3);
                        p.sendMessage(ChatColor.RED + "Вжух!");
                        return true;
                    }
                    p.sendTitle(null,  "&4В этом мире рандомная телепортация запрещена!");
                    return true;
                }
                else if (RandomTeleport.cd.containsKey(p.getName().toLowerCase())) {
                    final int f = (int)(System.currentTimeMillis() - RandomTeleport.cd.get(p.getName().toLowerCase()));
                    final int v = 20 - f / 1000;
                    p.sendTitle(null,  ChatColor.GOLD + "Вы не можете телепортироваться так часто, подождите " + v + " секунд.");
                    return true;
                }
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("add") && p.hasPermission("StarWars.admin") && p.getWorld().getName().equalsIgnoreCase("World")) {
                for (int i = 0; i < 100; ++i) {
                    if (!Main.c.contains("random-locs." + i)) {
                        Main.c.set("random-locs." + i, (Object)(String.valueOf(p.getLocation().getBlockX()) + "|" + p.getLocation().getBlockY() + "|" + p.getLocation().getBlockZ()));
                        Main.instance.saveConfig();
                        p.sendMessage("Успешно!");
                        RandomTeleport.locs.add(p.getLocation());
                        Main.instance.saveConfig();
                        break;
                    }
                }
            }
        }
        return true;
        
    } 

}
