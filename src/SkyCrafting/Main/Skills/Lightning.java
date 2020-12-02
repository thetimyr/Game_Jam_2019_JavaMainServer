package SkyCrafting.Main.Skills;

import java.util.*;
import org.bukkit.event.player.*;

import SkyCrafting.Main.Main;

import org.bukkit.event.block.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.*;

public class Lightning implements Listener
{
    private double additionalDamage;
    static WeakHashMap<Player, Long> cols;
    
    static {
        Lightning.cols = new WeakHashMap<Player, Long>();
    }
    
    public Lightning() {
        this.additionalDamage = 1.0;
    }
    
    private void lightning(final Location target) {
        target.getWorld().strikeLightningEffect(target);
    }
    
    static boolean check(final Player user, final long tmp) {
        final long time = System.currentTimeMillis();
        final Long last = Lightning.cols.get(user);
        if (last != null && time - last < tmp) {
            final long s = tmp - (time - last);
            user.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lСпособность перезаряжаться, подождите &f[" + s / 1000L + "]" + " секунд"));
            return false;
        }
        Lightning.cols.put(user, time);
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
        if (e.getPlayer().getItemInHand().getDurability() != 5) {
            return;
        }
        if (Main.instance.CheckregionPlaeyr(Player)) {
            return;
        }
        if (Main.instance.chekJedi(Player)) {
            return;
        }
        if (Main.instance.mesagges(Player, 7)) {
            return;
        }
        try {
            final LivingEntity entityTarget = Main.instance.rayTraceEntity(Player, 30);
            if (entityTarget == null || entityTarget.getPassenger() != null || entityTarget instanceof Villager || entityTarget instanceof Item) {
                Player.sendMessage("Враг не обнаружен, или слишком далеко");
            }
            else {
                if (Main.instance.CheckregionLivingEntity((Entity)entityTarget)) {
                    Player.sendMessage("Вы не можете использовать это рядом со спавном!");
                    return;
                }
                if (Main.instance.checEntytiSith(entityTarget)) {
                    return;
                }
                if (check(Player, 10000L)) {
                    final Block target = entityTarget.getLocation().getBlock();
                    final double health = entityTarget.getHealth() - 4.0;
                    if (this.additionalDamage > 0.0) {
                        entityTarget.setHealth(health);
                    }
                    entityTarget.playEffect(EntityEffect.HURT);
                    this.lightning(target.getLocation());
                }
            }
        }
        catch (Exception e2) {
            Player.sendMessage("Ваша цель не обнаружена!");
        }
    }
}
