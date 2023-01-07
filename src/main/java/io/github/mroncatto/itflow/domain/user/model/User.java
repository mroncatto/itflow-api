package io.github.mroncatto.itflow.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.mroncatto.itflow.domain.abstracts.Auditable;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean password_expired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Role> role;

    @OneToOne()
    @JsonIgnoreProperties("user")
    private Staff staff;

    public User buildForToken() {
        return User.builder()
                .username(this.username)
                .fullName(this.fullName)
                .avatar(this.avatar)
                .joinDate(this.joinDate)
                .lastLoginDate(this.lastLoginDate)
                .password_expired(this.password_expired)
                .build();
    }

}
