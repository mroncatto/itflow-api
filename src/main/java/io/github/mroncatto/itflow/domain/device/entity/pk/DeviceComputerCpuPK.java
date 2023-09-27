package io.github.mroncatto.itflow.domain.device.entity.pk;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComputerCpuPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "device_computer_id", insertable = false, updatable = false)
    private long deviceComputer;

    @Column(name = "computer_cpu_id", insertable = false, updatable = false)
    private long computerCpu;
}
