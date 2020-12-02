package SkyCrafting.Main;

public abstract class TimeUtil
{
    public static long remNSec(final long time, final int seconds) {
        return time - seconds * 1000L;
    }
    
    public static long addNSec(final long time, final int seconds) {
        return time + seconds * 1000L;
    }
    
    public static long addNSec(final int seconds) {
        return System.currentTimeMillis() + seconds * 1000L;
    }
    
    public static long remNMin(final long time, final int mins) {
        return time - mins * 60000L;
    }
    
    public static long addNMin(final long time, final int mins) {
        return time + mins * 60000L;
    }
    
    public static long remNHour(final long time, final int hours) {
        return time - hours * 3600000L;
    }
    
    public static long addNHour(final int hours) {
        return System.currentTimeMillis() + hours * hours;
    }
    
    public static long addNHour(final long time, final int hours) {
        return time + hours * 3600000L;
    }
    
    public static long remNDay(final long time, final int days) {
        return time - days * 86400000L;
    }
    
    public static long addNDay(final long time, final int days) {
        return time + days * 86400000L;
    }
    
    public static long remNMonth(final long time, final int months) {
        return time - months * 2592000000L;
    }
    
    public static long addNMonth(final long time, final int months) {
        return time + months * 2592000000L;
    }
    
    public static long addNYear(final long time, final int years) {
        return time + years * 31104000000L;
    }
    
    public static long remNYear(final long time, final int years) {
        return time + years * 31104000000L;
    }
    
    public static long getMinute(final long time) {
        return time / 60000L;
    }
    
    public static long getSecond(final long time) {
        return time / 1000L;
    }
    
    public static String formate(long time) {
        final long m = getMinute(time);
        time = remNMin(time, (int)m);
        final long s = getSecond(time);
        return String.format("%02.0f:%02.0f", m, s);
    }
}
