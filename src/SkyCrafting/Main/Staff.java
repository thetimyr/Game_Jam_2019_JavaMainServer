package SkyCrafting.Main;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Staff implements CommandExecutor, Listener
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (command.getName().equalsIgnoreCase("sc") && p.hasPermission("jedicraft.staff")) {
            if (args.length == 0) {
                p.sendMessage("Пожалуйста, напишите сообщение!");
                return true;
            }
            final StringBuilder str = new StringBuilder();
            for (int i = 0; i < args.length; ++i) {
                str.append(String.valueOf(args[i]) + " ");
            }
            final String msg = str.toString();
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("jedicraft.staff")) {
                    player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "ПЕРСОНАЛ" + ChatColor.DARK_GRAY + "] " + p.getDisplayName() + ChatColor.YELLOW + ": " + msg);
                }
            }
        }
        return true;
    }
}
