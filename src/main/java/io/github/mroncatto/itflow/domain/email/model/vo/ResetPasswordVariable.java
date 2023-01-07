package io.github.mroncatto.itflow.domain.email.model.vo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ResetPasswordVariable {
    PASSWORD;


    public static List<String> getValues(){
        return Arrays.stream(values())
                .map(ResetPasswordVariable::toString)
                .collect(Collectors.toList());
    }
}
