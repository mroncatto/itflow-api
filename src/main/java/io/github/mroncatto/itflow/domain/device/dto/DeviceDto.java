package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
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
public class DeviceDto {

    public interface DeviceView {
        interface DevicePost {}
        interface DevicePut {}
    }

    @NotNull(groups = DeviceView.DevicePut.class, message = "The id field is required")
    private Long id;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, max = 45, message = "The code field must contain max 45 characters")
    private String code;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, max = 45, message = "The tag field must contain max 45 characters")
    private String tag;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, max = 45, message = "The serial number field must contain max 45 characters")
    private String serialNumber;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @NotEmpty(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, message = "The hostname field is required")
    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, min = 2, max = 25, message = "The hostname field must contain between 5 and 25 characters")
    private String hostname;

    @NotNull(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, message = "The device category field is required")
    private DeviceCategory deviceCategory;

    @NotNull(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class}, message = "The device department field is required")
    private Department department;

    @JsonView(DeviceView.DevicePut.class)
    private boolean active;
}
