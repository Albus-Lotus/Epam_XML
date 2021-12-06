package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.AdvertisingPostcard;
import edu.kravchenko.xml.entity.GreetingPostcard;
import edu.kravchenko.xml.entity.Postcard;
import edu.kravchenko.xml.entity.PostcardTag;
import edu.kravchenko.xml.exception.PostcardException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostcardDomBuilder implements PostcardBuilder {
    private static final Logger logger = LogManager.getLogger();
    private List<Postcard> postcards = new ArrayList<>();
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
    }

    public void createPostcards(Element root, PostcardTag postcardType) {
        NodeList postcardList = root.getElementsByTagName(postcardType.toString());
        for (int i = 0; i < postcardList.getLength(); i++) {
            Element postcardElement = (Element) postcardList.item(i);
            Postcard postcard = buildPostcard(postcardElement, postcardType);
            postcards.add(postcard);
        }
    }

    private Postcard buildPostcard(Element postcardElement, PostcardTag postcardType) {
        Postcard postcard;
        switch (postcardType) {
            case GREETING_POSTCARD -> {
                postcard = new GreetingPostcard();
            }
            case ADVERTISING_POSTCARD -> {
                postcard = new AdvertisingPostcard();
                postcard.setService("");
            }
            default:
                lo

        }
        return postcard;
    }

    @Override
    public List<Postcard> getPostcards() {
        return postcards;
    }
}
