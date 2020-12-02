package SkyCrafting.Main.Points;

import org.bukkit.entity.*;
import java.util.*;
import com.sk89q.worldguard.protection.*;
import com.sk89q.worldguard.bukkit.*;
import com.sk89q.worldguard.protection.managers.*;
import com.sk89q.worldguard.protection.regions.*;
//import org.inventivetalent.bossbar.*;
import SkyCrafting.Main.*;
import org.bukkit.*;

public class Points
{
    public static final List<Points> points;
    private String name;
    private int money;
    private int time;
    private Race owner;
    private List<Location> markers;
    private String regionName;
    private double capturing;
    private Race prevOwner;
    private Set<Player> prevPlayers;
    private long nextGiving;
    
    static {
        points = new ArrayList<Points>();
    }
    
    public Points(final List<Location> markers, final String name, final String regionName, final int money, final int time) {
        this.owner = Race.NONE;
        this.prevPlayers = new HashSet<Player>();
        this.name = name;
        if (this.markers == null) {
            this.markers = markers;
        }
        this.regionName = regionName;
        this.money = money;
        this.time = time;
        Points.points.add(this);
    }
    
    public Points addMarker(final Location marker) {
        if (this.markers == null) {
            this.markers = new ArrayList<Location>();
        }
        this.markers.add(marker);
        return this;
    }
    
    public Set<Player> getAllPlayersInRegion(final String regionName) {
        final HashSet<Player> inRegion = new HashSet<Player>();
        for (final Player cPlayer : Bukkit.getOnlinePlayers()) {
            if (this.isInRegion(cPlayer.getLocation(), regionName)) {
                if (cPlayer.isDead()) {
                    continue;
                }
                inRegion.add(cPlayer);
            }
        }
        return inRegion;
    }
    
    public List<Location> getMarkers() {
        return this.markers;
    }
    
