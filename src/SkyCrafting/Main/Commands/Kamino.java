package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Kamino implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Kamino() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 15) {
        	p.sendTitle("§cУ вас нет 15 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("kamino") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedi11.world")), 
            		(double)Main.instance.getConfig().getInt("jedi11.x"), 
            		(double)Main.instance.getConfig().getInt("jedi11.y"), 
            		(double)Main.instance.getConfig().getInt("jedi11.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sith51.world")), 
            		(double)Main.instance.getConfig().getInt("sith51.x"), 
            		(double)Main.instance.getConfig().getInt("sith51.y"), 
            		(double)Main.instance.getConfig().getInt("sith51.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("ali51.world")), 
            		(double)Main.instance.getConfig().getInt("ali51.x"), 
            		(double)Main.instance.getConfig().getInt("ali51.y"), 
            		(double)Main.instance.getConfig().getInt("ali51.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc, "Камино");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
