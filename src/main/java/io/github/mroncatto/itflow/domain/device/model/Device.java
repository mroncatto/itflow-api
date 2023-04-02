package io.github.mroncatto.itflow.domain.device.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.mroncatto.itflow.domain.abstracts.Auditable;
import io.github.mroncatto.itflow.domain.company.model.Department;
import lombok.Getter;
import lombok.Setter;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    @Size(max = 45, message = "The code field must contain max 45 characters")
    private String code;

    @Column(length = 45)
    @Size(max = 45, message = "The tag field must contain max 45 characters")
    private String tag;

    @Column(length = 45)
    @Size(max = 45, message = "The serial number field must contain max 45 characters")
    private String serialNumber;

    @Column(length = 75, nullable = false)
    @Size(max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @NotNull(message = "The hostname field is required")
    @Column(length = 25, nullable = false)
    @Size(min = 2, max = 25, message = "The hostname field must contain between 5 and 25 characters")
    private String hostname;

    @NotNull(message = "The device category field is required")
    @ManyToOne(optional = false)
    private DeviceCategory deviceCategory;

    @NotNull(message = "The device department field is required")
    @ManyToOne(optional = false)
    private Department department;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties("device")
    private DeviceStaff deviceStaff;

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
}
