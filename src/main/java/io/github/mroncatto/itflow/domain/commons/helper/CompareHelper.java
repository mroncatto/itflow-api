package io.github.mroncatto.itflow.domain.commons.helper;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class CompareHelper {

    private CompareHelper() {}

    public static boolean biggerThan(Date dateA, Date dateB){
        boolean result = false;
        try {
            result = dateA.compareTo(dateB) > 0;
        } catch (Exception e){
            log.error("Error while comparing dates: {}", e.getMessage());
        }
        return result;
    }

    public static boolean biggerThan(Long valueA, Long valueB) {
        boolean result = false;
        try {
            result = valueA>valueB;
        } catch (Exception e){
            log.error("Error while comparing long: {}", e.getMessage());
        }
        return result;
    }

    public static boolean biggerThanZero(Long value) {
        return value > 0;
    }

    public static boolean match(Object a, Object b){
        return a.equals(b);
    }

    public static boolean match(String a, String b){
        return a.equals(b);
    }

    public static boolean distinct(Object a, Object b){
        return !a.equals(b);
    }

    public static boolean distinct(String a, String b){
        return !a.equals(b);
    }

    public static boolean isNumber(String a) {
        return a.matches("\\d+");
    }
}
