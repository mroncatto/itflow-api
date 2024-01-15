package io.github.mroncatto.itflow.domain.company.entity;

import io.github.mroncatto.itflow.domain.company.dto.BranchResDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;

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
public class Branch implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Company company;

    @Column(nullable = false)
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Branch branch = (Branch) o;
        return id != null && Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public BranchResDto response()
    {
        var response = BranchResDto.builder().build();
        BeanUtils.copyProperties(this, response);
        return response;
    }
}
