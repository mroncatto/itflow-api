package io.github.mroncatto.itflow.domain.software.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerSoftware;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString
public class SoftwareLicenseKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SoftwareLicense softwareLicense;

    @OneToMany(mappedBy = "softwareLicenseKey", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer", "software", "softwareLicenseKey"})
    private List<DeviceComputerSoftware> assignedLicenses;

    private int volume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SoftwareLicenseKey that = (SoftwareLicenseKey) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
