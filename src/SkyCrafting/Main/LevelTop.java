package SkyCrafting.Main;

import java.util.Arrays;

import org.bukkit.entity.Player;

import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;

public class LevelTop extends OnlineDataCollector {
	
	public LevelTop() {
		super("toplevel", "JediCraft", BoardType.DEFAULT, "&6Топ по уровню", "toplevel", Arrays.asList(null, null, "&a{amount}", null));
	}

	@Override
	public Double getScore(Player player) {
		return (double) getLevel(player);
	}
	public static int getLevel(final Player p) {
        if (!p.isOnline()) {
            final int level = Main.instance.levelsConfig.getInt(String.valueOf(String.valueOf(p.getName())) + ".level");
            return level;
        }
        final int level = Levels.levels.get(p.getName());
        return level;
    }
}