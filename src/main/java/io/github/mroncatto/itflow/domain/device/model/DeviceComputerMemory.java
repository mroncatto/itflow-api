package io.github.mroncatto.itflow.domain.device.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
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
}
