package io.github.mroncatto.itflow.domain.software.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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
    private String description;

    @Column(length = 45)
    private String code;

    @Temporal(TemporalType.DATE)
    private LocalDate expireAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("licenses")
    private Software software;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "softwareLicense", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public void disable() {
        this.active = false;
    }
}
