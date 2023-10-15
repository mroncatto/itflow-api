package io.github.mroncatto.itflow.domain.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
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
public class SoftwareRequestDto {

    public interface SoftwareView {
        interface SoftwarePost {
        }

        interface SoftwarePut {
        }
    }

    @JsonView(SoftwareView.SoftwarePut.class)
    private Long id;

    @NotNull(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            message = "[{field.name}] {validation.required}")
    @Size(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            max = 45, message = "[{field.name}] {validation.max}")
    private String name;

    @Size(groups = {SoftwareView.SoftwarePut.class, SoftwareView.SoftwarePost.class},
            max = 45, message = "[{field.developer}] {validation.max}")
    private String developer;

    @JsonView(SoftwareView.SoftwarePost.class)
    private boolean active;

}
