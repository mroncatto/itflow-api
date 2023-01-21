package io.github.mroncatto.itflow.domain.abstracts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractServiceTest extends AbstractService {

    @Test
    void shouldValidateNullValues() {
        boolean validate = false;

        try {
            validateNullFields(new Object(), null );
        } catch (Exception e) {
            validate = true;
        }

        assertTrue(validate);
    }

    @Test
    void shouldValidateEmptyFields() {
        boolean validate = false;

        try {
            validateEmptyFields("test", "");
        } catch (Exception e) {
            validate = true;
        }

        assertTrue(validate);
    }

    @Test
    void shouldValidateEmailField() {
        boolean validate;

        try {
            validate = false;
            validateEmailField("teste.teste.com");
        } catch (Exception e) {
            validate = true;
        }

        assertTrue(validate);

        try {
            validate = false;
            validateEmailField("teste@teste.com");
        } catch (Exception e) {
            validate = true;
        }

        assertFalse(validate);
    }
}