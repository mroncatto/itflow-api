package io.github.mroncatto.itflow.domain.staff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffDto {

    public interface StaffView {
        interface StaffPost {
        }

        interface StaffPut {
        }
    }

    @NotBlank(groups = StaffView.StaffPut.class, message = "The id field is required")
    @JsonView(StaffView.StaffPut.class)
    private Long id;

    @NotEmpty(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class}, message = "The name field is required")
    @Size(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class}, min = 5, max = 75, message = "The name field must contain between 5 and 75 characters")
    private String fullName;

    @Size(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class}, min = 5, max = 45, message = "The name field must contain between 5 and 45 characters")
    private String email;

    @NotNull(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class}, message = "The name field is required")
    private Department department;

    @NotNull(groups = {StaffView.StaffPost.class, StaffView.StaffPut.class}, message = "The occupation field is required")
    private Occupation occupation;

    @JsonView({StaffView.StaffPost.class, StaffView.StaffPut.class})
    private User user;

    @JsonView({StaffView.StaffPost.class, StaffView.StaffPut.class})
    private boolean active;
}
