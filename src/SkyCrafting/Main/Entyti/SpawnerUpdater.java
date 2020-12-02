package SkyCrafting.Main.Entyti;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import SkyCrafting.Main.*;
import com.sk89q.worldguard.protection.regions.*;
import com.sk89q.worldguard.protection.managers.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;

public class SpawnerUpdater extends BukkitRunnable
{
    Spawner spawn;
    
    public void run() {
        for (final Spawner cSpawner : Spawner.spawners.values()) {
            cSpawner.update();
            this.ChekDied(cSpawner, EntityTypes.Jabba, "Jabba");
            this.ChekDied(cSpawner, EntityTypes.Master, "Master");
           // this.ChekDied(cSpawner, EntityTypes.Lucar, "Lucar");
            this.ChekDied(cSpawner, EntityTypes.Adge, "Adge");
            this.ChekDied(cSpawner, EntityTypes.Magma, "Magma");
            this.ChekDied(cSpawner, EntityTypes.TemnuySlizen, "TemnuySlizen");
            this.ChekDied(cSpawner, EntityTypes.Yoda, "Yoda");
           // this.ChekDied(cSpawner, EntityTypes.Chubaka, "chubaka1");
            this.ChekDied(cSpawner, EntityTypes.Shtorm, "Shtorm");
            this.ChekDied(cSpawner, EntityTypes.Golem, "Golem");
            this.ChekDied(cSpawner, EntityTypes.Mol, "Mol");
            this.ChekDied(cSpawner, EntityTypes.ZOMBIEOTRECK, "ZOMBIEOTRECK");
            this.ChekDied(cSpawner, EntityTypes.Shudow, "Shudow");
            this.ChekDied(cSpawner, EntityTypes.Shudow1, "Shudow1");
            //this.ChekDied(cSpawner, EntityTypes.P1, "P1");
        }
    }
    
    public void ChekDied(final Spawner spawn, final EntityTypes type, final String Checkregion) {
        if (spawn.type.equals(type) && spawn.current == null) {
            final long a = spawn.getTime();
            for (final Player p : Bukkit.getOnlinePlayers()) {
                final RegionManager manager = Main.instance.wg.getRegionManager(p.getLocation().getWorld());
                for (final ProtectedRegion region : manager.getApplicableRegions(p.getLocation())) {
                    if (region.getId().equalsIgnoreCase(Checkregion)) {
                        sendActionBar(p, "§f§lДо спавна босса осталось " + Amaut(a / 60L, a % 60L));
                    }
                }
            }
        }
    }
    
    public static String Amaut(final long min, final long sec) {
        String minut = null;
        if (min <= 5L) {
            minut = "§a§l" + min + " §f§lминуты и §a§l" + sec + " §f§lсекунды";
        }
        else {
            minut = "§e§l" + min + " §f§lминут и §e§l" + sec + " §f§lсекунд";
        }
        return minut;
    }
    
    @SuppressWarnings("rawtypes")
	public static void sendActionBar(final Player p, String msg) {
        if (msg != null) {
            if (msg.contains("&")) {
                msg = msg.replace('&', '§');
            }
            final IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
            final PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)ppoc);
        }
        else {
            final IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"Message null! (Сообщите разработчику!)\"}");
            final PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)ppoc);
        }
    }
}
