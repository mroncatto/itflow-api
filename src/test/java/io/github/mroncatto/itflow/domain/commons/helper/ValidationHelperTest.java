package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    @Test
    void shouldBeNonNull() {
        assertTrue(ValidationHelper.nonNull(new Object()));
    }

    @Test
    void shouldBeNull() {
        assertTrue(ValidationHelper.nonNull(Object.class));
    }

    @Test
    void shouldStartsWith() {
        assertTrue(ValidationHelper.startWith("Testing ", "Testing "));
    }
}