package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerSoftwarePK;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
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
public class DeviceComputerSoftware  extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeviceComputerSoftwarePK id;

    @JoinColumn(name = "device_computer_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DeviceComputer deviceComputer;

    @JoinColumn(insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Software software;

    @ManyToOne()
    //@JsonIgnoreProperties({"softwareLicenseKey", "softwareLicense.software"})
    private SoftwareLicenseKey softwareLicenseKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DeviceComputerSoftware that = (DeviceComputerSoftware) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addEmbeddedKey() {
        if (Objects.nonNull(software) && Objects.nonNull(deviceComputer)) {
            this.id = new DeviceComputerSoftwarePK();
            this.id.setDeviceComputer(deviceComputer.getId());
            this.id.setSoftware(software.getId());
        }
    }
}
