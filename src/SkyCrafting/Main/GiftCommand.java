package SkyCrafting.Main;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;

public class GiftCommand extends EasyCommand
{
    HashMap<Player, ItemStack> list;
    
    public GiftCommand() {
        super("gift", "Использование: /gift <nick>", "Передача предметов", "jedicraft.gift", Arrays.asList("giftitem", "передать"));
        this.list = new HashMap<Player, ItemStack>();
    }
    
    @Override
    public boolean onCommand(final CommandSender s, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)s;
        if (cmd.getName().equalsIgnoreCase("gift")) {
            if (args.length == 0) {
                p.sendMessage("§cИспользование: /gift <ник>");
                return true;
            }
            for (final Player p2 : Bukkit.getOnlinePlayers()) {
                if (args.length > 0 && args[0].equalsIgnoreCase(p2.getName()) && args[0] != p.getName()) {
                    if (p.getWorld().getName().equalsIgnoreCase(p2.getWorld().getName())) {
                        if (p.getLocation().distance(p2.getLocation()) <= 5.0) {
                            if (p.getName().equals(p2.getName())) {
                                p.sendMessage(ChatColor.RED + "Вы не можете отправить самому себе предмет!");
                                return true;
                            }
                            if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasDisplayName()) {
                                p.sendMessage("§7Вы успешно передали §e" + p.getItemInHand().getItemMeta().getDisplayName() + "§7 игроку §4" + p2.getName());
                                p2.sendMessage("§7Вы получили §e" + p.getItemInHand().getItemMeta().getDisplayName() + "§7 от игрока §4" + p.getName());
                            }
                            else {
                                p.sendMessage("§7Вы успешно передали §e" + p.getItemInHand().getType().toString() + "§7 игроку §4" + p2.getName());
                                p2.sendMessage("§7Вы получили §e" + p.getItemInHand().getType().toString() + "§7 от игрока §4" + p.getName());
                            }
                            this.list.put(p, p.getItemInHand());
                            p.getInventory().removeItem(new ItemStack[] { this.list.get(p) });
                            p2.getInventory().addItem(new ItemStack[] { this.list.get(p) });
                            this.list.remove(p);
                        }
                        else {
                            p.sendMessage(ChatColor.RED + "Вы должны находиться в радиусе 5 блоков от игрока.");
                        }
                    }
                    else {
                        p.sendMessage(ChatColor.RED + "Вы должны находиться в одном и том же мире.");
                    }
                }
            }
        }
        return true;
    }
    
}
