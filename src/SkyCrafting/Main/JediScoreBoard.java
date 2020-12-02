package SkyCrafting.Main;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;



import org.bukkit.scoreboard.*;

import ru.dondays.protocolboard.api.Board;



public class JediScoreBoard
{
    static ArrayList<Player> jedi;
    static ArrayList<Player> sith;
    static ArrayList<Player> alliance;
    static ArrayList<String> A;
    public static int AZ = 0;
    public static Board board;

    static {
        JediScoreBoard.jedi = new ArrayList<Player>();
        JediScoreBoard.sith = new ArrayList<Player>();
        JediScoreBoard.alliance = new ArrayList<Player>();
        JediScoreBoard.A = new ArrayList<String>();
    }
    
    public static void UpdateList(final Player p) {
        final Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        final Team alliance = sb.registerNewTeam("Alliance");
        alliance.setPrefix(new StringBuilder().append(ChatColor.GRAY).toString());
        final Team jedi = sb.registerNewTeam("Jedi");
        jedi.setPrefix(new StringBuilder().append(ChatColor.AQUA).toString());
        final Team sith = sb.registerNewTeam("Sith");
   
        
        for (final Player p2 : Bukkit.getOnlinePlayers()) {
            if (Levels.getSide(p2).equalsIgnoreCase("Jedi")) {
                jedi.addPlayer((OfflinePlayer)p2);
            }
            if (Levels.getSide(p2).equalsIgnoreCase("Sith")) {
                sith.addPlayer((OfflinePlayer)p2);
            }
            if (Levels.getSide(p2).equalsIgnoreCase("Ali")) {
                alliance.addPlayer((OfflinePlayer)p2);
            }
        }
        p.setScoreboard(sb);
    }
    public static String ColorFaction(Player p) {
        if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
          return "§3";
        }
        if (Levels.getSide(p).equalsIgnoreCase("Sith")) {
          return "§c";
        }
        if (Levels.getSide(p).equalsIgnoreCase("Ali")) {
          return "§8";
        }
        if (!Levels.getSide(p).equalsIgnoreCase("Ali") || !Levels.getSide(p).equalsIgnoreCase("Sith") || !Levels.getSide(p).equalsIgnoreCase("Jedi")) {
            return "§7";
          }
        return "";
      }
    private static int id;

	public static void cancel() {
		Bukkit.getScheduler().cancelTask(id);
		id = 0;
	}
	  public static String formatEco(Player p) {
	        double b = Main.econ.getBalance(p.getName());
	        if (b >= 1000 && b < 10000000) {
	            return Math.round((b / 1000) * 100.0) / 100.0 + "K";
	        }
	        if (b >= 10000000) {
	            return Math.round((b / 1000000) * 100.0) / 100.0 + "M";
	        }
	        return (int)Main.econ.getBalance(p.getName()) + "";
	    }
	  
	  public static void updateScoreboard(Player p)
	  {
		  p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		    Scoreboard playerBoard = p.getScoreboard();
		    for (Objective obj : playerBoard.getObjectives()) {
		      obj.unregister();
		    }
		    final Objective stats = playerBoard.registerNewObjective("Stats", "dummy");
		    stats.setDisplaySlot(DisplaySlot.SIDEBAR); 
		    stats.setDisplayName("§3Jedi§cCraft");
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', " &bПерсонаж:")).setScore(12);
	        if (Levels.getFaction(p).equalsIgnoreCase("Jedi")) {
	            stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Сторона &7» &3&lСветлая " + "&6[&e" + JediScoreBoard.jedi.size() + "&6]" )).setScore(11);
	        }
	        if (Levels.getFaction(p).equalsIgnoreCase("Sith")) {
	            stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Сторона &7» &c&lТемная " + "&6[&e" + JediScoreBoard.sith.size() + "&6]")).setScore(11);
	        }
	        if (Levels.getFaction(p).equalsIgnoreCase("Ali")) {
	            stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Сторона &7» &8&lАльянс " + "&6[&e" + JediScoreBoard.alliance.size() + "&6]")).setScore(11);
	        } 
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Баланс &7» " + "&e" + formatEco(p) + " &6&l$")).setScore(10);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Уровень &7» " + "&e" + getLevel(p) +"&8/"+ "&e" + "24" )).setScore(9);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', " ")).setScore(8);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', " &bСтатистика:")).setScore(7);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Убийств &7» " + "&e"+ Levels.kills.get(p.getName()) )).setScore(6);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Опыт &7» " + "&e"+ Levels.xp.get(p.getName() ))).setScore(5);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Смертей &7» " + "&e"+ Levels.deaths.get(p.getName() ))).setScore(4);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Квесты &7» &e" + Levels.getCrystal(p) + "&8/&e10" )).setScore(2);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "              "   )).setScore(1);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  Общий онлайн &7» " + "&e" + Bukkit.getOnlinePlayers().size() )).setScore(0);
	        stats.getScore(ChatColor.translateAlternateColorCodes('&', "  ")).setScore(-1);
	        
	        JediScoreBoard.jedi.clear();
	        JediScoreBoard.sith.clear();
	        JediScoreBoard.alliance.clear();  
	        for (final Player player2 : Bukkit.getOnlinePlayers()) {
	            if (Levels.getSide(player2).equalsIgnoreCase("Jedi")) {
	                JediScoreBoard.jedi.add(player2);
	            }
	            if (Levels.getSide(player2).equalsIgnoreCase("Sith")) {
	                JediScoreBoard.sith.add(player2);
	            }
	            if (Levels.getSide(player2).equalsIgnoreCase("Ali")) {
	                JediScoreBoard.alliance.add(player2);
	            }
	           
	        }
	        	p.setPlayerListName("§8[§6" + Levels.getLevel(p) + "§8] " + ColorFaction(p) + p.getName());
	   
	  }
	  
	  
	public static int getLevel(final Player p) {
        if (!p.isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level");
            return level;
        }
        final int level = Levels.levels.get(p.getName());
        return level;
    }
 
    
}
