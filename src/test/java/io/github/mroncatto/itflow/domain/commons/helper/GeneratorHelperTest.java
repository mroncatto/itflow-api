package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorHelperTest {

    @Test
    void shouldGenerateRandomDigitAlphanumeric() {
        String random = GeneratorHelper.generateRandomAlphanumeric(10, true);
        assertTrue(random.length() == 10);
    }
}