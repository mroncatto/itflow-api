package io.github.mroncatto.itflow.domain.computer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComputerCpu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String brandName;

    @Column(length = 45)
    private String model;

    @Column(length = 25)
    private String generation;

    @Column(length = 25)
    private String socket;

    @Column(length = 25)
    private String core;

    @Column(length = 25)
    private String thread;

    @Column(length = 25)
    private String frequency;

    @Column(length = 25)
    private String fsb;

    @Column(nullable = false)
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ComputerCpu that = (ComputerCpu) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
