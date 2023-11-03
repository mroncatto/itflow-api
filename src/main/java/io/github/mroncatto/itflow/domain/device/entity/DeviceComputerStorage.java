package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerStoragePK;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceComputerStorage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeviceComputerStoragePK id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_computer_id", insertable = false, updatable = false)
    private DeviceComputer deviceComputer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "computer_storage_id", updatable = false, insertable = false)
    private ComputerStorage computerStorage;

    @Column(length = 11, nullable = false)
    private int size;

    public void addEmbeddedKey() {
        if (nonNull(computerStorage) && nonNull(deviceComputer)) {
            this.id = new DeviceComputerStoragePK();
            this.id.setComputerStorage(computerStorage.getId());
            this.id.setDeviceComputer(deviceComputer.getId());
        }
    }
}
