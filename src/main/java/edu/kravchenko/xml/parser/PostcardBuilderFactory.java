package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.exception.PostcardException;

import java.util.Locale;

public class PostcardBuilderFactory {
    private static final PostcardBuilderFactory INSTANCE = new PostcardBuilderFactory();

    private PostcardBuilderFactory() {
    }

    public PostcardBuilder getPostcardBuilder(ParserType parserType) {
        switch (parserType) {
            case SAX -> {
                return new PostcardSaxBuilder();
            }
            case STAX -> {
                return new PostcardStaxBuilder();
            }
            case DOM -> {
                return new PostcardDomBuilder();
            }
            default -> {
                throw new EnumConstantNotPresentException(
                        parserType.getDeclaringClass(), parserType.name());
            }
        }
    }

    public static PostcardBuilderFactory getInstance() {
        return INSTANCE;
    }
}
