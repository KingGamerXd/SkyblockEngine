package net.hypixel.skyblock.features.calendar;

import net.hypixel.skyblock.SkyBlock;
import net.hypixel.skyblock.module.ConfigModule;
import net.hypixel.skyblock.util.SLog;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;

import java.util.Arrays;
import java.util.List;

public final class SkyBlockCalendar {
    public static final List<String> MONTH_NAMES;
    public static long ELAPSED;
    public static final int YEAR = 8928000;
    public static final int MONTH = 744000;
    public static final int DAY = 24000;

    private SkyBlockCalendar() {
    }

    public static int getYear() {
        return (int) (SkyBlockCalendar.ELAPSED / 8928000L);
    }

    public static int getMonth() {
        return (int) (SkyBlockCalendar.ELAPSED / 744000L) % 12 + 1;
    }

    public static int getDay() {
        return (int) (SkyBlockCalendar.ELAPSED / 24000L) % 31 + 1;
    }

    public static String getMonthName(final int month) {
        if (month < 1 || month > 12) {
            return "Unknown Month";
        }
        return SkyBlockCalendar.MONTH_NAMES.get(month - 1);
    }

    public static String getMonthName() {
        return getMonthName(getMonth());
    }

    public static void saveElapsed() {
        final SkyBlock plugin = SkyBlock.getPlugin();
        ConfigModule.getGenericConfig().set("timeElapsed", SkyBlockCalendar.ELAPSED);
        ConfigModule.getGenericConfig().save();
    }

    public static void synchronize(){
        SkyBlockCalendar.ELAPSED = ConfigModule.getGenericConfig().getLong("timeElapsed");

        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (entity instanceof HumanEntity) {
                    continue;
                }
                entity.remove();
            }
            int time = (int) (SkyBlockCalendar.ELAPSED % 24000L - 6000L);
            if (time < 0) {
                time += 24000;
            }
            world.setTime(time);
        }
    }

    static {
        MONTH_NAMES = Arrays.asList("Early Spring", "Spring", "Late Spring", "Early Summer", "Summer", "Late Summer", "Early Autumn", "Autumn", "Late Autumn", "Early Winter", "Winter", "Late Winter");
        SkyBlockCalendar.ELAPSED = 0L;
    }
}
