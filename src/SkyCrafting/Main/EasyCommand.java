package SkyCrafting.Main;

import java.util.*;
import org.bukkit.*;
import java.lang.reflect.*;
import org.bukkit.command.*;

public abstract class EasyCommand implements CommandExecutor, TabExecutor
{
    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permMessage;
    protected static CommandMap cmap;
    
    public EasyCommand(final String command) {
        this(command, null, null, null, null);
    }
    
    public EasyCommand(final String command, final String usage) {
        this(command, usage, null, null, null);
    }
    
    public EasyCommand(final String command, final String usage, final String description) {
        this(command, usage, description, null, null);
    }
    
    public EasyCommand(final String command, final String usage, final String description, final String permissionMessage) {
        this(command, usage, description, permissionMessage, null);
    }
    
    public EasyCommand(final String command, final String usage, final String description, final List<String> aliases) {
        this(command, usage, description, null, aliases);
    }
    
    public EasyCommand(final String command, final String usage, final String description, final String permissionMessage, final List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
    }
    

    
    final CommandMap getCommandMap() {
        if (EasyCommand.cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                EasyCommand.cmap = (CommandMap)f.get(Bukkit.getServer());
                return this.getCommandMap();
            }
            catch (Exception e) {
                e.printStackTrace();
                return this.getCommandMap();
            }
        }
        if (EasyCommand.cmap != null) {
            return EasyCommand.cmap;
        }
        return this.getCommandMap();
    }
    
    public abstract boolean onCommand(final CommandSender p0, final Command p1, final String p2, final String[] p3);
    
    public List<String> onTabComplete(final CommandSender s, final Command cmd, final String label, final String[] args) {
        return null;
    }
    

}
