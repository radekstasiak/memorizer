package radev.com.memorizer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.model.Translation;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlarmReceiver extends BroadcastReceiver {
    static List<Translation> wordsMap = new ArrayList<Translation>();

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            // Register your reporting alarms here.
//        }
        Settings mSettings = MemorizerApp.getSettings();
        wordsMap = mSettings.getTranslationHistory();
        String word="";
        if (wordsMap.size() > 0) {
            word = wordsMap.get(0).getSource().toString();
            displayNotification(context,word);
            Log.d("CLOCK", word);
        } else {

            Log.d("CLOCK", "TIC TIC TIC TACK");
        }





    }

    private void displayNotification(Context context, String value){

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
