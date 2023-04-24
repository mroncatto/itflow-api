package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
public class DeviceStaff extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "device_id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false)
    private Device device;

    @NotNull(message = "The staff field is required")
    @ManyToOne(optional = false)
    private Staff staff;

    @Column(length = 45)
    @Size(max = 45, message = "The login field must contain max 45 characters")
    private String login;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceStaff that = (DeviceStaff) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
