package radev.com.memorizer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.rx.SchedulersFacade;

/**
 * Created here and now by radek.
 * Peace and love.
 */

@Module
public class RxModule {

    @Provides
    @Singleton
    public SchedulersFacade getSchedulerFacade() {
        return new SchedulersFacade();
    }
}
