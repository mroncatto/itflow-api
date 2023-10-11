package io.github.mroncatto.itflow.domain.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoftwareDto {

    public interface SoftwareView {
        interface SoftwarePost {
        }

        interface SoftwarePut {
        }
    }

    @JsonView(SoftwareView.SoftwarePut.class)
    private Long id;

    @NotNull(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            message = "The name field is required")
    @Size(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @Size(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            max = 45, message = "The developer field must contain max 45 characters")
    private String developer;

    @JsonView(SoftwareView.SoftwarePost.class)
    private boolean active;

}
