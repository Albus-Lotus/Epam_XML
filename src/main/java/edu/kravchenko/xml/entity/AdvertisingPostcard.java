package edu.kravchenko.xml.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class AdvertisingPostcard extends Postcard {
    private String organization;

    public AdvertisingPostcard() {
    }

    public AdvertisingPostcard(int id, String theme, boolean sent,
                               CountryType country, LocalDateTime year,
                               ValuableType valuable, String organization) {
        super(id, theme, sent, country, year, valuable);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdvertisingPostcard advertisingPostcard = (AdvertisingPostcard) o;
        return organization.equals(advertisingPostcard.organization);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + organization.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postcard{");
        sb.append(super.toString());
        sb.append(", organization=").append(organization);
        sb.append('}');
        return sb.toString();
    }
}
