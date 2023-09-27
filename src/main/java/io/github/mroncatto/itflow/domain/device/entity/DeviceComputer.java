package io.github.mroncatto.itflow.domain.device.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
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
public class DeviceComputer extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "device_id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false)
    private Device device;

    @NotNull(message = "The computer category field is required")
    @ManyToOne(optional = false)
    private ComputerCategory computerCategory;

    @Column(length = 75)
    @Size(max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @Column(nullable = false)
    private boolean virtual;

    @OneToOne()
    @JoinColumn(name = "device_id", referencedColumnName = "device_computer_id")
    @ToString.Exclude
    @JsonIgnoreProperties({"deviceComputer"})
    private DeviceComputerCpu cpu;

    // FIXME: REMOVER CHAVE COMPOSTA, ESTA CAUSANDO PROBLEMAS !!!!!!!!!!!!!!!1

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
