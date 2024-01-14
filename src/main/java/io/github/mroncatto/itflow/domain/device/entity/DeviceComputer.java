package io.github.mroncatto.itflow.domain.device.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
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
public class DeviceComputer extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "device_id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false)
    private Device device;

    @ManyToOne(optional = false)
    private ComputerCategory computerCategory;

    @Column(length = 75)
    private String description;

    @Column(nullable = false)
    private boolean virtual;

    @OneToMany(mappedBy = "deviceComputer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer"})
    private List<DeviceComputerCpu> computerCpuList;

    @OneToMany(mappedBy = "deviceComputer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer"})
    private List<DeviceComputerMemory> computerMemoryList;

    @OneToMany(mappedBy = "deviceComputer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer"})
    private List<DeviceComputerStorage> computerStorageList;

    @OneToMany(mappedBy = "deviceComputer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer", "softwareLicenseKey"})
    private List<DeviceComputerSoftware> computerSoftwareList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceComputer that = (DeviceComputer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
