package edu.kravchenko.xml.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PostcardXmlValidatorTest {
    private static PostcardXmlValidator validator;

    @BeforeAll
    public static void setUp() {
        validator = new PostcardXmlValidator();
    }

    @Test
    public void isValidXmlFileValid() {
        File file = new File(getClass().getClassLoader().getResource("files/postcards.xsd").getFile());
        String schemaPath = file.getAbsolutePath();
        file = new File(getClass().getClassLoader().getResource("files/postcards.xml").getFile());
        String filePath = file.getAbsolutePath();
        assertTrue(validator.isValidXmlFile(filePath, schemaPath));
    }

    @Test
    public void isValidXmlFileInvalid() {
        File file = new File(getClass().getClassLoader().getResource("files/postcards.xsd").getFile());
        String schemaPath = file.getAbsolutePath();
        file = new File(getClass().getClassLoader().getResource("files/invalid_postcards.xml").getFile());
        String filePath = file.getAbsolutePath();
        assertFalse(validator.isValidXmlFile(filePath, schemaPath));
    }
}
