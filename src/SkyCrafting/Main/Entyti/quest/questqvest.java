package SkyCrafting.Main.Entyti.quest;

import java.util.*;
import org.bukkit.entity.*;

public class questqvest
{
    static ArrayList<String> quest;
    
    static {
        questqvest.quest = new ArrayList<String>();
    }
    
    public static void setquest(final Player p) {
        questqvest.quest.add(p.getName());
    }
}
