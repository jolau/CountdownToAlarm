package ch.jolau.countdowntoalarm;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by jolau on 28.12.15.
 */
public class AlarmClockObserver extends ContentObserver {

    public AlarmClockObserver(Handler handler) {
        super(handler);
    }

}
