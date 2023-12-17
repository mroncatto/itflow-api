package io.github.mroncatto.itflow.domain.commons.helper;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneratorHelper {

    private GeneratorHelper() {}

    public static String generateRandomAlphanumeric(int size, boolean uppercase) {
        String random = RandomStringUtils.randomAlphanumeric(size);
        return uppercase ? random.toUpperCase() : random;
    }
}
