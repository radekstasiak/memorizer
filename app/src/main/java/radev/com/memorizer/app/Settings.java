package radev.com.memorizer.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class Settings {

    private SharedPreferences mPreferences;
    private static final String PREF_NAME = "User Details";
    private static Settings sInstance;
    private Gson gson;
    private ObjectMapper objectMapper;
    public Settings(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        objectMapper = new ObjectMapper();
    }

    public Settings() {
        mPreferences = MemorizerApp.getInstance().getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    static synchronized Settings get() {
        if (sInstance == null) sInstance = new Settings(MemorizerApp.getInstance().getApplicationContext());
        return sInstance;
    }
    public String getUrl() {
        String url = mPreferences.getString("url", "translate.googleapis.com");
        return "https://"+url+"/";
    }

    public List<Translation> getTranslationHistory(){
        String json = mPreferences.getString("TranslationListHistory", "");
        List<Translation> obj = null;
        try {
            obj = objectMapper.readValue(json,new TypeReference<List<Translation>>(){});
            if(obj==null){
                return new ArrayList<Translation>();
            }
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Translation>();
    }

    public void saveTranslationHistory(List<Translation> translationArrayList){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString("TranslationListHistory", gson.toJson(translationArrayList));
        prefsEditor.commit();
    }



}
