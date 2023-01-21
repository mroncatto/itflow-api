package io.github.mroncatto.itflow.domain.email.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailEventData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private EmailSendEvent sendEvent;

    @Column(nullable = false)
    private String variableName;

    @Column(nullable = false)
    private String variableValue;
}
