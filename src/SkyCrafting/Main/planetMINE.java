package SkyCrafting.Main;

import org.bukkit.event.*;

import SkyCrafting.Main.Commands.Naboo;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class planetMINE implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public planetMINE() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 10) {
        	p.sendTitle("§cУ вас нет 10 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("ilum") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jediilum.world")), (double)Main.instance.getConfig().getInt("jediilum.x"), (double)Main.instance.getConfig().getInt("jediilum.y"), (double)Main.instance.getConfig().getInt("jediilum.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sithilum.world")), (double)Main.instance.getConfig().getInt("sithilum.x"), (double)Main.instance.getConfig().getInt("sithilum.y"), (double)Main.instance.getConfig().getInt("sithilum.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc,sithLoc, "MINE");
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /jedicraft");
        return true;
    }
}
