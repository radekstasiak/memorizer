package radev.com.memorizer.di.module;

import android.app.AlarmManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.AlarmScheduler;

/**
 * Created here and now by radek.
 * Peace and love.
 */

@Module
public class NotificationModule {

    @Provides
    @Singleton
    public AlarmScheduler provideAlarmScheduler(AlarmManager alarmManager, Context context) {
       return new AlarmScheduler(context, alarmManager);
    }
}
