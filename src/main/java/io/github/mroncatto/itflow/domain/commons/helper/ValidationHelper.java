package io.github.mroncatto.itflow.domain.commons.helper;

import java.util.Objects;

public class ValidationHelper {

    public static boolean nonNull(Object o){
        return Objects.nonNull(o);
    }
    public static boolean nonNullAndNonEmpty(Object o){
        return Objects.nonNull(o) && o.toString().length() > 0;
    }
    public static boolean isNull(Object o){
        return Objects.isNull(o);
    }
    public static boolean isNullOrEmpty(Object o){
        return Objects.isNull(o) || o.toString().length() == 0;
    }

    public static boolean startWith(String text, String sentence){
        return text.startsWith(sentence);
    }
}
