package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerCpuPK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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
    @JoinColumn(name = "computer_cpu_id", updatable = false, insertable = false)
    private ComputerCpu computerCpu;

    @Column(length = 11, nullable = false)
    private String core;

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
        if (Objects.nonNull(computerCpu) && Objects.nonNull(deviceComputer)) {
            this.id = new DeviceComputerCpuPK();
            this.id.setComputerCpu(computerCpu.getId());
            this.id.setDeviceComputer(deviceComputer.getId());
        }
    }
}
