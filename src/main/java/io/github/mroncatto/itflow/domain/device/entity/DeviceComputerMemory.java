package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerMemoryPK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Entity
@Table
@Getter
@Setter
@ToString
public class DeviceComputerMemory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeviceComputerMemoryPK id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_computer_id", updatable = false, insertable = false)
    private DeviceComputer deviceComputer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "computer_memory_id", updatable = false, insertable = false)
    private ComputerMemory computerMemory;

    @Column(length = 11, nullable = false)
    private int modules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceComputerMemory that = (DeviceComputerMemory) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addEmbeddedKey() {
        if (nonNull(computerMemory) && nonNull(deviceComputer)) {
            this.id = new DeviceComputerMemoryPK();
            this.id.setComputerMemory(computerMemory.getId());
            this.id.setDeviceComputer(deviceComputer.getId());
        }
    }
}
