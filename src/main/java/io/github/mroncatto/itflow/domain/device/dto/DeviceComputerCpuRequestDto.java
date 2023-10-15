package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerCpuPK;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerCpuRequestDto {

    public interface DeviceComputerCpuView {
        interface DeviceComputerCpuPut {}
    }

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private DeviceComputerCpuPK id;

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private DeviceComputer deviceComputer;

    @JsonView(DeviceComputerCpuView.DeviceComputerCpuPut.class)
    private ComputerCpu computerCpu;

    @NotEmpty(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class,
            message = "[{field.vcpu}] {validation.required}")
    @Size(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class,
            max = 11, message = "[{field.vcpu}] {validation.max}")
    private String vcpu;

    @NotEmpty(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class,
            message = "[{field.unit}] {validation.required}")
    @Size(groups = DeviceComputerCpuView.DeviceComputerCpuPut.class,
            max = 11, message = "[{field.unit}] {validation.max}")
    private String unit;
}
