package radev.com.memorizer.model;

public enum Language {
    ENGLISH("en"),
    POLISH("pl"),
    DUTCH("nl");

    private final String languageCode;

    Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }
}
