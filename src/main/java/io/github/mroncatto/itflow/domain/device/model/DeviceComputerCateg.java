package io.github.mroncatto.itflow.domain.device.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
public class DeviceComputerCateg implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    @Size(max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @Column(nullable = false)
    private boolean active;
}
