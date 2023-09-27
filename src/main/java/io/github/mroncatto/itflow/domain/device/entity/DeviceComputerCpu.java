package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerCpuPK;
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

import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Entity
@Table
@Getter
@Setter
@ToString
public class DeviceComputerCpu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeviceComputerCpuPK id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_computer_id", updatable = false, insertable = false)
    private DeviceComputer deviceComputer;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, insertable = false)
    private ComputerCpu computerCpu;

    @Column(length = 11, nullable = false)
    @NotNull(message = "The vcpu field is required")
    @Size(min = 1, max = 11, message = "The vcpu field must contain between 1 and 11 digits")
    private String vcpu;

    @Column(length = 11, nullable = false)
    @NotNull(message = "The unit field is required")
    @Size(min = 1, max = 11, message = "The unit field must contain between 1 and 11 digits")
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceComputerCpu that = (DeviceComputerCpu) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addEmbeddedKey() {
        if (nonNull(computerCpu) && nonNull(deviceComputer)) {
            id.setComputerCpu(computerCpu.getId());
            id.setDeviceComputer(deviceComputer.getId());
        }
    }
}
