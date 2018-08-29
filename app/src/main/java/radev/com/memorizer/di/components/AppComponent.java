package radev.com.memorizer.di.components;

/**
 * Created here and now by radek.
 * Peace and love.
 */

import javax.inject.Singleton;

import dagger.Component;
import radev.com.memorizer.AlarmReceiver;
import radev.com.memorizer.BootReceiver;
import radev.com.memorizer.Dashboard;
import radev.com.memorizer.WordViewActivity;
import radev.com.memorizer.data.DataRepository;
import radev.com.memorizer.data.api.DataApiMapper;
import radev.com.memorizer.data.apiTranslator.ApiTranslatorModule;
import radev.com.memorizer.data.apiTranslator.TimePickerFragment;
import radev.com.memorizer.di.module.AppModule;
import radev.com.memorizer.di.module.DataRepositoryModule;
import radev.com.memorizer.di.module.MapperModule;
import radev.com.memorizer.di.module.NotificationModule;
import radev.com.memorizer.di.module.RxModule;
import radev.com.memorizer.di.module.TimerModule;
import radev.com.memorizer.rx.SchedulersFacade;

@Singleton
@Component(modules = {ApiTranslatorModule.class,
        AppModule.class,
        TimerModule.class,
        NotificationModule.class,
        DataRepositoryModule.class,
        RxModule.class,
        MapperModule.class})
public interface AppComponent {
    void inject(Dashboard activity);

    void inject(WordViewActivity activity);

    void inject(AlarmReceiver alarmReceiver);

    void inject(BootReceiver bootReceiver);

    void inject(TimePickerFragment timePickerFragment);

    DataRepository getDatRepository();
    DataApiMapper getDataApiMapper();
    SchedulersFacade getSchedulersFacade();
}
