package io.github.mroncatto.itflow.infrastructure.web.advice;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CustomHttpResponse {
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
