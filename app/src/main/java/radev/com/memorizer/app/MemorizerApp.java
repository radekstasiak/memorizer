package radev.com.memorizer.app;

import android.app.AlarmManager;
import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import radev.com.memorizer.apiTranslator.ApiTranslatorModule;
import radev.com.memorizer.di.components.AppComponent;
import radev.com.memorizer.di.components.DaggerAppComponent;
import radev.com.memorizer.di.module.AppModule;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class MemorizerApp extends Application {

    private static MemorizerApp sInstance;
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiTranslatorModule(new ApiTranslatorModule())
                .build();
        AndroidThreeTen.init(this);

    }
    public synchronized static MemorizerApp getInstance() {
        return sInstance;
    }

    public static Settings getSettings() {
        return Settings.get();
    }

    public AppComponent getComponent() {
        return component;
    }

    public AlarmManager getAlarmManager(){
        return (AlarmManager) this.getSystemService(ALARM_SERVICE);
    }
}
