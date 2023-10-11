package io.github.mroncatto.itflow.domain.device.dto;

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
public class DeviceCategoryDto {

    public interface DeviceCategoryView {
        interface DeviceCategoryPut {}
        interface DeviceCategoryPost {}
    }

    @JsonView(DeviceCategoryView.DeviceCategoryPut.class)
    private Long id;

    @NotNull(groups = {DeviceCategoryView.DeviceCategoryPost.class, DeviceCategoryView.DeviceCategoryPut.class}, message = "The name field is required")
    @Size(groups = {DeviceCategoryView.DeviceCategoryPost.class, DeviceCategoryView.DeviceCategoryPut.class}, max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @JsonView(DeviceCategoryView.DeviceCategoryPost.class)
    private boolean active;
}
