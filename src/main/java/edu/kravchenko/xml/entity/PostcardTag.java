package edu.kravchenko.xml.entity;

public enum PostcardTag {
    OLD_CARDS,
    GREETING_POSTCARD,
    ADVERTISING_POSTCARD,
    THEME,
    SENT,
    COUNTRY,
    YEAR,
    VALUABLE,
    ID,
    AUTHOR,
    HOLIDAY,
    SERVICE;

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    @Override
    public String toString() {
        String result = this.name();
        result = result.toLowerCase();
        result = result.replace(UNDERSCORE, HYPHEN);
        return result;
    }
}
