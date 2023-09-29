package io.github.mroncatto.itflow.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.mroncatto.itflow.domain.commons.model.Auditable;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"id"})
public class User extends Auditable<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "The name field is required")
    @Column(length = 100, nullable = false)
    @Size(min = 5, max = 100, message = "The name field must contain between 5 and 100 characters")
    private String fullName;

    @Column()
    private String avatar;

    @NotNull(message = "The email field is required")
    @Size(min = 5, max = 100, message = "The email field must contain between 10 and 100 characters")
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "The username field is required")
    @Size(min = 5, max = 45, message = "The username field must contain between 5 and 45 characters")
    @Column(length = 45, nullable = false, unique = true)
    private String username;

    @Column(length = 128, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean nonLocked;

    @Column(nullable = false)
    private boolean passwordNonExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Role> role;

    @OneToOne()
    @JsonIgnoreProperties("user")
    private Staff staff;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
