package io.github.mroncatto.itflow.domain.device.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.device.interfaces.IDeviceCategoryService;
import io.github.mroncatto.itflow.domain.device.model.DeviceCategory;
import io.github.mroncatto.itflow.domain.device.repository.IDeviceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceCategoryService extends AbstractService implements IDeviceCategoryService {

    private final IDeviceCategoryRepository repository;

    @Override
    public List<DeviceCategory> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    public List<DeviceCategory> findByDeviceIsNotNull() {
        return this.repository.findByDeviceIsNotNull();
    }

    @Override
    public DeviceCategory save(DeviceCategory entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public DeviceCategory update(DeviceCategory entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        DeviceCategory category = this.findById(entity.getId());
        category.setName(entity.getName());
        return this.repository.save(category);
    }

    @Override
    public DeviceCategory findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("DEVICE CATEGORY NOT FOUND"));
    }

    @Override
    public Page<DeviceCategory> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public DeviceCategory deleteById(Long id) throws NoResultException {
        DeviceCategory category = this.findById(id);
        category.setActive(false);
        return this.repository.save(category);
    }
}
