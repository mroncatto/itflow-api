package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.device.model.DeviceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IDeviceCategoryService extends IAbstractService<DeviceCategory> {
    DeviceCategory findById(Long id) throws NoResultException;
    Page<DeviceCategory> findAll(Pageable pageable, String filter);
    DeviceCategory deleteById(Long id) throws NoResultException;
}
