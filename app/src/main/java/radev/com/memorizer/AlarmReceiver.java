package radev.com.memorizer;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;
import radev.com.memorizer.model.Translation;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    public static final String CHANNEL_ID = "CCL_CHANNEL_ID";

    static List<Translation> wordsMap = new ArrayList<Translation>();

    @Inject
    Settings settings;

    @Inject
    AlarmScheduler alarmScheduler;

    @Override
    public void onReceive(Context context, Intent intent) {
        MemorizerApp.getInstance().getComponent().inject(this);
        wordsMap = settings.getTranslationHistory();
        String word = "";
        long timestamp = 0L;
        if (wordsMap.size() > 0) {
            word = wordsMap.get(0).getSource().toString();
            timestamp = wordsMap.get(0).getTimestamp();
            displayNotification(context, word, timestamp);
            Log.d("CLOCK", word);
        } else {
            Log.d("CLOCK", "TIC TIC TIC TACK");
        }

    }

    private void displayNotification(Context context, String value, long timestamp) {

        Intent resultIntent = new Intent(context, WordViewActivity.class);
        resultIntent.putExtra(WordViewActivity.RECENT_TRANSLATION, value);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(settings.getCurrentAlarmTimestamp());
        long timeInSecs = timestamp/1000;
        Instant instant = Instant.now(); //can be LocalDateTime
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
        LocalDateTime datetime = LocalDateTime.ofEpochSecond(timeInSecs, 0, currentOffsetForMyZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = datetime.format(formatter);
        String dateString = String.format(context.getResources().getString(R.string.notification_text), formatDateTime);
        mBuilder
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_text_rotation_none_black_48))
                .setSmallIcon(R.drawable.baseline_text_rotation_none_black_18)
                .setContentTitle(value)
                .setContentIntent(resultPendingIntent)
                .setContentText(dateString)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
