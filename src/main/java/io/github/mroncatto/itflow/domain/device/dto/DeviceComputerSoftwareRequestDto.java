package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerSoftware;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerSoftwarePK;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicenseKey;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerSoftwareRequestDto {

    public interface DeviceComputerSoftwareView {
        interface DeviceComputerSoftwarePost {
        }

        interface DeviceComputerSoftwarePut {
        }
    }

    @NotNull(groups = {DeviceComputerSoftwareView.DeviceComputerSoftwarePut.class})
    private DeviceComputerSoftwarePK id;

    @NotNull(groups = {DeviceComputerSoftwareView.DeviceComputerSoftwarePost.class, DeviceComputerSoftwareView.DeviceComputerSoftwarePut.class},
            message = "[{field.software}] {validation.required}")
    private Software software;

    @NotNull(groups = {DeviceComputerSoftwareView.DeviceComputerSoftwarePut.class},
            message = "[{field.software_license_key}] {validation.required}")
    private SoftwareLicenseKey softwareLicenseKey;


    public DeviceComputerSoftware convert() {
        var computerSoftware = new DeviceComputerSoftware();
        BeanUtils.copyProperties(this, computerSoftware);
        return computerSoftware;
    }
}
