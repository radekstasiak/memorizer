package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class AlarmScheduler {

    AlarmManager alarmManager;
    AlarmHelper alarmHelper;
    Context context;
    Settings settings;

    public AlarmScheduler(Context context, AlarmManager alarmManager, AlarmHelper alarmHelper, Settings settings) {
        this.alarmManager = alarmManager;
        this.context = context;
        this.settings = settings;
        this.alarmHelper = alarmHelper;
    }

    public void schedulerNextAlarm(){
        if(alarmHelper.isAlarmActive()) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            long nextAlarmTimestamp = alarmHelper.getNextAlarmTimestampInMillis();
            alarmHelper.saveCurrentAlarmTimestamp(alarmHelper.getNextAlarmTimestampInEpochSecond());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextAlarmTimestamp, alarmIntent);
        }

    }

    public void cancelCurrentAlarm(){
        if(alarmHelper.isAlarmActive()) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmHelper.getCurrentAlarmTimestampInMillis(), alarmIntent);
            alarmManager.cancel(alarmIntent);
        }

    }
}
