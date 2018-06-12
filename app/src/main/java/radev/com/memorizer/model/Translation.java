package radev.com.memorizer.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class Translation implements Comparable<Translation>{

    @SerializedName("timestamp")
    long timestamp;

    @SerializedName("source")
    String source;

    @SerializedName("languageTo")
    Language languageTo;

    @SerializedName("languageFrom")
    Language languageFrom;

    @SerializedName("translationList")
    List<String> translationList = new ArrayList<String>();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getTranslationList() {
        return translationList;
    }

    public void setTranslationList(List<String> translationList) {
        this.translationList = translationList;
    }
    public Language getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(Language languageTo) {
        this.languageTo = languageTo;
    }

    public Language getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(Language languageFrom) {
        this.languageFrom = languageFrom;
    }

    @Override
    public int compareTo(@NonNull Translation another) {
        if(this.getTimestamp()>another.getTimestamp()){
            return -1;
        }else {
            return 1;
        }
    }
}
