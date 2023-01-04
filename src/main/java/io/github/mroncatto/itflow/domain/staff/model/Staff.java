package io.github.mroncatto.itflow.domain.staff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.mroncatto.itflow.domain.company.model.Department;
import io.github.mroncatto.itflow.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Staff implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "The name field is required")
    @Column(length = 75, nullable = false)
    @Size(min = 5, max = 75, message = "The name field must contain between 5 and 75 characters")
    private String fullName;

    @Column(length = 45)
    @Size(min = 5, max = 45, message = "The name field must contain between 5 and 45 characters")
    private String email;

    @NotNull(message = "The name field is required")
    @ManyToOne(optional = false)
    private Department department;

    @NotNull(message = "The occupation field is required")
    @ManyToOne(optional = false)
    private Occupation occupation;

    @OneToOne(mappedBy = "staff")
    @JsonIgnoreProperties("staff")
    private User user;

    @Column(nullable = false)
    private boolean active;

}
