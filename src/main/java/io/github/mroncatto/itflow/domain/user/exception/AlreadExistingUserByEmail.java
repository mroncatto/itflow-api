package io.github.mroncatto.itflow.domain.user.exception;

public class AlreadExistingUserByEmail extends Exception {
    public AlreadExistingUserByEmail(String message) {
        super(message);
    }
}
