package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.Postcard;
import edu.kravchenko.xml.exception.PostcardException;

import java.util.Locale;

public class PostcardBuilderFactory {
    private static final PostcardBuilderFactory INSTANCE = new PostcardBuilderFactory();
    private enum ParserType {
        SAX, STAX, DOM
    }

    private PostcardBuilderFactory() {
    }

    public PostcardBuilder getPostcardBuilder(String parserType) throws PostcardException {
        ParserType type = ParserType.valueOf(parserType.toUpperCase(Locale.ROOT));
        switch (type) {
            case SAX -> {
                return new PostcardSaxBuilder();
            }
            case DOM -> {
                return new PostcardDomBuilder();
            }
            default -> {
                throw new EnumConstantNotPresentException(
                        type.getDeclaringClass(), type.name());
            }
        }
    }

    public static PostcardBuilderFactory getInstance() {
        return INSTANCE;
    }
}
