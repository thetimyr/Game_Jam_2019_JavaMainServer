package SkyCrafting.Main;

import org.bukkit.event.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;

public class score implements Listener, CommandExecutor
{
    ArrayList<Player> nodrop;
    
    public score() {
        this.nodrop = new ArrayList<Player>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("score") && Levels.faction.containsKey(p.getName())) {
        	Levels.crystal.put(p.getName(), Levels.crystal.get(p.getName()) + 1);
            return true;
        }
        return true;
    }
}
