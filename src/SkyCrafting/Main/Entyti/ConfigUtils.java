package SkyCrafting.Main.Entyti;

import SkyCrafting.Main.*;
import org.bukkit.configuration.file.*;
import org.bukkit.configuration.*;
import java.io.*;

public class ConfigUtils
{
    public static FileConfiguration loadConfig(final String fileName) {
        final File file = new File(Main.instance.getDataFolder(), fileName);
        final YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        final InputStream defConfigStream = Main.instance.getResource(fileName);
        if (defConfigStream != null) {
            final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults((Configuration)defConfig);
        }
        return (FileConfiguration)fileConfiguration;
    }
    
    public static FileConfiguration loadConfig(final File file) {
        final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
        return (FileConfiguration)defConfig;
    }
    
    public static void saveDefaultConfig(final String fileName) {
        final File file = new File(Main.instance.getDataFolder(), fileName);
        if (!file.exists()) {
            Main.instance.saveResource(fileName, false);
        }
    }
}
