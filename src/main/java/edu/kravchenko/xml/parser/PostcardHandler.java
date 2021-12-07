package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.*;

public class PostcardHandler extends DefaultHandler {
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private Set<Postcard> postcards;
    private Postcard current;
    private PostcardTag currentXmlTag;
    private EnumSet<PostcardTag> withText;

    public PostcardHandler() {
        postcards = new HashSet<>();
        withText = EnumSet.range(PostcardTag.THEME, PostcardTag.ORGANIZATION);
    }

    public Set<Postcard> getPostcards() {
        return postcards;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (qName.equals(PostcardTag.GREETING_POSTCARD.toString())
                || qName.equals(PostcardTag.ADVERTISING_POSTCARD.toString())) {
            current = qName.equals(PostcardTag.GREETING_POSTCARD.toString()) ?
                    new GreetingPostcard() :
                    new AdvertisingPostcard();
            if (attrs.getLength() == 1) {
                current.setId(Integer.parseInt(attrs.getValue(0)));
                current.setAuthor(Postcard.DEFAULT_AUTHOR);
            } else {
                int idAttributeIndex = attrs.getLocalName(0).equals(PostcardTag.ID.toString()) ? 0 : 1;
                int websiteAttributeIndex = 1 - idAttributeIndex;
                current.setId(Integer.parseInt(attrs.getValue(idAttributeIndex)));
                current.setAuthor(attrs.getValue(websiteAttributeIndex));
            }
        } else {
            PostcardTag temp = PostcardTag.valueOf(qName.toUpperCase().replace(HYPHEN, UNDERSCORE));
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(PostcardTag.GREETING_POSTCARD.toString())
                || qName.equals(PostcardTag.ADVERTISING_POSTCARD.toString())) {
            postcards.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case THEME -> current.setTheme(data);
                case SENT -> current.setSent(Boolean.parseBoolean(data));
                case COUNTRY -> current.setCountry(CountryType.valueOf(data.toUpperCase(Locale.ROOT)));
                case SENT_DATE -> current.setSentDate(LocalDateTime.parse(data));
                case VALUABLE -> current.setValuable(ValuableType.valueOf(data.toUpperCase(Locale.ROOT)));
                case HOLIDAY -> ((GreetingPostcard) current)
                        .setHoliday(HolidayType.valueOf(data.toUpperCase(Locale.ROOT)));
                case ORGANIZATION -> ((AdvertisingPostcard) current).setOrganization(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
