package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.device.dto.*;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceStaffService;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.isNull;

@Service
@Log4j2
@AllArgsConstructor
public class DeviceService extends AbstractService implements IDeviceService, IDeviceStaffService, IDeviceComputerService {
    public static final String BAD_REQUEST_DEVICE_DOES_NOT_HAVE_COMPUTER = "badRequest.device_does_not_have_computer";
    public static final String BAD_REQUEST_DEVICE_ALREADY_EXISTS_BY_CODE = "badRequest.device_already_exists_by_code";
    private final IDeviceRepository repository;
    private final MessageService messageService;

    @Override
    public List<Device> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Page<Device> findAll(Specification<Device> spec, Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }

    @Override
    public Device save(DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);

        if (Objects.nonNull(deviceRequestDto.getCode()) && !deviceRequestDto.getCode().isBlank())
            validateUniqueCode(deviceRequestDto);

        var device = new Device();
        BeanUtils.copyProperties(deviceRequestDto, device);
        log.debug(">>>CREATING DEVICE: {}", deviceRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device updateStaff(DeviceStaffRequestDto deviceStaffRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        deviceStaffRequestDto.setDevice(device);
        deviceStaffRequestDto.setId(id);
        device.setDeviceStaff(deviceStaffRequestDto.convert());
        log.debug(">>>UPDATING STAFF: {}", deviceStaffRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device updateComputer(DeviceComputerRequestDto deviceComputerRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        deviceComputerRequestDto.setDevice(device);
        deviceComputerRequestDto.setId(id);
        if (Objects.nonNull(device.getDeviceComputer()))
            device.updateDeviceComputer(deviceComputerRequestDto.convert());
        else
            device.setDeviceComputer(deviceComputerRequestDto.convert());
        log.debug(">>>UPDATING DEVICE COMPUTER: {}", deviceComputerRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device update(DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        if (Objects.nonNull(deviceRequestDto.getCode()) && !deviceRequestDto.getCode().isBlank())
            validateUniqueCode(deviceRequestDto);
        Device device = this.findById(deviceRequestDto.getId());
        device.setCode(deviceRequestDto.getCode());
        device.setTag(deviceRequestDto.getTag());
        device.setDeviceCategory(deviceRequestDto.getDeviceCategory());
        device.setDepartment(deviceRequestDto.getDepartment());
        device.setSerialNumber(deviceRequestDto.getSerialNumber());
        device.setHostname(deviceRequestDto.getHostname());
        device.setDescription(deviceRequestDto.getDescription());
        log.debug(">>>UPDATING DEVICE: {}", deviceRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("DEVICE NOT FOUND"));
    }

    @Override
    @Transactional
    public Device deleteById(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setActive(false);
        log.debug(">>>DELETING DEVICE BY: {}", id);
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public Device deleteStaffFromDevice(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setDeviceStaff(null);
        log.debug(">>>REMOVE STAFF FROM DEVICE BY ID: {}", id);
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public Device deleteComputerFromDevice(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setDeviceComputer(null);
        log.debug(">>>REMOVE COMPUTER FROM DEVICE BY ID: {}", id);
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public Device addDeviceComputerCpu(DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);

        var deviceComputerCpu = deviceComputerCpuRequestDto.convert();
        deviceComputerCpu.setDeviceComputer(device.getDeviceComputer());
        deviceComputerCpu.addEmbeddedKey();
        device.getDeviceComputer().getComputerCpuList().add(deviceComputerCpu);
        log.debug(">>>ADD COMPUTER CPU INTO DEVICE: {}", deviceComputerCpuRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public void deleteDeviceComputerCpu(Long id, Long cpuId) throws NoResultException, BadRequestException {
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);
        device.getDeviceComputer()
                .getComputerCpuList()
                .removeIf(cpuList -> cpuList.getComputerCpu().getId().equals(cpuId));
        log.debug(">>>REMOVE COMPUTER CPU FROM DEVICE, cpuId: {}", cpuId);
        this.repository.save(device);
    }

    @Override
    @Transactional
    public Device addDeviceComputerMemory(DeviceComputerMemoryRequestDto deviceComputerMemoryRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);

        var deviceComputerMemory = deviceComputerMemoryRequestDto.convert();
        deviceComputerMemory.setDeviceComputer(device.getDeviceComputer());
        deviceComputerMemory.addEmbeddedKey();
        device.getDeviceComputer().getComputerMemoryList().add(deviceComputerMemory);
        log.debug(">>>ADD COMPUTER MEMORY INTO DEVICE: {}", deviceComputerMemoryRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public void deleteDeviceComputerMemory(Long id, Long memoryId) throws NoResultException, BadRequestException {
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);

        device.getDeviceComputer()
                .getComputerMemoryList()
                .removeIf(memoryList -> memoryList.getComputerMemory().getId().equals(memoryId));
        log.debug(">>>REMOVE COMPUTER MEMORY FROM DEVICE, memoryId: {}", memoryId);
        this.repository.save(device);
    }

    @Override
    @Transactional
    public Device addDeviceComputerStorage(DeviceComputerStorageRequestDto deviceComputerStorageRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);

        var deviceComputerStorage = deviceComputerStorageRequestDto.convert();
        deviceComputerStorage.setDeviceComputer(device.getDeviceComputer());
        deviceComputerStorage.addEmbeddedKey();
        device.getDeviceComputer().getComputerStorageList().add(deviceComputerStorage);
        log.debug(">>>ADD COMPUTER STORAGE INTO DEVICE: {}", deviceComputerStorageRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public void deleteDeviceComputerStorage(Long id, Long storageId) throws NoResultException, BadRequestException {
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);

        device.getDeviceComputer()
                .getComputerStorageList()
                .removeIf(memoryList -> memoryList.getComputerStorage().getId().equals(storageId));
        log.debug(">>>REMOVE COMPUTER STORAGE FROM DEVICE, storageId: {}", storageId);
        this.repository.save(device);
    }

    @Override
    public Device addDeviceComputerSoftware(DeviceComputerSoftwareRequestDto computerSoftwareRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        validateExistsDeviceComputer(device);
        var deviceComputerSoftware = computerSoftwareRequestDto.convert();
        deviceComputerSoftware.setDeviceComputer(device.getDeviceComputer());
        deviceComputerSoftware.addEmbeddedKey();
        device.getDeviceComputer().getComputerSoftwareList().add(deviceComputerSoftware);
        return this.repository.save(device);
    }

    private void validateUniqueCode(DeviceRequestDto deviceRequestDto) throws BadRequestException {
        Device anydevice = this.repository.findAllByCode(deviceRequestDto.getCode())
                .stream()
                .filter(Objects::nonNull)
                .filter(Device::isActive)
                .findFirst().orElse(null);

        if (Objects.nonNull(anydevice) && distinct(anydevice.getId(), deviceRequestDto.getId()))
            throw new BadRequestException(messageService.getMessage(BAD_REQUEST_DEVICE_ALREADY_EXISTS_BY_CODE));
    }

    private void validateExistsDeviceComputer(Device device) throws BadRequestException {
        if (isNull(device.getDeviceComputer()))
            throw new BadRequestException(messageService.getMessage(BAD_REQUEST_DEVICE_DOES_NOT_HAVE_COMPUTER));
    }
}
