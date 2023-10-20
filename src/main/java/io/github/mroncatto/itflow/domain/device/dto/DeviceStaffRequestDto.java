package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString(exclude = "device")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceStaffRequestDto {

    public interface DeviceStaffView {
        interface DeviceStaffPut {}
    }

    @JsonView(DeviceStaffView.DeviceStaffPut.class)
    private Long id;

    @JsonView(DeviceStaffView.DeviceStaffPut.class)
    private Device device;

    @NotNull(groups = DeviceStaffView.DeviceStaffPut.class, message = "[{field.staff}] {validation.required}")
    private Staff staff;

    @Size(groups = DeviceStaffView.DeviceStaffPut.class,
            max = 45, message = "[{field.login}] {validation.max}")
    private String login;

    public DeviceStaff convert() {
        var deviceStaff = new DeviceStaff();
        BeanUtils.copyProperties(this, deviceStaff);
        return deviceStaff;
    }
}
