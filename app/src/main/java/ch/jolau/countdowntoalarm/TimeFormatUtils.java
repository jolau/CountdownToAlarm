package ch.jolau.countdowntoalarm;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by jolau on 29.12.15.
 */
public class TimeFormatUtils {
    public static String getRelativeToFutureTimeString(Context context, long time, boolean shortVersion) {
        long now = System.currentTimeMillis();
        if (time < now || time <= 0) return "";

        final Resources res = context.getResources();
        final Duration timeDiff = new Duration(time - now);

        if (timeDiff.getMinutes(false) == 0) {
            return res.getString(R.string.now);
        } else if (timeDiff.getMinutes(true) < 60) {
            return res.getString(R.string.in_one_time, getMinuteString(res, timeDiff, shortVersion));
        } else if (timeDiff.getHours(true) < 24) {
            if (timeDiff.getMinutesOfHour(true) == 60)
                return res.getString(R.string.in_one_time, getHourString(res, timeDiff, shortVersion, true));
            else {
                return res.getString(R.string.in_time, getHourString(res, timeDiff, shortVersion, false), getMinuteString(res, timeDiff, shortVersion));
            }
        } else {
            return res.getString(R.string.in_time, getDayString(res, timeDiff, shortVersion), getHourString(res, timeDiff, shortVersion, true));
        }
    }

    private static String getDayString(Resources res, Duration timeDiff, boolean shortVersion) {
        long days = timeDiff.getDays(false);
        return shortVersion ? res.getString(R.string.short_day, days) : res.getQuantityString(R.plurals.days, (int) days, days);
    }

    private static String getHourString(Resources res, Duration timeDiff, boolean shortVersion, boolean isMaxDetail) {
        //if the minutes are not shown, the the rounded up value of the hours makes more sense
        long hourOfDay = timeDiff.getHoursOfDay(isMaxDetail);
        return shortVersion ? res.getString(R.string.short_hour, hourOfDay) : res.getQuantityString(R.plurals.hours, (int) hourOfDay, hourOfDay);
    }

    private static String getMinuteString(Resources res, Duration timeDiff, boolean shortVersion) {
        long minutesOfHour = timeDiff.getMinutesOfHour(true);
        return shortVersion ? res.getString(R.string.short_minute, minutesOfHour) : res.getQuantityString(R.plurals.minutes, (int) minutesOfHour, minutesOfHour);
    }

}
