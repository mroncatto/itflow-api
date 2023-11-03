package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerMemory;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerMemoryPK;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerMemoryRequestDto {

    public interface DeviceComputerMemoryView {
        interface DeviceComputerMemoryPut {}
    }

    @JsonView(DeviceComputerMemoryView.DeviceComputerMemoryPut.class)
    private DeviceComputerMemoryPK id;

    @JsonView(DeviceComputerMemoryView.DeviceComputerMemoryPut.class)
    private DeviceComputer deviceComputer;

    @JsonView(DeviceComputerMemoryView.DeviceComputerMemoryPut.class)
    private ComputerMemory computerMemory;
    
    @Min(value = 1, groups = DeviceComputerMemoryView.DeviceComputerMemoryPut.class,
            message = "[{field.modules}] {validation.min}")
    private int modules;


    public DeviceComputerMemory convert() {
        var DeviceComputerMemory = new DeviceComputerMemory();
        BeanUtils.copyProperties(this, DeviceComputerMemory);
        return DeviceComputerMemory;
    }

}
