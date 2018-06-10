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
        cancelCurrentAlarm();

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, settings.getCurrentAlarmHour());
        calendar.set(Calendar.MINUTE, settings.getCurrentAlarmMinute());
        calendar.set(Calendar.SECOND, 00);

        Calendar currentCalendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, settings.getCurrentAlarmDate());
        calendar.set(Calendar.HOUR_OF_DAY, settings.getCurrentAlarmHour());
        calendar.set(Calendar.MINUTE, settings.getCurrentAlarmMinute());
        calendar.set(Calendar.SECOND, 00);

        if(calendar.getTime().before(currentCalendar.getTime())){
            calendar.add(Calendar.DATE,1);
            settings.setCurrentAlarmDate(calendar.get(Calendar.DATE));
            settings.setCurrentAlarmHours(calendar.get(Calendar.HOUR_OF_DAY));
            settings.setCurrentAlarmMinute((calendar.get(Calendar.MINUTE)));
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

    private void cancelCurrentAlarm(){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DATE, settings.getCurrentAlarmDate());
        calendar.set(Calendar.HOUR_OF_DAY, settings.getCurrentAlarmHour());
        calendar.set(Calendar.MINUTE, settings.getCurrentAlarmMinute());
        calendar.set(Calendar.SECOND, 00);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        alarmManager.cancel(alarmIntent);
    }
}
