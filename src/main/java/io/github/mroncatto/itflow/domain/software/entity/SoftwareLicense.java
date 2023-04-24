package io.github.mroncatto.itflow.domain.software.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
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
public class SoftwareLicense extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 75, nullable = false)
    @NotNull(message = "The description field is required")
    @Size(max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @Column(length = 45)
    @Size(max = 45, message = "The code field must contain max 45 characters")
    private String code;

    @Temporal(TemporalType.DATE)
    private Date expireAt;

    @NotNull(message = "The software field is required")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("licenses")
    private Software software;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "softwareLicense", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"softwareLicense"})
    @ToString.Exclude
    private List<SoftwareLicenseKey> keys;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SoftwareLicense that = (SoftwareLicense) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
