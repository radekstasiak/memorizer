package radev.com.memorizer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.data.api.DataApiMapper;

/**
 * Created here and now by radek.
 * Peace and love.
 */
@Module
public class MapperModule {

    @Provides
    @Singleton
    public DataApiMapper provideDataApiMapper(){
        return new DataApiMapper();
    }
}
