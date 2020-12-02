package SkyCrafting.Main;

import SkyCrafting.Main.Entyti.*;
import java.util.*;

public class SmartMenu
{
    public SmartMenu(final EntityTypes type, final ArrayList<String> Lore) {
        for (final Spawner cSpawner : Spawner.spawners.values()) {
            if (cSpawner.getType().equals(type)) {
                if (cSpawner.getCurrent() == null) {
                    final long a = cSpawner.getTime();
                    Lore.add("§7Статус: §aУбит!");
                    Lore.add("§f§lДо спавна босса осталось " + SpawnerUpdater.Amaut(a / 60L, a % 60L));
                }
                else {
                    Lore.add("§7Статус: §aГотов к бою!");
                }
            }
        }
    }
}
