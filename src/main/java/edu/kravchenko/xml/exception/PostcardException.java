package edu.kravchenko.xml.exception;

public class PostcardException extends Exception {
    public PostcardException() {
    }

    public PostcardException(String message) {
        super(message);
    }

    public PostcardException(Throwable cause) {
        super(cause);
    }

    public PostcardException(String message, Throwable cause) {
        super(message, cause);
    }
}
