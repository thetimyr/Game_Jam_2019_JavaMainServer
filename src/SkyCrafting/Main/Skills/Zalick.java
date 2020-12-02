package SkyCrafting.Main.Skills;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.potion.*;

import SkyCrafting.Main.Main;

import org.bukkit.event.*;

public class Zalick implements Listener
{
    static WeakHashMap<Player, Long> cols;
    
    static {
    	Zalick.cols = new WeakHashMap<Player, Long>();
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Zalick.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезарежаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        Zalick.cols.put(user, time);
        return true;
    }
    
    @EventHandler
    public void use(final PlayerInteractEvent e) {
        final Player Player = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() != Material.INK_SACK) {
            return;
        }
        if (e.getPlayer().getItemInHand().getDurability() != 15) {
            return;
        }
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 23)) {
            return;
        }
        if (check(Player, 20000L)) {
            Player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 100));
        }
    }
}