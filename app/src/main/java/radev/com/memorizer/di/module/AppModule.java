package radev.com.memorizer.di.module;

import android.app.AlarmManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.app.MemorizerApp;
import radev.com.memorizer.app.Settings;

/**
 * Created here and now by radek.
 * Peace and love.
 */


@Module
public class AppModule {
    private final MemorizerApp mApp;

    public AppModule(MemorizerApp app) {
        mApp = app;
    }

    @Provides
    public MemorizerApp provideMemorizerApp() {
        return mApp;
    }

    @Provides
    @Singleton
    public Settings settings() {
        return new Settings(mApp);
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public AlarmManager proideAlarmManager() {
        return mApp.getAlarmManager();
    }


}
