package radev.com.memorizer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.apiTranslator.TimePickerFragment;

/**
 * Created here and now by radek.
 * Peace and love.
 */
@Module
public class TimerModule {
    @Provides
    @Singleton
    public TimePickerFragment provideTimePicker(){

        return new TimePickerFragment();
    }
}
