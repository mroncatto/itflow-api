package io.github.mroncatto.itflow.domain.email.entity.vo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WelcomeVariable {
    PASSWORD;


    public static List<String> getValues(){
        return Arrays.stream(values())
                .map(WelcomeVariable::toString)
                .collect(Collectors.toList());
    }
}
