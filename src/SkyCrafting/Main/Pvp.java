package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;

public class Pvp implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        final ArrayList<Location> locations = new ArrayList<Location>();
        final Random rand = new Random();
        if (Levels.getLevel(p) < 8) {
            p.sendMessage(ChatColor.RED + "У вас нет 8 уровня!!!");
            return true;
        }
        if (command.getName().equalsIgnoreCase("pvp") && Levels.faction.containsKey(p.getName())) {
            locations.add(new Location(Bukkit.getWorld("Space"), -376.0, 25.0, -1959.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -377.0, 22.0, -1883.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -404.0, 22.0, -1884.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -386.0, 22.0, -1878.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -344.0, 22.0, -1897.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -396.0, 10.0, -1924.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -415.0, 28.0, -1895.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -377.0, 28.0, -1888.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -343.0, 28.0, -1888.0));
            locations.add(new Location(Bukkit.getWorld("Space"), -377.0, 24.0, -2002.0));
            final Location loc = locations.get(rand.nextInt(locations.size()));
            p.teleport(loc);
            return true;
        }
        p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /jedicraft");
        return true;
    }
}
