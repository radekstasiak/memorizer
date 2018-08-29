package radev.com.memorizer.data;

import java.util.List;

import io.reactivex.Single;
import radev.com.memorizer.data.api.DataApiMapper;
import radev.com.memorizer.data.apiTranslator.ApiTranslatorService;
import radev.com.memorizer.domain.SimpleTranslationRepository;
import radev.com.memorizer.model.Language;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class DataRepository implements
        SimpleTranslationRepository{

    ApiTranslatorService apiTranslatorService;
    DataApiMapper dataApiMapper;

    public DataRepository(ApiTranslatorService apiTranslatorService,
                          DataApiMapper dataApiMapper){
        this.apiTranslatorService = apiTranslatorService;
        this.dataApiMapper = dataApiMapper;
    }

    @Override
    public Single<Translation> getSimpleTranslation(String client, final String sourceLang, final String translationLang, List<String> options, final String word) {
        Language languageFrom = Language.valueOf(sourceLang);
        Language languageTo = Language.valueOf(translationLang);
        return apiTranslatorService
                .getFullTranslation(client, languageFrom.getLanguageCode(), languageTo.getLanguageCode(), options, word)
                .map(simpleTranslationResponse -> dataApiMapper.map(simpleTranslationResponse.body(), word,sourceLang,translationLang));
    }
}
