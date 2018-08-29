package radev.com.memorizer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import radev.com.memorizer.data.DataRepository;
import radev.com.memorizer.data.api.DataApiMapper;
import radev.com.memorizer.data.apiTranslator.ApiTranslatorService;

/**
 * Created here and now by radek.
 * Peace and love.
 */
@Module
public class DataRepositoryModule  {

    @Provides
    @Singleton
    public DataRepository provideDataRepository(ApiTranslatorService apiTranslatorService,
                                                DataApiMapper dataApiMapper){
        return new DataRepository(apiTranslatorService,dataApiMapper);
    }
}
