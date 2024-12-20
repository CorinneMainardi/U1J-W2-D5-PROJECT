package it.epicode.exceptions;

public class ExistingIsbn extends RuntimeException {
    public ExistingIsbn(String message) {
        super(message);
    }
}
