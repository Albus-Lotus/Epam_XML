package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.Postcard;
import edu.kravchenko.xml.exception.PostcardException;

import java.util.List;

public interface PostcardBuilder {
    void buildPostcards(String filePath) throws PostcardException;

    List<Postcard> getPostcards();
}
