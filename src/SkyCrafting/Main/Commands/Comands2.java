package SkyCrafting.Main.Commands;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.*;

import SkyCrafting.Main.Levels;

public class Comands2 implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player p = (Player)sender;
        final String itemd = p.getItemInHand().getType().toString();
        if (!itemd.equals("CARPET") && !itemd.equals("")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lЧувак, это не шапка"));
            return true;
        }
        final PlayerInventory inv = p.getPlayer().getInventory();
        ItemStack cap = inv.getItemInHand();
        if (!Levels.getFaction(p).equalsIgnoreCase("Jedi") && !Levels.getFaction(p).equalsIgnoreCase("Sith")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lДля начала выберите фракцию."));
            return true;
        }
        if (Levels.getFaction(p).equalsIgnoreCase("Jedi")) {
            cap = this.addArmorPoints(cap, 4);
            if (inv.getHelmet() == null) {
                inv.setHelmet(cap);
                inv.setItemInHand((ItemStack)null);
            }
            else {
                final ItemStack helmet = inv.getHelmet();
                inv.setHelmet(inv.getItemInHand());
                inv.setItemInHand(helmet);
            }
        }
        else if (Levels.getFaction(p).equalsIgnoreCase("Sith")) {
            cap = this.addArmorPoints(cap, 4);
            if (inv.getHelmet() == null) {
                inv.setHelmet(cap);
                inv.setItemInHand((ItemStack)null);
            }
            else {
                final ItemStack helmet = inv.getHelmet();
                inv.setHelmet(inv.getItemInHand());
                inv.setItemInHand(helmet);
            }
        }
        return true;
    }
    
    private ItemStack addArmorPoints(final ItemStack cap, final int i) {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(cap);
        final NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        final NBTTagList modifiers = new NBTTagList();
        final NBTTagCompound toughness = new NBTTagCompound();
        toughness.set("AttributeName", (NBTBase)new NBTTagString("armorToughness"));
        toughness.set("Name", (NBTBase)new NBTTagString("armorToughness"));
        toughness.set("Amount", (NBTBase)new NBTTagInt(4));
        toughness.set("Operation", (NBTBase)new NBTTagInt(0));
        toughness.set("UUIDLeast", (NBTBase)new NBTTagInt(894654));
        toughness.set("UUIDMost", (NBTBase)new NBTTagInt(2872));
        modifiers.add((NBTBase)toughness);
        final NBTTagCompound armor = new NBTTagCompound();
        armor.set("AttributeName", (NBTBase)new NBTTagString("generic.armor"));
        armor.set("Name", (NBTBase)new NBTTagString("generic.armor"));
        armor.set("Amount", (NBTBase)new NBTTagInt(4));
        armor.set("Operation", (NBTBase)new NBTTagInt(0));
        armor.set("UUIDLeast", (NBTBase)new NBTTagInt(894654));
        armor.set("UUIDMost", (NBTBase)new NBTTagInt(2872));
        modifiers.add((NBTBase)armor);
        compound.set("AttributeModifiers", (NBTBase)modifiers);
        nmsStack.setTag(compound);
        final ItemStack x = CraftItemStack.asBukkitCopy(nmsStack);
        final ItemMeta im = x.getItemMeta();
        im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        x.setItemMeta(im);
        return x;
    }
}
