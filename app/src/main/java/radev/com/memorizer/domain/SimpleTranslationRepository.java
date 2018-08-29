package radev.com.memorizer.domain;

import java.util.List;

import io.reactivex.Single;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public interface SimpleTranslationRepository {

    Single<Translation> getSimpleTranslation(String client, String sourceLang, String translationLang, List<String> options, String word);
}
