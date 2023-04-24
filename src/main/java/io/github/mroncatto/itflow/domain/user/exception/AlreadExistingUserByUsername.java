package io.github.mroncatto.itflow.domain.user.exception;

public class AlreadExistingUserByUsername extends Exception {
    public AlreadExistingUserByUsername(String message) {
        super(message);
    }
}
