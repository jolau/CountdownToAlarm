package ch.jolau.countdowntoalarm;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

/**
 * Created by jolau on 28.12.15.
 */
public class CountdownToAlarmExtension extends DashClockExtension {

    private static final String TAG = "CountdownToAlarmExtension";

    public static final String PREF_NAME = "pref_name";

    private AlarmManager alarmManager;

    private boolean mRegistered = false;

    private BroadcastReceiver mNextAlarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onUpdateData(0);
        }
    };

    private void registerNextAlarmBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AlarmManager.ACTION_NEXT_ALARM_CLOCK_CHANGED);
        registerReceiver(mNextAlarmReceiver, filter);
        mRegistered = true;
    }

    private void unregisterNextAlarmBroadcast() {
        if (mRegistered) {
            unregisterReceiver(mNextAlarmReceiver);
            mRegistered = false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        registerNextAlarmBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNextAlarmBroadcast();
    }

    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        setUpdateWhenScreenOn(true);
    }

    @Override
    protected void onUpdateData(int reason) {
        AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();
        if (alarmClockInfo != null) {
            long alarmTime = alarmClockInfo.getTriggerTime();

            String timeToAlarm = TimeFormatUtils.getRelativeToFutureTimeString(this, alarmTime, false);
            String timeToAlarmShort = TimeFormatUtils.getRelativeToFutureTimeString(this, alarmTime, true);

            // Publish the extension data update.
            publishUpdate(new ExtensionData()
                            .visible(true)
                            .icon(R.mipmap.ic_launcher)
                            .status(timeToAlarmShort)
                            .expandedTitle(timeToAlarm)
            );
        } else {
            publishUpdate(new ExtensionData().visible(false));
        }
    }
}
