package edu.kravchenko.xml.entity;

import java.time.LocalDateTime;

public abstract class Postcard {
    public static final String DEFAULT_AUTHOR = "Anonymous";
    private int id;
    private String author;
    private String theme;
    private boolean sent;
    private CountryType country;
    private LocalDateTime sentDate;
    private ValuableType valuable;

    public Postcard() {
    }

    public Postcard(int id, String theme, boolean sent, CountryType country,
                    LocalDateTime sentDate, ValuableType valuable) {
        this.id = id;
        this.author = DEFAULT_AUTHOR;
        this.theme = theme;
        this.sent = sent;
        this.country = country;
        this.sentDate = sentDate;
        this.valuable = valuable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public CountryType getCountry() {
        return country;
    }

    public void setCountry(CountryType country) {
        this.country = country;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public ValuableType getValuable() {
        return valuable;
    }

    public void setValuable(ValuableType valuable) {
        this.valuable = valuable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postcard postcard = (Postcard) o;
        return sent == postcard.sent
                && author.equals(postcard.author)
                && theme.equals(postcard.theme)
                && country == postcard.country
                && sentDate.equals(sentDate)
                && valuable == postcard.valuable;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + author.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + (sent ? 1 : 0);
        result = 31 * result + country.hashCode();
        result = 31 * result + sentDate.hashCode();
        result = 31 * result + valuable.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postcard{");
        sb.append("id=").append(id);
        sb.append(", author=").append(author);
        sb.append(", theme=").append(theme);
        sb.append(", sent=").append(sent);
        sb.append(", country=").append(country);
        sb.append(", sentDate=").append(sentDate);
        sb.append(", valuable=").append(valuable);
        sb.append('}');
        return sb.toString();
    }
}
