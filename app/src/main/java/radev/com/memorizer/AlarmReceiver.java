package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.model.Translation;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlarmReceiver extends BroadcastReceiver {
    static List<Translation> wordsMap = new ArrayList<Translation>();

    @Inject
    Settings settings;

    @Inject
    AlarmScheduler alarmScheduler;

    @Override
    public void onReceive(Context context, Intent intent) {
        MemorizerApp.getInstance().getComponent().inject(this);
        alarmScheduler.schedulerNextAlarm();
        wordsMap = settings.getTranslationHistory();
        String word = "";
        if (wordsMap.size() > 0) {
            word = wordsMap.get(0).getSource().toString();
            displayNotification(context, word);
            Log.d("CLOCK", word);
        } else {
            Log.d("CLOCK", "TIC TIC TIC TACK");
        }

    }

    private void displayNotification(Context context, String value) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.mipmap.ic_launcher);
// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

// Sets an ID for the notification, so it can be updated
        int notifyID = 1;
        int numMessages = 0;
// Start of a loop that processes data and then notifies the user
        mBuilder.setContentText(value)
                .setNumber(++numMessages);
        // Because the ID remains unchanged, the existing notification is
        // updated.
        mNotifyMgr.notify(
                notifyID,
                mBuilder.build());
    }
}
