package SkyCrafting.Main;

public enum Race
{
    JEDI("JEDI", 0), 
    SITH("SITH", 1), 
    NONE("NONE", 2);
    
    private Race(final String s, final int n) {
    }
    
    public static Race fromString(final String race) {
        Race[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Race cur = values[i];
            if (cur.name().equalsIgnoreCase(race)) {
                return cur;
            }
        }
        return Race.NONE;
    }
}
