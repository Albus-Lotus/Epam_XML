package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.Postcard;
import edu.kravchenko.xml.exception.PostcardException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class PostcardSaxBuilder implements PostcardBuilder {
    private static final Logger logger = LogManager.getLogger();
    private List<Postcard> postcards;

    public PostcardSaxBuilder() throws PostcardException {
    }

    @Override
    public void buildPostcards(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            PostcardHandler postcardHandler = new PostcardHandler();
            reader.setContentHandler(postcardHandler);
            reader.setErrorHandler(new PostcardErrorHandler());
            reader.parse(filePath);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Error while parsing file {}; message {}", filePath, e.getMessage());
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading file {}; message {}", filePath, e.getMessage());
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Error while configuration; message {}", e.getMessage());
        }
    }

    @Override
    public List<Postcard> getPostcards() {
        return postcards;
    }
}
