package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.*;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.validation.BindingResult;

public interface IDeviceComputerService {
    Device updateComputer(DeviceComputerRequestDto deviceComputerRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device deleteComputerFromDevice(Long id) throws NoResultException;
    Device addDeviceComputerCpu(DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, Long id, BindingResult result) throws BadRequestException;
    void deleteDeviceComputerCpu(Long id, Long cpuId) throws NoResultException, BadRequestException;
    Device addDeviceComputerMemory(DeviceComputerMemoryRequestDto deviceComputerMemoryRequestDto, Long id, BindingResult result) throws BadRequestException;
    void deleteDeviceComputerMemory(Long id, Long memoryId) throws NoResultException, BadRequestException;
    Device addDeviceComputerStorage(DeviceComputerStorageRequestDto deviceComputerStorageRequestDto, Long id, BindingResult result) throws BadRequestException;
    Device addDeviceComputerSoftware(DeviceComputerSoftwareRequestDto computerSoftwareRequestDto, Long id, BindingResult result) throws BadRequestException;
    void deleteDeviceComputerStorage(Long id, Long storageId) throws NoResultException, BadRequestException;
}
