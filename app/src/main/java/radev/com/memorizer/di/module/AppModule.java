package radev.com.memorizer.di.module;

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
    public MemorizerApp provideFireflyApp() {
        return mApp;
    }

    @Provides
    @Singleton
    public Settings settings() {
        return new Settings(mApp);
    }
}
