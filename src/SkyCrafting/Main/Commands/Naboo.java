package SkyCrafting.Main.Commands;

import org.bukkit.event.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

public class Naboo implements Listener, CommandExecutor
{
    static boolean q1;
    ArrayList<Player> nodrop;
    
    static {
        Naboo.q1 = true;
    }
    
    public Naboo() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 11) {
            p.sendTitle("§cУ вас нет 11 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("naboo") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedi1.world")), 
            		(double)Main.instance.getConfig().getInt("jedi1.x"), (double)Main.instance.getConfig().getInt("jedi1.y"), 
            		(double)Main.instance.getConfig().getInt("jedi1.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sith5.world")), 
            		(double)Main.instance.getConfig().getInt("sith5.x"), 
            		(double)Main.instance.getConfig().getInt("sith5.y"), 
            		(double)Main.instance.getConfig().getInt("sith5.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("ali5.world")), 
            		(double)Main.instance.getConfig().getInt("ali5.x"), 
            		(double)Main.instance.getConfig().getInt("ali5.y"), 
            		(double)Main.instance.getConfig().getInt("ali5.z"));
            
            ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc, "Набу");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
    
    public static void ChecPoint(final Player p, final ArrayList<Player> q, final Location Jedi, final Location Sith, final Location Ali, final String text) {
        if (Levels.faction.get(p.getName()).equalsIgnoreCase("Jedi")) {
            p.teleport(Jedi);
            p.sendMessage(ChatColor.DARK_AQUA + "Вы успешно телепортированы на планету " + text + "!");
            Naboo.q1 = true;
            return;
        }
        if (Levels.faction.get(p.getName()).equalsIgnoreCase("Ali")) {
            p.teleport(Ali);
            p.sendMessage(ChatColor.DARK_AQUA + "Вы успешно телепортированы на планету " + text + "!");
            return;
        }
        if (Levels.faction.get(p.getName()).equalsIgnoreCase("Sith")) {
            p.teleport(Sith);
            p.sendMessage(ChatColor.DARK_RED + "Вы успешно телепортированы на планету " + text + "!");
            return;
        }
    }
}
