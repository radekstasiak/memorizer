package radev.com.memorizer.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import radev.com.memorizer.model.Language;
import radev.com.memorizer.model.Translation;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class Settings {

    private SharedPreferences mPreferences;

    private static final String PREF_NAME = "User Details";
    private static final String TRANSLATION_HISTORY_PREFS_NAME = "TranslationListHistory";
    private static final String FROM_TO_LANGUAGES_PREFS_NAME = "FromToLanguages";

    private static final String CURRENT_ALARM_HOURS = "CurrentAlarmHour";
    private static final String CURRENT_ALARM_MINUTE = "CurrentAlarmMinute";
    private static final String CURRENT_ALARM_TIMESTAMP = "CurrentAlarmDate";
    private static final String ALARM_ACTIVE= "AlarmActive";

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
        if (sInstance == null)
            sInstance = new Settings(MemorizerApp.getInstance().getApplicationContext());
        return sInstance;
    }

    public String getUrl() {
        String url = mPreferences.getString("url", "translate.googleapis.com");
        return "https://" + url + "/";
    }

    public List<Translation> getTranslationHistory() {
        String json = mPreferences.getString(TRANSLATION_HISTORY_PREFS_NAME, "");
        List<Translation> obj = null;
        try {
            obj = objectMapper.readValue(json, new TypeReference<List<Translation>>() {
            });
            if (obj == null) {
                return new ArrayList<Translation>();
            }
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Translation>();
    }

    public void saveTranslationHistory(List<Translation> translationArrayList) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString(TRANSLATION_HISTORY_PREFS_NAME, gson.toJson(translationArrayList));
        prefsEditor.commit();
    }

    public List<Language> getFromToLanguages() {
        String json = mPreferences.getString(FROM_TO_LANGUAGES_PREFS_NAME, "");
        List<Language> obj = null;
        try {
            obj = objectMapper.readValue(json, new TypeReference<List<Language>>() {
            });
            if (obj == null) {
                return new ArrayList<Language>();
            }
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<Language>();
    }

    public void saveFromToLanguages(final Language from, final Language to) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString(FROM_TO_LANGUAGES_PREFS_NAME, gson.toJson(Arrays.asList(from, to)));
        prefsEditor.commit();
    }

    public int getCurrentAlarmHour() {
        return mPreferences.getInt(CURRENT_ALARM_HOURS, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }

    public void setCurrentAlarmHour(int hour) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putInt(CURRENT_ALARM_HOURS, hour);
        prefsEditor.commit();
    }

    public int getCurrentAlarmMinute() {
        return mPreferences.getInt(CURRENT_ALARM_MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
    }

    public void setCurrentAlarmMinute(int minute) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putInt(CURRENT_ALARM_MINUTE, minute);
        prefsEditor.commit();
    }

    public long getCurrentAlarmTimestamp(){
        return  mPreferences.getLong(CURRENT_ALARM_TIMESTAMP, System.currentTimeMillis());
    }

    public void setCurrentAlarmTimestamp(long value){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putLong(CURRENT_ALARM_TIMESTAMP, value);
        prefsEditor.commit();
    }

    public void setAlarmActive(boolean value){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putBoolean(ALARM_ACTIVE, value);
        prefsEditor.commit();
    }


    public boolean isAlarmActive(){
        return  mPreferences.getBoolean(ALARM_ACTIVE,false);
    }

}
