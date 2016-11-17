package radev.com.memorizer.apiTranslator;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import radev.com.memorizer.app.Settings;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created here and now by radek.
 * Peace and love.
 */
@Module
@Singleton
public class ApiTranslatorModule {

    @Provides
    public ApiTranslatorService provideApiService(Settings settings){
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        return new Retrofit.Builder()
                .baseUrl(settings.getUrl())
                .client(httpClient)
                .addConverterFactory(new StringConverter())
                .build()
                .create(ApiTranslatorService.class);
    }
}
