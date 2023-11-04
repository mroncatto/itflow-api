package io.github.mroncatto.itflow.domain.device.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

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
@ToString(exclude = {"deviceStaff", "deviceComputer"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String code;

    @Column(length = 45)
    private String tag;

    @Column(length = 45)
    private String serialNumber;

    @Column(length = 75, nullable = false)
    private String description;

    @Column(length = 25, nullable = false)
    private String hostname;

    @ManyToOne(optional = false)
    private DeviceCategory deviceCategory;

    @ManyToOne(optional = false)
    private Department department;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(mappedBy = "device", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonIgnoreProperties("device")
    private DeviceStaff deviceStaff;

    @OneToOne(mappedBy = "device", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonIgnoreProperties({"device"})
    private DeviceComputer deviceComputer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Device device = (Device) o;
        return id != null && Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void updateDeviceComputer(DeviceComputer deviceComputer) {
        this.getDeviceComputer().setComputerCategory(deviceComputer.getComputerCategory());
        this.getDeviceComputer().setDescription(deviceComputer.getDescription());
        this.getDeviceComputer().setVirtual(deviceComputer.isVirtual());
    }
}
