package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class AlarmScheduler {

    AlarmManager alarmManager;
    Context context;
    Settings settings;

    public AlarmScheduler(Context context, AlarmManager alarmManager, Settings settings) {
        this.alarmManager = alarmManager;
        this.context = context;
        this.settings = settings;
    }

    public void schedulerNextAlarm(){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calNow = Calendar.getInstance();
        calNow.setTimeInMillis(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(settings.getCurrentAlarmTimestamp());

        if (calendar.compareTo(calNow) <= 0) {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    public void cancelCurrentAlarm(){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(settings.getCurrentAlarmTimestamp());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        alarmManager.cancel(alarmIntent);

    }
}
