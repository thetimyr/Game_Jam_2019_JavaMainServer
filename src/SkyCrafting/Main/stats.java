package SkyCrafting.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class stats implements Listener, CommandExecutor {
	
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] a) {
    	
    	
    	
    	
    	
    	
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("status")) {
            if (a.length == 0) {
            	final double eco = Main.econ.getBalance(p.getName());
            	final int a1 = (int)eco;
            	
            	p.sendMessage(" §9§nВаша статистика: ");
                p.sendMessage(" §bВаш ник: §e" + p.getDisplayName());
                p.sendMessage(" §bУбийств: " + "§e" + Levels.kills.get(p.getName()));
                p.sendMessage(" §bУровень: " + "§e" + Levels.getLevel(p) +"§8/"+ "§e" + "24");
                p.sendMessage(" §bБаланс: " + "§e" + a1);
                if (Levels.getFaction(p).equalsIgnoreCase("Jedi")) {
                	p.sendMessage(" §bСторона: " +"§3Джедай");
                }
                if (Levels.getFaction(p).equalsIgnoreCase("Sith")) {
                	p.sendMessage(" §bСторона: " + "§cСитх");
                }
                if (!Levels.getFaction(p).equalsIgnoreCase("Jedi") && !Levels.getFaction(p).equalsIgnoreCase("Sith")) {
                    p.sendMessage(" §bСторона: " + "§7Альянс");
                }
                p.sendMessage(" §bДобыто кристаллов: " + "§e" + Levels.crystal.get(p.getName()));
                
                
                return true;
            }
            if (a[0].equalsIgnoreCase("statusmine")) {
            	p.sendMessage(" §9§nCтатистика шахты: ");
            	p.sendMessage(" §bДобыто кристаллов: " + "§e" + Levels.crystal.get(p.getName()));
                return true;
            }
            if (a[1].equalsIgnoreCase("mine")) {
            	p.sendMessage(" §9§nCтатистика шахты: ");
            	p.sendMessage(" §bДобыто кристаллов: " + "§e" + Levels.crystal.get(p.getName()));
                return true;
            }
            
        }
		return true;
    }

}
