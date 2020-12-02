package SkyCrafting.Main;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Utils {
	
	  public static void sendActionBar(Player p, String msg)
	  {
	    if (msg != null) {
	      IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + (msg) + "\"}");
	      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
	      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	    } else {
	      IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ("§dMessage null! (Сообщите SkyCrafting_!)") + "\"}");
	      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
	      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
	    }
	  }

}
