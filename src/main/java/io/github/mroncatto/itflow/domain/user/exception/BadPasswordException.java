package io.github.mroncatto.itflow.domain.user.exception;

public class BadPasswordException extends Exception {
    public BadPasswordException(String message) {
        super(message);
    }
}
