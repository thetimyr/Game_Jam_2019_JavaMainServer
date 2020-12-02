package SkyCrafting.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class tester implements Listener {


    @EventHandler
    public void onCo(PlayerInteractEvent event) throws InterruptedException {
    Player player = event.getPlayer();
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.WOOD_HOE && event.getClickedBlock().getType() == Material.MOSSY_COBBLESTONE) {
    event.getClickedBlock().setType(Material.COBBLESTONE);
    player.getInventory().addItem(new ItemStack(Material.VINE));
    player.sendMessage(ChatColor.YELLOW + "Вы убрали мусор.");
    Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable(){
    public void run(){
    event.getClickedBlock().setType(Material.MOSSY_COBBLESTONE);
    }
    },50*20);
    } else {
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem().getType() == Material.WOOD_HOE && event.getClickedBlock().getType() == Material.COBBLESTONE) {
    player.sendMessage(ChatColor.RED + "Этот блок чистый!");
    }
    }
    }
}
