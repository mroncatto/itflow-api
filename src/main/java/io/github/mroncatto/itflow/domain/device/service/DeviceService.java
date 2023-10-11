package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerCpuDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceComputerDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceDto;
import io.github.mroncatto.itflow.domain.device.dto.DeviceStaffDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceStaffService;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Service
@RequiredArgsConstructor
public class DeviceService extends AbstractService implements IDeviceService, IDeviceStaffService, IDeviceComputerService {
    private final IDeviceRepository repository;
    private final FilterService filterService;


    @Override
    public List<Device> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable, String filter, List<String> departments, List<String> categories) {
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
    public Device save(DeviceDto deviceDto, BindingResult result) throws BadRequestException {
        validateResult(result);

        if (nonNull(deviceDto.getCode()) && !deviceDto.getCode().isBlank())
            validateUniqueCode(deviceDto);

        var device = new Device();
        BeanUtils.copyProperties(deviceDto, device);
        return this.repository.save(device);
    }

    @Override
    public Device updateStaff(DeviceStaffDto deviceStaffDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        deviceStaffDto.setId(id);
        deviceStaffDto.setDevice(device);
        var deviceStaff = new DeviceStaff();
        BeanUtils.copyProperties(deviceStaffDto, deviceStaff);
        device.setDeviceStaff(deviceStaff);
        return this.repository.save(device);
    }

    @Override
    public Device updateComputer(DeviceComputerDto deviceComputerDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        deviceComputerDto.setId(id);
        deviceComputerDto.setDevice(device);

        var deviceComputer = new DeviceComputer();
        BeanUtils.copyProperties(deviceComputerDto, deviceComputer);
        device.setDeviceComputer(deviceComputer);
        return this.repository.save(device);
    }

    @Override
    public Device update(DeviceDto deviceDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        if (nonNull(deviceDto.getCode()) && !deviceDto.getCode().isBlank())
            validateUniqueCode(deviceDto);
        Device device = this.findById(deviceDto.getId());
        device.setCode(deviceDto.getCode());
        device.setTag(deviceDto.getTag());
        device.setDeviceCategory(deviceDto.getDeviceCategory());
        device.setDepartment(deviceDto.getDepartment());
        device.setSerialNumber(deviceDto.getSerialNumber());
        device.setHostname(deviceDto.getHostname());
        device.setDescription(deviceDto.getDescription());
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
    public Device addDeviceComputerCpu(DeviceComputerCpuDto deviceComputerCpuDto, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        if(nonNull(device.getDeviceComputer())) throw new BadRequestException("CURRENT DEVICE DOES NOT HAVE A COMPUTER RESOURCE");

        var deviceComputerCpu = new DeviceComputerCpu();
        BeanUtils.copyProperties(deviceComputerCpuDto, deviceComputerCpu);
        deviceComputerCpu.addEmbeddedKey();
        //device.getDeviceComputer().setCpu(deviceComputerCpu);
        return this.repository.save(device);
    }

    private void validateUniqueCode(DeviceDto deviceDto) throws BadRequestException {
        Device anydevice = this.repository.findAllByCode(deviceDto.getCode())
                .stream()
                .filter(Device::isActive)
                .findFirst().orElse(null);

        if (nonNull(anydevice) && distinct(anydevice.getId(), deviceDto.getId()))
            throw new BadRequestException("A device with the given code already exists!");
    }
}
