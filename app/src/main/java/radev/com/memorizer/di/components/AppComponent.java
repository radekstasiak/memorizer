package radev.com.memorizer.di.components;

/**
 * Created here and now by radek.
 * Peace and love.
 */

import javax.inject.Singleton;

import dagger.Component;
import radev.com.memorizer.AlarmReceiver;
import radev.com.memorizer.Dashboard;
import radev.com.memorizer.apiTranslator.ApiTranslatorModule;
import radev.com.memorizer.apiTranslator.TimePickerFragment;
import radev.com.memorizer.di.module.AppModule;
import radev.com.memorizer.di.module.NotificationModule;
import radev.com.memorizer.di.module.TimerModule;

@Singleton
@Component(modules = {ApiTranslatorModule.class,
        AppModule.class,
        TimerModule.class,
        NotificationModule.class})
public interface AppComponent {
    void inject(Dashboard activity);

    void inject(AlarmReceiver alarmReceiver);

    void inject(TimePickerFragment timePickerFragment);
}
