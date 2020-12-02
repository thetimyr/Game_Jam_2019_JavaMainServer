package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Endor implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Endor() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 24) {
        	p.sendTitle("§cУ вас нет 24 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("endor") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jediend.world")), 
            		(double)Main.instance.getConfig().getInt("jediend.x"), 
            		(double)Main.instance.getConfig().getInt("jediend.y"), 
            		(double)Main.instance.getConfig().getInt("jediend.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sithend.world")), 
            		(double)Main.instance.getConfig().getInt("sithend.x"), 
            		(double)Main.instance.getConfig().getInt("sithend.y"), 
            		(double)Main.instance.getConfig().getInt("sithend.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("alihend.world")), 
            		(double)Main.instance.getConfig().getInt("alihend.x"), 
            		(double)Main.instance.getConfig().getInt("alihend.y"), 
            		(double)Main.instance.getConfig().getInt("alihend.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc , "Ендор");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
