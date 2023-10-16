package io.github.mroncatto.itflow.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDto {
    public interface UserView {
        interface UserPost {}
        interface UserPut {}
    }

    @NotEmpty(message = "[{field.full_name}] {validation.required}", groups = {UserRequestDto.UserView.UserPost.class, UserRequestDto.UserView.UserPut.class})
    @Size(min = 5, max = 100, groups = {UserRequestDto.UserView.UserPost.class, UserRequestDto.UserView.UserPut.class},
            message = "[{field.full_name}] {validation.between}")
    private String fullName;

    @NotEmpty(message = "[{field.email}] {validation.required}", groups = {UserRequestDto.UserView.UserPost.class, UserRequestDto.UserView.UserPut.class})
    @Size(min = 5, max = 100, groups = {UserRequestDto.UserView.UserPost.class, UserRequestDto.UserView.UserPut.class},
            message = "[{field.email}] {validation.between}")
    @Email(message = "{validation.email}", groups = {UserRequestDto.UserView.UserPost.class, UserRequestDto.UserView.UserPut.class})
    private String email;

    @NotEmpty(message = "[{field.username}] {validation.required}", groups = {UserRequestDto.UserView.UserPost.class})
    @Size(min = 5, max = 45, groups = {UserRequestDto.UserView.UserPost.class},
            message = "[{field.username}] {validation.between}")
    private String username;

    @JsonView(UserRequestDto.UserView.UserPut.class)
    private Staff staff;

    @JsonView({UserRequestDto.UserView.UserPut.class, UserRequestDto.UserView.UserPost.class})
    private boolean active;

}
