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
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceRequestDto {

    public interface DeviceView {
        interface DevicePost {
        }

        interface DevicePut {
        }
    }

    @NotNull(groups = DeviceView.DevicePut.class, message = "[{field.id}] {validation.required}")
    private Long id;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            max = 45, message = "[{field.code}] {validation.max}")
    private String code;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            max = 45, message = "[{field.tag}] {validation.max}")
    private String tag;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            max = 45, message = "[{field.serial_number}] {validation.max}")
    private String serialNumber;

    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            max = 75, message = "[{field.description}] {validation.max}")
    private String description;

    @NotEmpty(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            message = "[{field.hostname}] {validation.required}")
    @Size(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            min = 2, max = 25, message = "[{field.hostname}] {validation.between}")
    private String hostname;

    @NotNull(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            message = "[{field.device_category}] {validation.required}")
    private DeviceCategory deviceCategory;

    @NotNull(groups = {DeviceView.DevicePut.class, DeviceView.DevicePost.class},
            message = "[{field.department}] {validation.required}")
    private Department department;

    @JsonView(DeviceView.DevicePut.class)
    private boolean active;
}
