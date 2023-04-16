package io.github.mroncatto.itflow.domain.computer.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComputerStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    @NotNull(message = "The brand name field is required")
    @Size(max = 45, message = "The brand name field must contain max 45 characters")
    private String brandName;

    @Column(length = 25)
    @NotNull(message = "The transfer rate field is required")
    @Size(max = 25, message = "The transfer rate field must contain max 25 characters")
    private String transferRate;

    @Column(length = 25)
    @NotNull(message = "The type field is required")
    @Size(max = 25, message = "The type field must contain max 25 characters")
    private String type;

    @Column(nullable = false)
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ComputerStorage that = (ComputerStorage) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
