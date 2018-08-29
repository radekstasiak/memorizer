package radev.com.memorizer.data.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import radev.com.memorizer.model.Language;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class DataApiMapper {
    public Translation map(String response,
                                 String sourceWord,
                                 String sourceLanguage,
                                 String destinationLanguage) {
        Translation translation = new Translation();
        try {
            JSONArray obja = new JSONArray(response);
            JSONArray array = null;
            if (obja.get(1) instanceof JSONArray) {
                array = (JSONArray) ((JSONArray) ((JSONArray) obja.get(1)).get(0)).get(1);
            } else {
                array = new JSONArray(Arrays.asList(((JSONArray) ((JSONArray) obja.get(0)).get(0)).get(0)));
            }
            List<String> translationList = new ArrayList<String>();
            translation.setSource(sourceWord);
            translation.setTimestamp(System.currentTimeMillis());
            translation.setLanguageTo(Language.valueOf(destinationLanguage));
            translation.setLanguageFrom(Language.valueOf(sourceLanguage));
            for (int i = 0; i < array.length(); i++) {
                translationList.add((String) array.get(i));
            }
            translation.setTranslationList(translationList);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return translation;
    }
}
