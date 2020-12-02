package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Mustafar implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Mustafar() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 19) {
            p.sendTitle("§cУ вас нет 19 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("mustafar") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedi111.world")), 
            		(double)Main.instance.getConfig().getInt("jedi111.x"), 
            		(double)Main.instance.getConfig().getInt("jedi111.y"), 
            		(double)Main.instance.getConfig().getInt("jedi111.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sith511.world")), 
            		(double)Main.instance.getConfig().getInt("sith511.x"), 
            		(double)Main.instance.getConfig().getInt("sith511.y"), 
            		(double)Main.instance.getConfig().getInt("sith511.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("ali511.world")), 
            		(double)Main.instance.getConfig().getInt("ali511.x"), 
            		(double)Main.instance.getConfig().getInt("ali511.y"), 
            		(double)Main.instance.getConfig().getInt("ali511.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc, "Мустафар");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
