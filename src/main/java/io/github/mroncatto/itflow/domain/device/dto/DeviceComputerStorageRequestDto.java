package io.github.mroncatto.itflow.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerStorage;
import io.github.mroncatto.itflow.domain.device.entity.pk.DeviceComputerStoragePK;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceComputerStorageRequestDto {

    public interface DeviceComputerStorageView {
        interface DeviceComputerStoragePut {}
    }

    @JsonView(DeviceComputerStorageView.DeviceComputerStoragePut.class)
    private DeviceComputerStoragePK id;

    @JsonView(DeviceComputerStorageView.DeviceComputerStoragePut.class)
    private DeviceComputer deviceComputer;

    @JsonView(DeviceComputerStorageView.DeviceComputerStoragePut.class)
    private ComputerStorage computerStorage;


    @Min(value = 1, groups = DeviceComputerStorageView.DeviceComputerStoragePut.class,
            message = "[{field.size}] {validation.min}")
    private int size;

    public DeviceComputerStorage convert() {
        var deviceComputerStorage = new DeviceComputerStorage();
        BeanUtils.copyProperties(this, deviceComputerStorage);
        return deviceComputerStorage;
    }

}
