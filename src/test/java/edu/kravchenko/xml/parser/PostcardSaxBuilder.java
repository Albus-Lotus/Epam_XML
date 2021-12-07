package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.*;
import edu.kravchenko.xml.exception.PostcardException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostcardSaxBuilder {
    private static PostcardBuilder postcardSaxBuilder;
    private static final Set<Postcard> expected = new HashSet<>();

    @BeforeAll
    public static void setUp() {
        PostcardBuilderFactory builderFactory = PostcardBuilderFactory.getInstance();
        postcardSaxBuilder = builderFactory.getPostcardBuilder(ParserType.SAX);
        Postcard postcard = new GreetingPostcard(
                10,
                "landscape",
                false,
                CountryType.BELARUS,
                LocalDateTime.parse("2001-11-17T06:00:00"),
                ValuableType.THEMATIC,
                HolidayType.CHRISTMAS
        );
        expected.add(postcard);
        postcard = new GreetingPostcard(
                13,
                "landscape",
                false,
                CountryType.ENGLAND,
                LocalDateTime.parse("1998-11-17T06:00:00"),
                ValuableType.COLLECTIBLE,
                HolidayType.CHRISTMAS
        );
        postcard.setAuthor("Polina");
        expected.add(postcard);
        postcard = new AdvertisingPostcard(
                85,
                "pizza",
                false,
                CountryType.ENGLAND,
                LocalDateTime.parse("2001-11-09T08:00:00"),
                ValuableType.COLLECTIBLE,
                "dodo"
        );
        postcard.setAuthor("Andrew");
        expected.add(postcard);
    }

    @Test
    public void buildPostcards() throws PostcardException {
        File file = new File(getClass().getClassLoader().getResource("files/postcards.xml").getFile());
        String filePath = file.getAbsolutePath();
        postcardSaxBuilder.buildPostcards(filePath);
        Set<Postcard> actual = postcardSaxBuilder.getPostcards();
        assertEquals(expected, actual);
        //assertThat(actual).containsExactlyElementsOf(expected);
    }
}
