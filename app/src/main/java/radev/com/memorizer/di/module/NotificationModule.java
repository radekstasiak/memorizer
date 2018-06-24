package radev.com.memorizer.di.module;

import android.app.AlarmManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.AlarmHelper;
import radev.com.memorizer.AlarmScheduler;
import radev.com.memorizer.DateTimeHelper;
import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */

@Module
public class NotificationModule {

    @Provides
    @Singleton
    public AlarmScheduler provideAlarmScheduler(AlarmManager alarmManager, AlarmHelper alarmHelper, Context context, Settings settings) {
        return new AlarmScheduler(context, alarmManager, alarmHelper, settings);
    }

    @Provides
    @Singleton
    public AlarmHelper provideAlarmHelper(Settings settings, DateTimeHelper dateTimeHelper) {
        return new AlarmHelper(settings, dateTimeHelper);
    }
}
