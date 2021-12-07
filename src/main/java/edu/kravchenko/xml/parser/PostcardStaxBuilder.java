package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;


public class PostcardStaxBuilder extends PostcardBuilder {
    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';
    private XMLInputFactory inputFactory;

    public PostcardStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildPostcards(String filePath) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(PostcardTag.ADVERTISING_POSTCARD.toString())
                            || name.equals(PostcardTag.GREETING_POSTCARD.toString())) {
                        Postcard postcard = buildPostcard(reader);
                        postcards.add(postcard);
                    }
                }
            }
        } catch (XMLStreamException e) {
            logger.log(Level.ERROR, "Error while parsing file {}; message {}", filePath, e.getMessage());
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "Error while finding file {}; message {}", filePath, e.getMessage());
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading file {}; message {}", filePath, e.getMessage());
        }
        logger.log(Level.INFO, "file were successfully parsed by StAX");
    }

    private Postcard buildPostcard(XMLStreamReader reader) throws XMLStreamException {
        Postcard postcard = reader.getLocalName().equals(PostcardTag.GREETING_POSTCARD.toString()) ?
                new GreetingPostcard() :
                new AdvertisingPostcard();
        String data = reader.getAttributeValue(null, PostcardTag.ID.toString());
        postcard.setId(Integer.parseInt(data));
        data = reader.getAttributeValue(null, PostcardTag.AUTHOR.toString());
        if (data == null) {
            postcard.setAuthor(Postcard.DEFAULT_AUTHOR);
        } else {
            postcard.setAuthor(data);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName().toUpperCase(Locale.ROOT).replace(HYPHEN, UNDERSCORE);
                    buildPostcardProperties(reader, name, postcard);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(PostcardTag.ADVERTISING_POSTCARD.toString())
                            || name.equals(PostcardTag.GREETING_POSTCARD.toString())) {
                        return postcard;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <postcard>");
    }

    private void buildPostcardProperties(XMLStreamReader reader, String name, Postcard postcard) throws XMLStreamException {
        String data = getXMLText(reader);
        switch (PostcardTag.valueOf(name)) {
            case THEME -> postcard.setTheme(data);
            case SENT -> postcard.setSent(Boolean.parseBoolean(data));
            case COUNTRY -> postcard.setCountry(CountryType.valueOf(data.toUpperCase(Locale.ROOT)));
            case SENT_DATE -> postcard.setSentDate(LocalDateTime.parse(data));
            case VALUABLE -> postcard.setValuable(ValuableType.valueOf(data.toUpperCase(Locale.ROOT)));
            case HOLIDAY -> ((GreetingPostcard) postcard)
                    .setHoliday(HolidayType.valueOf(data.toUpperCase(Locale.ROOT)));
            case ORGANIZATION -> ((AdvertisingPostcard) postcard).setOrganization(data);
            default -> throw new XMLStreamException("Unknown element in tag <postcard>");
        }
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
