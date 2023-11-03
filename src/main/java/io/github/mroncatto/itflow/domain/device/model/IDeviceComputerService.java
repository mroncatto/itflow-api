package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerMemoryRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerStorageRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputerRequestDto deviceComputerRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
    Device addDeviceComputerCpu(DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteDeviceComputerCpu(Long id) throws NoResultException;
    Device addDeviceComputerMemory(DeviceComputerMemoryRequestDto deviceComputerMemoryRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteDeviceComputerMemory(Long id) throws NoResultException;
    Device addDeviceComputerStorage(DeviceComputerStorageRequestDto deviceComputerStorageRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteDeviceComputerStorage(Long id) throws NoResultException;
}
