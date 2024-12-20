package it.epicode.exceptions;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound(String message) {
        super(message);
    }
}
