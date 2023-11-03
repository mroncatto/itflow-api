package io.github.mroncatto.itflow.domain.device.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComputerMemoryPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "device_computer_id", insertable = false, updatable = false)
    private long deviceComputer;

    @Column(name = "computer_memory_id", insertable = false, updatable = false)
    private long computerMemory;
}
