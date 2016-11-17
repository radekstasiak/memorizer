package radev.com.memorizer.apiTranslator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public interface ApiTranslatorService {

    @GET("translate_a/single")
    Call<String> getFullTranslation(@Query("client") String client,
                                                @Query("sl") String sourceLang,
                                                @Query("tl") String translationLang,
                                                @Query("dt") List<String> options,
                                                @Query("q")  String word);

    @GET("translate_a/single")
    Call<String> getSimpleTranslation(@Query("client") String client,
                                                @Query("sl") String sourceLang,
                                                @Query("tl") String translationLang,
                                                @Query("dt") String options,
                                                @Query("q")  String word);
}
