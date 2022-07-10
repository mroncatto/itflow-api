package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    @Test
    void notNull() {
        assertTrue(ValidationHelper.nonNull(new Object()));
    }

    @Test
    void isNull() {
        assertTrue(ValidationHelper.nonNull(Object.class));
    }

    @Test
    void startWith() {
        assertTrue(ValidationHelper.startWith("Testing ", "Testing "));
    }
}