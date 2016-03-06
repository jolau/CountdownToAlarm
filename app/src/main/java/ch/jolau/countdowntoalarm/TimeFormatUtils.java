package ch.jolau.countdowntoalarm;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by jolau on 29.12.15.
 */
public class TimeFormatUtils {
    /**
     * One second (in milliseconds)
     */
    public static final int ONE_SECOND = 1000;
    /**
     * One minute (in milliseconds)
     */
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    /**
     * One hour (in milliseconds)
     */
    public static final int ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * One day (in milliseconds)
     */
    public static final int ONE_DAY = 24 * ONE_HOUR;


    public static String getRelativeToFutureTimeString(Context context, long time, long now, boolean shortVersion) {
        if (time < now || time <= 0) return "";

        final Resources res = context.getResources();
        final long timeDiff = time - now;

        if (timeDiff < ONE_MINUTE) {
            return res.getString(R.string.now);
        } else if (timeDiff < ONE_HOUR) {
            return res.getString(R.string.in_one_time, getMinuteString(res, timeDiff, shortVersion));
        } else if (timeDiff < ONE_DAY) {
            if (getMinutesOfHour(timeDiff) == 60)
                return res.getString(R.string.in_one_time, getHourString(res, timeDiff, shortVersion));
            else {
                return res.getString(R.string.in_time, getHourString(res, timeDiff, shortVersion), getMinuteString(res, timeDiff, shortVersion));
            }
        } else {
            return res.getString(R.string.in_time, getDayString(res, timeDiff, shortVersion), getHourString(res, timeDiff, shortVersion));
        }
    }

    private static String getDayString(Resources res, double timeDiff, boolean shortVersion) {
        long days = (long) Math.floor(timeDiff / ONE_DAY);
        return shortVersion ? res.getString(R.string.short_day, days) : res.getQuantityString(R.plurals.days, (int) days, days);
    }

    private static String getHourString(Resources res, double timeDiff, boolean shortVersion) {
        double days = Math.floor(timeDiff / ONE_DAY);
        double hours = days > 0 ? Math.ceil(timeDiff / ONE_HOUR) : Math.floor(timeDiff / ONE_HOUR); //if the minutes are not shown, the the rounded up value of the hours makes more sense
        long hourOfDay = (long) (hours - days * 24);
        return shortVersion ? res.getString(R.string.short_hour, hourOfDay) : res.getQuantityString(R.plurals.hours, (int) hourOfDay, hourOfDay);
    }

    private static String getMinuteString(Resources res, double timeDiff, boolean shortVersion) {
        long minutesOfHour = getMinutesOfHour(timeDiff);
        return shortVersion ? res.getString(R.string.short_minute, minutesOfHour) : res.getQuantityString(R.plurals.minutes, (int) minutesOfHour, minutesOfHour);
    }

    private static long getMinutesOfHour(double timeDiff) {
        double hours = Math.floor(timeDiff / ONE_HOUR);
        return (long) (Math.ceil(timeDiff / ONE_MINUTE) - hours * 60);
    }
}
