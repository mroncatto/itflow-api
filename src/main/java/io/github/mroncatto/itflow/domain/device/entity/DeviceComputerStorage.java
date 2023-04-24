package io.github.mroncatto.itflow.domain.device.entity;

import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The device computer field is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "device_computer_id")
    private DeviceComputer deviceComputer;

    @ManyToOne(optional = false)
    private ComputerStorage computerStorage;

    @Column(length = 25)
    @NotNull(message = "The size field is required")
    @Size(max = 25, message = "The size field must contain max 25 characters")
    private String size;
}
