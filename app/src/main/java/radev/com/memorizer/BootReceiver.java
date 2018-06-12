package radev.com.memorizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import radev.com.memorizer.app.MemorizerApp;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class BootReceiver extends BroadcastReceiver {

    @Inject
    AlarmScheduler alarmScheduler;

    @Override
    public void onReceive(Context context, Intent intent) {
        MemorizerApp.getInstance().getComponent().inject(this);
        alarmScheduler.schedulerNextAlarm();

    }
}