    public Points setMarkers(final List<Location> markers) {
        this.markers = markers;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getMoney() {
        return this.money;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public Points setName(final String name) {
        this.name = name;
        return this;
    }
    
    public Race getOwner() {
        return this.owner;
    }
    
    public Points setOwner(final Race owner) {
        this.owner = owner;
        return this;
    }
    
    public String getRegionName() {
        return this.regionName;
    }
    
    public Points setRegionName(final String regionName) {
        this.regionName = regionName;
        return this;
    }
    
    public ApplicableRegionSet getWGSet(final Location loc) {
        final WorldGuardPlugin wg = Main.instance.wg;
        if (wg == null) {
            return null;
        }
        final RegionManager rm = wg.getRegionManager(loc.getWorld());
        if (rm == null) {
            return null;
        }
        return rm.getApplicableRegions(BukkitUtil.toVector(loc));
    }
    
    public boolean isInRegion(final Location playerLocation, final String regionName) {
        if (regionName == null) {
            return true;
        }
        final ApplicableRegionSet set = this.getWGSet(playerLocation);
        if (set == null) {
            return false;
        }
        for (final ProtectedRegion r : set) {
            if (!r.getId().equalsIgnoreCase(regionName)) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public List<Player> getByRace(final Race race) {
        final ArrayList<Player> playerList = new ArrayList<Player>();
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (race == Race.SITH && Levels.getFaction(p).equalsIgnoreCase("Sith")) {
                playerList.add(p);
            }
            if (race == Race.JEDI && Levels.getFaction(p).equalsIgnoreCase("Jedi")) {
                playerList.add(p);
            }
        }
        return playerList;
    }
    
    public String getOwner(final Race race) {
        String r = null;
        if (race == Race.SITH) {
            r = "Ситхами!";
        }
        if (race == Race.JEDI) {
            r = "Джедаями!";
        }
        return r;
    }
    
    @SuppressWarnings("unused")
	public boolean update() {
        if (this.markers == null || this.markers.get(0) == null) {
            return false;
        }
        final ProtectedRegion region = Main.instance.wg.getRegionManager(this.markers.get(0).getWorld()).getRegion(this.regionName);
        final Set<Player> inRegion = this.getAllPlayersInRegion(region.getId());
        int jedi = 0;
        int sith = 0;
        for (final Player p : this.prevPlayers) {
            if (Levels.getSide(p).equalsIgnoreCase("Jedi")) {
                ++jedi;
            }
            else {
                if (!Levels.getSide(p).equalsIgnoreCase("Sith")) {
                    //BossBarAPI.setMessage(p, ChatColor.RED + "Вы не состоите в фракции!");
                    return true;
                }
                ++sith;
            }
        }
        if (this.owner != Race.NONE && this.nextGiving < System.currentTimeMillis()) {
            final List<Player> payArray = this.getByRace(this.owner);
            for (final Player p2 : payArray) {
                if (!Main.econ.hasAccount(p2.getName())) {
                    continue;
                }
                Main.econ.depositPlayer(p2.getName(), (double)this.money);
            }
            this.nextGiving = TimeUtil.addNSec(System.currentTimeMillis(), this.time);
        }
        if (inRegion.size() == 0) {
            return false;
        }
        if (this.capturing >= 100.0) {
            this.owner = Race.JEDI;
            this.capturing = 100.0;
        }
        if (this.capturing == 0.0) {
            this.owner = Race.NONE;
        }
        if (this.capturing <= -100.0) {
            this.owner = Race.SITH;
            this.capturing = -100.0;
        }
        if (this.owner != this.prevOwner) {
            if (this.owner == Race.NONE) {
                Bukkit.broadcastMessage(String.format("Точка %s была освобождена!", this.name));
            }
            else {
                Bukkit.broadcastMessage(String.format("Точка %s была захвачена %s", this.name, this.getOwner(this.owner)));
                Bukkit.broadcastMessage("Теперь все " + this.getOwner(this.owner).replace("ям", "").replace("ам", "") + " будут получать по " + this.money + "$ рас в " + this.time + " секунд.");
            }
            this.updateBlocks();
        }
        final float inProgress;
        if ((inProgress = (jedi - sith) * 1) == 0.0f) {
            for (final Player p : inRegion) {
               // BossBarAPI.setMessage(p, ChatColor.DARK_RED + "✖ Захват точки заблокирован врагом ✖", (float)Math.abs(this.capturing));
            }
        }
        else {
            if (Math.abs(this.capturing + inProgress) <= 100.0) {
                this.capturing += inProgress;
                for (final Player p : inRegion) {
                    //BossBarAPI.setMessage(p, ChatColor.YELLOW + "Происходит захват точки " + ((this.capturing > 0.0 == inProgress > 0.0f) ? "►►►" : "◄◄◄") + ChatColor.BOLD + ((this.capturing > 0.0) ? (ChatColor.AQUA + " Свет") : (ChatColor.DARK_RED + " Тьма")), (float)Math.abs(this.capturing));
                }
                if (this.owner != Race.NONE && this.capturing > 0.0 != inProgress > 0.0f && this.capturing % 25.0 == 0.0) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage("Точка " + this.name + " была атакована!");
                    }
                }
            }
            if (Math.abs(this.capturing) == 100.0) {
                for (final Player p : inRegion) {
                   // BossBarAPI.setMessage(p, ChatColor.GREEN + "Точка захвачена");
                    if (!inRegion.contains(p)) {
                        this.prevPlayers.remove(p);
                    }
                }
            }
        }
        this.prevOwner = this.owner;
        this.prevPlayers = inRegion;
        return true;
    }
    
    private void updateBlocks() {
        if (this.markers != null) {
            for (final Location loc : this.markers) {
                loc.getWorld().getBlockAt(loc).setType(Material.STAINED_CLAY);
                if (this.owner == Race.SITH || this.owner == Race.JEDI) {
                    loc.getWorld().getBlockAt(loc).setData((byte)((this.owner == Race.SITH) ? 6 : 3));
                }
            }
        }
    }
    
    public static void addPoint(final Points point) {
        Points.points.add(point);
    }
    
    public static Points getPoint(final String name) {
        for (final Points point : Points.points) {
            if (!point.getName().equals(name)) {
                continue;
            }
            return point;
        }
        return null;
    }
}
