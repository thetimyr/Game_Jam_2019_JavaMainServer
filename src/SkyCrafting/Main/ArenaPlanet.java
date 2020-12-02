package SkyCrafting.Main;

import org.bukkit.event.*;

import SkyCrafting.Main.Commands.Naboo;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class ArenaPlanet implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public ArenaPlanet() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) < 8) {
            p.sendTitle("§cУ вас нет 8 уровня!!!", "");
            return true;
        }
        if (command.getName().equalsIgnoreCase("arena") && Levels.faction.containsKey(p.getName())) {
            final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("Arenajedi.world")), (double)Main.instance.getConfig().getInt("Arenajedi.x"), (double)Main.instance.getConfig().getInt("Arenajedi.y"), (double)Main.instance.getConfig().getInt("Arenajedi.z"));
            final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("ArenaSith.world")), (double)Main.instance.getConfig().getInt("ArenaSith.x"), (double)Main.instance.getConfig().getInt("ArenaSith.y"), (double)Main.instance.getConfig().getInt("ArenaSith.z"));
            Naboo.ChecPoint(p, this.nodrop, jediLoc, sithLoc,sithLoc, "Арена");           
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /jedicraft");
        return true;       
    }
    
    
}
