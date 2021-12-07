package edu.kravchenko.xml.parser;

import edu.kravchenko.xml.entity.Postcard;
import edu.kravchenko.xml.exception.PostcardException;

import java.util.HashSet;
import java.util.Set;

public abstract class PostcardBuilder {
    protected Set<Postcard> postcards;

    public PostcardBuilder() {
        postcards = new HashSet<Postcard>();
    }

    public PostcardBuilder(Set<Postcard> postcards) {
        this.postcards = postcards;
    }

    public Set<Postcard> getPostcards() {
        return postcards;
    }

    public abstract void buildPostcards(String filename) throws PostcardException;
}
