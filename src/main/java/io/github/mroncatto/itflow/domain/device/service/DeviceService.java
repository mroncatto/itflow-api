package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputer;
import io.github.mroncatto.itflow.domain.device.entity.DeviceComputerCpu;
import io.github.mroncatto.itflow.domain.device.entity.DeviceStaff;
import io.github.mroncatto.itflow.domain.device.model.IDeviceComputerService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceService;
import io.github.mroncatto.itflow.domain.device.model.IDeviceStaffService;
import io.github.mroncatto.itflow.infrastructure.persistence.IDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Predicate;
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
    public Device save(Device entity, BindingResult result) throws BadRequestException {
        validateResult(result);

        if (nonNull(entity.getCode()) && !entity.getCode().isBlank())
            validateUniqueCode(entity);

        return this.repository.save(entity);
    }

    @Override
    public Device updateStaff(DeviceStaff entity, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        entity.setId(id);
        entity.setDevice(device);
        device.setDeviceStaff(entity);
        return this.repository.save(device);
    }

    @Override
    public Device updateComputer(DeviceComputer entity, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        entity.setId(id);
        entity.setDevice(device);
        device.setDeviceComputer(entity);
        return this.repository.save(device);
    }

    @Override
    public Device update(Device entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        if (nonNull(entity.getCode()) && !entity.getCode().isBlank())
            validateUniqueCode(entity);
        Device device = this.findById(entity.getId());
        device.setCode(entity.getCode());
        device.setTag(entity.getTag());
        device.setDeviceCategory(entity.getDeviceCategory());
        device.setDepartment(entity.getDepartment());
        device.setSerialNumber(entity.getSerialNumber());
        device.setHostname(entity.getHostname());
        device.setDescription(entity.getDescription());
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
    public Device addDeviceComputerCpu(DeviceComputerCpu entity, Long id, BindingResult result) throws BadRequestException {
        validateResult(result);
        Device device = this.findById(id);
        if(nonNull(device.getDeviceComputer())) throw new BadRequestException("CURRENT DEVICE DOES NOT HAVE A COMPUTER RESOURCE");
        entity.addEmbeddedKey();
        device.getDeviceComputer().setCpu(entity);
        return this.repository.save(device);
    }

    private void validateUniqueCode(Device device) throws BadRequestException {
        Device anydevice = this.repository.findAllByCode(device.getCode())
                .stream()
                .filter(Device::isActive)
                .findFirst().orElse(null);

        if (nonNull(anydevice) && distinct(anydevice.getId(), device.getId()))
            throw new BadRequestException("A device with the given code already exists!");
    }
}
