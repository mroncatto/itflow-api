package io.github.mroncatto.itflow.domain.device.model.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class DeviceComputerSoftwarePK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "device_computer_id", insertable = false, updatable = false)
    private long deviceComputer;

    @Column(name = "software_id", insertable = false, updatable = false)
    private long software;
}
