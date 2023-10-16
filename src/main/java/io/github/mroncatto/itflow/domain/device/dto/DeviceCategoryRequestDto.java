package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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
public class DeviceCategoryRequestDto {

    public interface DeviceCategoryView {
        interface DeviceCategoryPut {}
        interface DeviceCategoryPost {}
    }

    @JsonView(DeviceCategoryView.DeviceCategoryPut.class)
    private Long id;

    @NotEmpty(groups = {DeviceCategoryView.DeviceCategoryPost.class, DeviceCategoryView.DeviceCategoryPut.class},
            message = "[{field.name}] {validation.required}")
    @Size(groups = {DeviceCategoryView.DeviceCategoryPost.class, DeviceCategoryView.DeviceCategoryPut.class},
            max = 45, message = "[{field.name}] {validation.max}")
    private String name;

    @JsonView(DeviceCategoryView.DeviceCategoryPost.class)
    private boolean active;
}
