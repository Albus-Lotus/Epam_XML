package edu.kravchenko.xml.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class GreetingPostcard extends Postcard {
    private HolidayType holiday;

    public GreetingPostcard() {
    }

    public GreetingPostcard(int id, String theme, boolean sent,
                            CountryType country, LocalDateTime year,
                            ValuableType valuable, HolidayType holiday) {
        super(id, theme, sent, country, year, valuable);
        this.holiday = holiday;
    }

    public HolidayType getHoliday() {
        return holiday;
    }

    public void setHoliday(HolidayType holiday) {
        this.holiday = holiday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GreetingPostcard greetingPostcard = (GreetingPostcard) o;
        return holiday == greetingPostcard.holiday;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + holiday.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postcard{");
        sb.append(super.toString());
        sb.append(", holiday=").append(holiday);
        sb.append('}');
        return sb.toString();
    }
}
