package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    @Test
    void shouldStartsWith() {
        assertTrue(ValidationHelper.startWith("Testing ", "Testing "));
    }
}