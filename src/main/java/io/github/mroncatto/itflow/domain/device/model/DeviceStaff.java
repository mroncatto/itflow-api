package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.abstracts.Auditable;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
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
public class DeviceStaff extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "device_id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false)
    private Device device;

    @NotNull(message = "The staff field is required")
    @ManyToOne(optional = false)
    private Staff staff;

    @Column(length = 45)
    @Size(max = 45, message = "The login field must contain max 45 characters")
    private String login;
}
