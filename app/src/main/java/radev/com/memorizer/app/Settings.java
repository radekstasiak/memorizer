package radev.com.memorizer.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class Settings {

    private SharedPreferences mPreferences;
    private static final String PREF_NAME = "User Details";
    private static Settings sInstance;

    public Settings(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public Settings() {
        mPreferences = MemorizerApp.getInstance().getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    static synchronized Settings get() {
        if (sInstance == null) sInstance = new Settings();
        return sInstance;
    }
    public String getUrl() {
        String url = mPreferences.getString("url", "translate.googleapis.com");
        return "https://"+url+"/";
    }
}
