package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.abstracts.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
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
    private DeviceComputerCateg computerCateg;

    @Column(length = 75)
    @Size(max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @Column(nullable = false)
    private boolean virtual;
}
