package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Hoth implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Hoth() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 21) {
            p.sendTitle("§cУ вас нет 21 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("hot") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedih.world")), 
            		(double)Main.instance.getConfig().getInt("jedih.x"), 
            		(double)Main.instance.getConfig().getInt("jedih.y"), 
            		(double)Main.instance.getConfig().getInt("jedih.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sithh.world")), 
            		(double)Main.instance.getConfig().getInt("sithh.x"), 
            		(double)Main.instance.getConfig().getInt("sithh.y"), 
            		(double)Main.instance.getConfig().getInt("sithh.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("alih.world")), 
            		(double)Main.instance.getConfig().getInt("alih.x"), 
            		(double)Main.instance.getConfig().getInt("alih.y"), 
            		(double)Main.instance.getConfig().getInt("alih.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc,aliLoc, "Хот");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
