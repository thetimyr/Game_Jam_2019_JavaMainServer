package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Lotal implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Lotal() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 25) {
        	p.sendTitle("§cУ вас нет 25 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("lotal") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jedilotal.world")), 
            		(double)Main.instance.getConfig().getInt("jedilotal.x"), 
            		(double)Main.instance.getConfig().getInt("jedilotal.y"), 
            		(double)Main.instance.getConfig().getInt("jedilotal.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sithlotal.world")), 
            		(double)Main.instance.getConfig().getInt("sithlotal.x"), 
            		(double)Main.instance.getConfig().getInt("sithlotal.y"), 
            		(double)Main.instance.getConfig().getInt("sithlotal.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("alilotal.world")), 
            		(double)Main.instance.getConfig().getInt("alilotal.x"), 
            		(double)Main.instance.getConfig().getInt("alilotal.y"), 
            		(double)Main.instance.getConfig().getInt("alilotal.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc, "Лотал");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
