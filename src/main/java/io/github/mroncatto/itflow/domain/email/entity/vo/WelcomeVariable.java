package io.github.mroncatto.itflow.domain.email.entity.vo;

import java.util.Arrays;
import java.util.List;

public enum WelcomeVariable {
    PASSWORD;


    public static List<String> getValues(){
        return Arrays.stream(values())
                .map(WelcomeVariable::toString)
                .toList();
    }
}
