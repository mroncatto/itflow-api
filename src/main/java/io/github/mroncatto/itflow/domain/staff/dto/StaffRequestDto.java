package io.github.mroncatto.itflow.domain.staff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.domain.user.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffRequestDto {

    public interface StaffView {
        interface StaffPost {
        }

        interface StaffPut {
        }
    }

    @NotNull(groups = StaffView.StaffPut.class, message = "[{field.id}] {validation.required}")
    @JsonView(StaffView.StaffPut.class)
    private UUID id;

    @NotEmpty(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class},
            message = "[{field.name}] {validation.required}")
    @Size(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class},
            min = 5, max = 75, message = "[{field.full_name}] {validation.between}")
    private String fullName;

    @Size(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class},
            min = 5, max = 45, message = "[{field.email}] {validation.between}")
    private String email;

    @NotNull(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class},
            message = "[{field.department}] {validation.required}")
    private Department department;

    @NotNull(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class},
            message = "[{field.occupation}] {validation.required}")
    private Occupation occupation;

    @JsonView({StaffView.StaffPost.class, StaffView.StaffPut.class})
    private User user;

    @JsonView({StaffView.StaffPost.class, StaffView.StaffPut.class})
    private boolean active;
}
