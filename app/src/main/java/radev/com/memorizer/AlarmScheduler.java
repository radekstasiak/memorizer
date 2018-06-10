package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class AlarmScheduler {

    AlarmManager alarmManager;
    Context context;

    public AlarmScheduler(Context context, AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
        this.context = context;
    }

    public void schedulerNextAlarm(){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE,00 );
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

    }
}
