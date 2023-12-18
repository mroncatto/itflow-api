package io.github.mroncatto.itflow.domain.software.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

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

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String developer;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "software", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"software", "keys"})
    @ToString.Exclude
    private Set<SoftwareLicense> licenses;

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
