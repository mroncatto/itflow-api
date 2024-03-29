package io.github.mroncatto.itflow.domain.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name field is required")
    @Column(length = 45, nullable = false)
    @Size(min = 5, max = 45, message = "The name field must contain between 5 and 45 characters")
    private String name;

    @NotNull(message = "The branch field is required")
    @ManyToOne(optional = false)
    private Branch branch;

    @Column(nullable = false)
    private boolean active;
}
