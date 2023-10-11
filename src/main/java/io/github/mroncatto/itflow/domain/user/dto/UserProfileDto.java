package io.github.mroncatto.itflow.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDto {

    public interface UserProfileView {
        interface ProfileUpdate {}
    }

    @NotEmpty(message = "The name field is required", groups = {UserProfileDto.UserProfileView.ProfileUpdate.class})
    @Size(min = 5, max = 100, groups = {UserProfileDto.UserProfileView.ProfileUpdate.class}, message = "The name field must contain between 5 and 100 characters")
    private String fullName;

    @NotEmpty(message = "The email field is required", groups = {UserProfileDto.UserProfileView.ProfileUpdate.class})
    @Size(min = 5, max = 100, groups = {UserProfileDto.UserProfileView.ProfileUpdate.class}, message = "The email field must contain between 10 and 100 characters")
    private String email;

}
