package radev.com.memorizer;

import org.threeten.bp.Instant;
import org.threeten.bp.ZonedDateTime;

import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class AlarmHelper {

    Settings settings;
    DateTimeHelper dateTimeHelper;

    public AlarmHelper(Settings settings, DateTimeHelper dateTimeHelper) {
        this.settings = settings;
        this.dateTimeHelper = dateTimeHelper;
    }

    public void saveAlarmDetails(int hourOfDay, int minute) {
        settings.setCurrentAlarmHour(hourOfDay);
        settings.setCurrentAlarmMinute(minute);
        settings.setCurrentAlarmTimestamp(dateTimeHelper.convertAlarmTimeToEpochTimestamp(hourOfDay,minute));
        settings.setAlarmActive(true);
    }


    public void saveCurrentAlarmTimestamp(long timestamp){
        settings.setCurrentAlarmTimestamp(timestamp);
    }

    // get timestap of next alarm, when scheduling new alarm in alarm manager
    public long getNextAlarmTimestampInMillis() {
        return getNextAlarmDate().toInstant().toEpochMilli();
    }

    // save timestamp of next alarm scheduled
    public long getNextAlarmTimestampInEpochSecond() {
        return getNextAlarmDate().toInstant().getEpochSecond();
    }

    // timestamp for cancelAlarm method
    public long getCurrentAlarmTimestampInMillis() {
        Instant instant = Instant.ofEpochSecond(settings.getCurrentAlarmTimestamp());
        return instant.toEpochMilli();
    }

    public boolean isAlarmActive(){
        return settings.isAlarmActive();
    }

    private ZonedDateTime getNextAlarmDate() {
        ZonedDateTime zonedDateTime = dateTimeHelper.convertTimeStampToTime(settings.getCurrentAlarmTimestamp());
        zonedDateTime = zonedDateTime.withMinute(settings.getCurrentAlarmMinute());
        zonedDateTime = zonedDateTime.withHour(settings.getCurrentAlarmHour());
        ZonedDateTime zonedDateTimeNow = dateTimeHelper.getTimeNow();

        while (zonedDateTime.isBefore(zonedDateTimeNow)){
            zonedDateTime = zonedDateTime.plusDays(1);
        }

        return zonedDateTime;
    }





}
