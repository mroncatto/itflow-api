package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerDto {

    public interface DeviceComputerView {
        interface DeviceComputerPut {}
    }

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private Long id;

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private Device device;

    @NotNull(groups = {DeviceComputerView.DeviceComputerPut.class}, message = "The computer category field is required")
    private ComputerCategory computerCategory;

    @Size(groups = {DeviceComputerView.DeviceComputerPut.class}, max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private boolean virtual;
}
