package radev.com.memorizer;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class DateTimeHelper {

    public ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    public ZonedDateTime getTimeNow() {
        return ZonedDateTime.now(getZoneId());
    }

    public ZonedDateTime convertTimeStampToTime(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return ZonedDateTime.ofInstant(instant, getZoneId());
    }

    public long convertAlarmTimeToEpochTimestamp(int hourOfDay, int minute){
        ZonedDateTime zonedDateTime = getTimeNow();
        zonedDateTime = zonedDateTime.withHour(hourOfDay);
        zonedDateTime = zonedDateTime.withMinute(minute);
        return zonedDateTime.toInstant().getEpochSecond();

    }
}
