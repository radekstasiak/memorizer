package radev.com.memorizer.di.components;



/**
 * Created here and now by radek.
 * Peace and love.
 */

import javax.inject.Singleton;

import dagger.Component;
import radev.com.memorizer.Dashboard;
import radev.com.memorizer.apiTranslator.ApiTranslatorModule;
import radev.com.memorizer.di.module.AppModule;

@Singleton
@Component(modules = {ApiTranslatorModule.class, AppModule.class})

public interface AppComponent {
    void inject(Dashboard activity);
}
