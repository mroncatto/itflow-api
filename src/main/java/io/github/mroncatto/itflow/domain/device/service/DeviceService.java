package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceRequestDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceStaffRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceStaffService;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeviceService extends AbstractService implements IDeviceService, IDeviceStaffService, IDeviceComputerService {
    private final IDeviceRepository repository;
    private final FilterService filterService;
    private final MessageService messageService;


    @Override
    public List<Device> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable, String filter, List<String> departments, List<String> categories) {
        log.debug(">>>FILTERING DEVICE BY: {}", filter);
        log.debug(">>>AND FILTERING DEPARTMENTS: {}", departments);
        log.debug(">>>AND FILTERING CATEGORIES: {}", categories);
        Page<Device> result = this.repository.findAll((Specification<Device>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //TODO: Improve situacion device to many types

            if (nonNull(filter)) {
                Predicate predicateID = filterService.equalsFilter(builder, root, "id", convertToLong(filter));
                Predicate predicateCode = filterService.equalsFilter(builder, root, "code", filter);
                Predicate predicateTag = filterService.likeFilter(builder, root, "tag", filter);
                Predicate predicateHost = filterService.likeFilter(builder, root, "hostname", filter);
                predicates.add(builder.or(predicateID, predicateCode, predicateTag, predicateHost));
            }

            if (nonNull(departments))
                predicates.add(filterService.whereInFilter(root, "department", "id", departments));
            if (nonNull(categories))
                predicates.add(filterService.whereInFilter(root, "deviceCategory", "id", categories));

            return builder.and(predicates.toArray(Predicate[]::new));

        }, pageable);

       /* result.getContent().forEach(device -> {
            if (nonNull(device.getDeviceComputer()))
                Hibernate.initialize(device.getDeviceComputer().getDeviceComputerCpu());
        });*/

        return result;
    }

    @Override
    public Device save(DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);

        if (nonNull(deviceRequestDto.getCode()) && !deviceRequestDto.getCode().isBlank())
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
        deviceStaffRequestDto.setId(id);
        deviceStaffRequestDto.setDevice(device);
        var deviceStaff = new DeviceStaff();
        BeanUtils.copyProperties(deviceStaffRequestDto, deviceStaff);
        device.setDeviceStaff(deviceStaff);
        log.debug(">>>UPDATING STAFF: {}", deviceStaffRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device updateComputer(DeviceComputerRequestDto deviceComputerRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        deviceComputerRequestDto.setId(id);
        deviceComputerRequestDto.setDevice(device);

        var deviceComputer = new DeviceComputer();
        BeanUtils.copyProperties(deviceComputerRequestDto, deviceComputer);
        device.setDeviceComputer(deviceComputer);
        log.debug(">>>UPDATING DEVICE COMPUTER: {}", deviceComputerRequestDto.toString());
        return this.repository.save(device);
    }

    @Override
    public Device update(DeviceRequestDto deviceRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        if (nonNull(deviceRequestDto.getCode()) && !deviceRequestDto.getCode().isBlank())
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
    @Transactional(readOnly = true)
    public Device findById(Long id) throws NoResultException {
        Device device = this.repository.findById(id).orElseThrow(() -> new NoResultException("DEVICE NOT FOUND"));
       /* if (nonNull(device.getDeviceComputer()))
            Hibernate.initialize(device.getDeviceComputer().getCpu());*/
        return device;
    }

    @Override
    public Device deleteById(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setActive(false);
        log.debug(">>>DELETING DEVICE BY: {}", id);
        return this.repository.save(device);
    }

    @Override
    public Device deleteStaffFromDevice(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setDeviceStaff(null);
        return this.repository.save(device);
    }

    @Override
    public Device deleteComputerFromDevice(Long id) throws NoResultException {
        Device device = this.findById(id);
        device.setDeviceComputer(null);
        return this.repository.save(device);
    }

    @Override
    @Transactional
    public Device addDeviceComputerCpu(DeviceComputerCpuRequestDto deviceComputerCpuRequestDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        if(nonNull(device.getDeviceComputer()))
            throw new BadRequestException(messageService.getMessage("badRequest.device_does_not_have_computer"));

        var deviceComputerCpu = new DeviceComputerCpu();
        BeanUtils.copyProperties(deviceComputerCpuRequestDto, deviceComputerCpu);
        deviceComputerCpu.addEmbeddedKey();
        //device.getDeviceComputer().setCpu(deviceComputerCpu);
        return this.repository.save(device);
    }

    private void validateUniqueCode(DeviceRequestDto deviceRequestDto) throws BadRequestException {
        Device anydevice = this.repository.findAllByCode(deviceRequestDto.getCode())
                .stream()
                .filter(Device::isActive)
                .findFirst().orElse(null);

        if (nonNull(anydevice) && distinct(anydevice.getId(), deviceRequestDto.getId()))
            throw new BadRequestException(messageService.getMessage("badRequest.device_already_exists_by_code"));
    }
}
