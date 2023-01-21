package io.github.mroncatto.itflow.domain.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name field is required")
    @Column(length = 45, nullable = false)
    @Size(min = 5, max = 45, message = "The name field must contain between 5 and 45 characters")
    private String name;

    @Column(length = 45)
    @Size(min = 5, max = 45, message = "The document field must contain between 5 and 45 characters")
    private String document;

    @Column(nullable = false)
    private boolean active;
}
