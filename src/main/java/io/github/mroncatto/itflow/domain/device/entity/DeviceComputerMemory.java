package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString
public class DeviceComputerMemory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The device computer field is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "device_computer_id")
    private DeviceComputer deviceComputer;

    @NotNull(message = "The computer memory field is required")
    @ManyToOne(optional = false)
    private ComputerMemory computerMemory;

    @NotNull(message = "The unit field is required")
    private int unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceComputerMemory that = (DeviceComputerMemory) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
