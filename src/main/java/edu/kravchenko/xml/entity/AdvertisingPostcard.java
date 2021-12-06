package edu.kravchenko.xml.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class AdvertisingPostcard extends Postcard {
    private String service;

    public AdvertisingPostcard() {
    }

    public AdvertisingPostcard(int id, String theme, boolean sent,
                               CountryType country, LocalDateTime year,
                               ValuableType valuable, String service) {
        super(id, theme, sent, country, year, valuable);
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdvertisingPostcard advertisingPostcard = (AdvertisingPostcard) o;
        return service.equals(advertisingPostcard.service);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + service.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postcard{");
        sb.append(super.toString());
        sb.append(", service=").append(service);
        sb.append('}');
        return sb.toString();
    }
}
