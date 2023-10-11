package io.github.mroncatto.itflow.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    public interface UserView {
        interface UserPost {}
        interface UserPut {}
    }

    @NotEmpty(message = "The name field is required", groups = {UserDto.UserView.UserPost.class, UserDto.UserView.UserPut.class})
    @Size(min = 5, max = 100, groups = {UserDto.UserView.UserPost.class, UserDto.UserView.UserPut.class}, message = "The name field must contain between 5 and 100 characters")
    private String fullName;

    @NotEmpty(message = "The email field is required", groups = {UserDto.UserView.UserPost.class, UserDto.UserView.UserPut.class})
    @Size(min = 5, max = 100, groups = {UserDto.UserView.UserPost.class, UserDto.UserView.UserPut.class}, message = "The email field must contain between 10 and 100 characters")
    private String email;

    @NotEmpty(message = "The username field is required", groups = {UserDto.UserView.UserPost.class})
    @Size(min = 5, max = 45, groups = {UserDto.UserView.UserPost.class}, message = "The username field must contain between 5 and 45 characters")
    private String username;

    @JsonView(UserDto.UserView.UserPut.class)
    private Staff staff;

    @JsonView({UserDto.UserView.UserPut.class, UserDto.UserView.UserPost.class})
    private boolean active;

}
