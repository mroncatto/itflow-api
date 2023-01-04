package io.github.mroncatto.itflow.domain.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    @Id
    private Long id;

    @Column(length = 30, nullable = false)
    @NotNull(message = "The role field is required")
    @Size(min = 5, max = 30, message = "(!) The role field must contain between 5 and 30 characters")
    private String role;
}
