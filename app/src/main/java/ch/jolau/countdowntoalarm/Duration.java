package ch.jolau.countdowntoalarm;

/**
 * Created by jolau on 06.03.16.
 */
public class Duration {
    /**
     * One second (in milliseconds)
     */
    public static final double ONE_SECOND = 1000;
    /**
     * One minute (in milliseconds)
     */
    public static final double ONE_MINUTE = 60 * ONE_SECOND;
    /**
     * One hour (in milliseconds)
     */
    public static final double ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * One day (in milliseconds)
     */
    public static final double ONE_DAY = 24 * ONE_HOUR;

    private final long durationMillis;

    public Duration(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public long getMillis() {
        return durationMillis;
    }

    public long getDays(boolean isMaxDetail) {
        return round(durationMillis / ONE_DAY, isMaxDetail);
    }

    public long getHoursOfDay(boolean isMaxDetail) {
        long daysInHours = getDays(false) * 24;
        return getHours(isMaxDetail) - daysInHours;
    }

    public long getHours(boolean isMaxDetail) {
        return round(durationMillis / ONE_HOUR, isMaxDetail);
    }

    public long getMinutesOfHour(boolean isMaxDetail) {
        long hoursInMinutes = getHours(false) * 60;
        long minutes = getMinutes(isMaxDetail);
        return minutes - hoursInMinutes;
    }

    public long getMinutes(boolean isMaxDetail) {
        return round(durationMillis / ONE_MINUTE, isMaxDetail);
    }

    private long round(double time, boolean isMaxDetail) {
        if (isMaxDetail) {
            return (long) Math.ceil(time);
        } else {
            return (long) Math.floor(time);
        }
    }
}
