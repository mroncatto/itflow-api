package io.github.mroncatto.itflow.domain.software.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.abstracts.Auditable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Software extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name field is required")
    @Column(length = 45)
    @Size(max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @Column(length = 45)
    @Size(max = 45, message = "The developer field must contain max 45 characters")
    private String developer;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "software", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"software", "keys"})
    @ToString.Exclude
    private List<SoftwareLicense> licenses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Software software = (Software) o;
        return id != null && Objects.equals(id, software.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
