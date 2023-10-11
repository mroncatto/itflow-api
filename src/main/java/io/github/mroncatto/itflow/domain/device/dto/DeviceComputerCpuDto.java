package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerCpuPK;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerCpuDto {

    public interface DeviceComputerCpuView {
        interface DeviceComputerCpuPut {}
    }

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private DeviceComputerCpuPK id;

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private DeviceComputer deviceComputer;

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private ComputerCpu computerCpu;

    @NotNull(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class, message = "The vcpu field is required")
    @Size(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class, min = 1, max = 11, message = "The vcpu field must contain between 1 and 11 digits")
    private String vcpu;

    @NotNull(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class, message = "The unit field is required")
    @Size(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class, min = 1, max = 11, message = "The unit field must contain between 1 and 11 digits")
    private String unit;
}
