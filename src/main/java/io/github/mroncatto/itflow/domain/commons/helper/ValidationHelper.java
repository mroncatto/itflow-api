package io.github.mroncatto.itflow.domain.commons.helper;

import java.util.Objects;

public class ValidationHelper {

    public static boolean notNull(Object o){
        return Objects.nonNull(o);
    }
    public static boolean isNull(Object o){
        return !Objects.nonNull(o);
    }

    public static boolean startWith(String text, String sentence){
        return text.startsWith(sentence);
    }
}
