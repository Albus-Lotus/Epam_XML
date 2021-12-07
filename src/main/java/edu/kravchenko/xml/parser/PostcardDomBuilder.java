package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.*;
import edu.kravchenko.xml.exception.PostcardException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class PostcardDomBuilder extends PostcardBuilder {
    private static final Logger logger = LogManager.getLogger();
    private DocumentBuilder docBuilder;

    public PostcardDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Configuration failed", e);
        }
    }

    @Override
    public void buildPostcards(String filePath) throws PostcardException {
        Document doc;
        try {
            doc = docBuilder.parse(filePath);
            Element root = doc.getDocumentElement();
            createPostcards(root, PostcardTag.GREETING_POSTCARD);
            createPostcards(root, PostcardTag.ADVERTISING_POSTCARD);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading file {}; message {}", filePath, e.getMessage());
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Error while parsing file {}; message {}", filePath, e.getMessage());
        }
        logger.log(Level.INFO, "file were successfully parsed by DOM");
    }

    public void createPostcards(Element root, PostcardTag postcardType) throws PostcardException {
        NodeList postcardList = root.getElementsByTagName(postcardType.toString());
        for (int i = 0; i < postcardList.getLength(); i++) {
            Element postcardElement = (Element) postcardList.item(i);
            Postcard postcard = buildPostcard(postcardElement, postcardType);
            postcards.add(postcard);
        }
    }

    private Postcard buildPostcard(Element postcardElement, PostcardTag postcardType) throws PostcardException {
        Postcard postcard;
        switch (postcardType) {
            case GREETING_POSTCARD -> {
                postcard = new GreetingPostcard();
                String holiday = getElementTextContent(postcardElement, PostcardTag.HOLIDAY.toString());
                ((GreetingPostcard) postcard).setHoliday(HolidayType.valueOf(holiday.toUpperCase(Locale.ROOT)));
            }
            case ADVERTISING_POSTCARD -> {
                postcard = new AdvertisingPostcard();
                ((AdvertisingPostcard) postcard)
                        .setOrganization(getElementTextContent(postcardElement, PostcardTag.ORGANIZATION.toString()));
            }
            default -> throw new PostcardException("invalid tag");
        }
        String data = postcardElement.getAttribute(PostcardTag.ID.toString());
        postcard.setId(Integer.parseInt(data));
        data = postcardElement.getAttribute(PostcardTag.AUTHOR.toString());
        if (data.isBlank()) {
            postcard.setAuthor(Postcard.DEFAULT_AUTHOR);
        } else {
            postcard.setAuthor(data);
        }
        data = getElementTextContent(postcardElement, PostcardTag.THEME.toString());
        postcard.setTheme(data);
        data = getElementTextContent(postcardElement, PostcardTag.SENT.toString());
        postcard.setSent(Boolean.parseBoolean(data));
        data = getElementTextContent(postcardElement, PostcardTag.COUNTRY.toString());
        postcard.setCountry(CountryType.valueOf(data.toUpperCase(Locale.ROOT)));
        data = getElementTextContent(postcardElement, PostcardTag.SENT_DATE.toString());
        postcard.setSentDate(LocalDateTime.parse(data));
        data = getElementTextContent(postcardElement, PostcardTag.VALUABLE.toString());
        postcard.setValuable(ValuableType.valueOf(data.toUpperCase(Locale.ROOT)));
        return postcard;
    }

    private String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
