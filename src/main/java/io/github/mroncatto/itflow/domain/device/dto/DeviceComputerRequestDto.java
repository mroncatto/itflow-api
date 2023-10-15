package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerRequestDto {

    public interface DeviceComputerView {
        interface DeviceComputerPut {}
    }

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private Long id;

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private Device device;

    @NotNull(groups = {DeviceComputerView.DeviceComputerPut.class},
            message = "[{field.computer_category}] {validation.required}")
    private ComputerCategory computerCategory;

    @Size(groups = {DeviceComputerView.DeviceComputerPut.class},
            max = 75, message = "[{field.description}] {validation.max}")
    private String description;

    @JsonView(DeviceComputerView.DeviceComputerPut.class)
    private boolean virtual;
}
