package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Unfoboss implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (Levels.getLevel(p) >= 21) {
            if (command.getName().equalsIgnoreCase("endor") && Levels.faction.containsKey(p.getName())) {
                final Location jediLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("jediend.world")), (double)Main.instance.getConfig().getInt("jediend.x"), (double)Main.instance.getConfig().getInt("jediend.y"), (double)Main.instance.getConfig().getInt("jediend.z"));
                final Location sithLoc = new Location(Bukkit.getWorld(Main.instance.getConfig().getString("sithend.world")), (double)Main.instance.getConfig().getInt("sithend.x"), (double)Main.instance.getConfig().getInt("sithend.y"), (double)Main.instance.getConfig().getInt("sithend.z"));
                if (Levels.faction.get(p.getName()).equalsIgnoreCase("Jedi")) {
                    p.teleport(jediLoc);
                    p.sendMessage(ChatColor.DARK_AQUA + "Вы успешно телепортированы на планету Ендор!");
                    return true;
                }
                if (Levels.faction.get(p.getName()).equalsIgnoreCase("Sith")) {
                    p.teleport(sithLoc);
                    p.sendMessage(ChatColor.DARK_RED + "Вы успешно телепортированы на планету Ендор!");
                    return true;
                }
            }
            p.sendMessage(ChatColor.GREEN + "Вы еще не выбрали свою сторону, пропишите /side");
            return true;
        }
        p.sendMessage(ChatColor.RED + "У вас нет 21 уровня!!!");
        return true;
    }
}
