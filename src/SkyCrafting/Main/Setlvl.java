package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;

public class Setlvl implements Listener, CommandExecutor{
	
	

	
    public boolean abs(final CommandSender sender) {
        return !(sender instanceof Player);
    }
    @SuppressWarnings("static-access")
	@EventHandler
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] a) {
    	
    	
    	
    	
    	
    	
        final Player p = (Player)sender;
        if (p.isOp() && command.getName().equalsIgnoreCase("lwget")) {
            if (a.length == 0) {
                p.sendMessage("§6-----§dПсевдо админ панель XD§6");
                p.sendMessage("§6|§c/lwget §3lvl <player> <Level> - §bВыдать уровень игроку   ");
                p.sendMessage("§6|§c/lwget §3kills <player> <ammount> - §bприбавить себе килов");
                p.sendMessage("§6|§c/lwget §3xp - §bприбавить себе 100 опыта                  ");
                p.sendMessage("§6|§c/lwget §3scorebord - §bПерезагрузить табло                ");
                p.sendMessage("§6|§c/lwget §3jcp - §bPickaxe1                                 ");
                p.sendMessage("§6|§c/lwget §3playerinfo <player> - §bИнформация о игроке      ");
                p.sendMessage("§6|§c/lwget §3quest <player> - §bОтнять у игрока 1 выполненый квест      ");
                p.sendMessage("§6|§c/lwget §3quesplus <player> - §bДобавить игроку 1 выполненый квест");
                String logplayer = p.getDisplayName();
				Main.log.info("Игрок   " + logplayer + "   §4прописал команду §3/lwget");
                return true;
            }
            
            
            if (p.isOp() && a[0].equalsIgnoreCase("lvl")) {
            	
                String logplayer = p.getName();
				Main.log.info("Игрок   " + logplayer + "   §4Выдал пытается выдать себе §3уровень!");				
				p.sendMessage("§aУспешно! | надеюсьXD");
				
                Levels.levels.put(a[1], Integer.valueOf(a[2]));
                Main.instance.levelsConfig.set(String.valueOf(a[1]) + ".level", (Object)a[2]);
                Main.instance.saveCustomYml(Main.instance.levelsConfig, Main.instance.levels);
                
                return true;
            }
            if (p.isOp() && a[0].equalsIgnoreCase("playerinfo")) {
            	try {
				p.sendMessage("§aУспешно! | надеюсьXD");	
				int kill = Levels.kills.get(a[1]);
				int xp = Levels.xp.get(a[1]);
				int death = Levels.deaths.get(a[1]);
				int crystal = Levels.crystal.get(a[1]);
				int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(a[1])) + ".level");
				
		        final double eco = Main.econ.getBalance(a[1]);
		        final int a1 = (int)eco;
				p.sendMessage("§6§m---------------------------------------");    
				p.sendMessage("§d§lИнформация о игроке §c" + a[1]);
				p.sendMessage("§b§lKills §c" + kill);
				p.sendMessage("§b§lLEVEL §c" + level);
				p.sendMessage("§b§lBALANCE §c" + a1);
				p.sendMessage("§b§lDeaths §c" + death);
				p.sendMessage("§b§lExp §c" + xp);
				p.sendMessage("§b§lQuests §c" + crystal);  
				p.sendMessage("§6§m---------------------------------------");  
            	}catch (Exception e) {
					p.sendMessage("§cВы не ввели аргументы!");
				}
                return true;
            }
            if (p.isOp() && a[0].equalsIgnoreCase("quest")) {
            	try {
				p.sendMessage("§aУспешно! | надеюсьXD");	
				Levels.crystal.put(a[1], Integer.valueOf(((Integer)Levels.crystal.get(a[1])).intValue() - 1));  
            	}catch (Exception e) {
					p.sendMessage("§cВы не ввели аргументы!");
				}
                return true;
            }
            if (p.isOp() && a[0].equalsIgnoreCase("questplus")) {
            	try {
				p.sendMessage("§aУспешно! | надеюсьXD");	
				Levels.crystal.put(a[1], Integer.valueOf(((Integer)Levels.crystal.get(a[1])).intValue() + 1));  
            	}catch (Exception e) {
					p.sendMessage("§cВы не ввели аргументы!");
				}
                return true;
            }
            if (p.isOp() && a[0].equalsIgnoreCase("kills")) {
            	
            	p.sendMessage("§cНаверное выдались XD");
            	
            	String killer = a[1];
            	String strcol = a[2];
            	int col = Integer.valueOf(strcol);
            	
              	Levels.kills.put(killer, Integer.valueOf(((Integer)Levels.kills.get(killer)).intValue() + col));  	
            }
            if (p.isOp() && a[0].equalsIgnoreCase("scorebord")) {
            	
            	
            	p.sendMessage("§aБорд перезагружен!");            	
                JediScoreBoard.UpdateList(p);
                JediScoreBoard.updateScoreboard(p);               
              }
	        if (p.isOp() && a[0].equalsIgnoreCase("jcp")) {
	            p.sendMessage("§cВыданно!");
				p.sendMessage("§aУспешно! | надеюсьXD");
	            p.getInventory().addItem(ItemsNew.Pickaxe1());
	            
	            return true;
	           }
            if (p.isOp() && a[0].equalsIgnoreCase("xp")) {
            	
            	String skiller = p.getName();
        	
                Levels.xp.put(skiller, Integer.valueOf(((Integer)Levels.xp.get(skiller)).intValue() + 100 ));
        	
             JediScoreBoard.UpdateList(p);
             JediScoreBoard.updateScoreboard(p);
        	
            }
        }
            
        return true;
    }
    
    
    
}
