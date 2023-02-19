package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractService;
import io.github.mroncatto.itflow.domain.device.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.NoResultException;

public interface IDeviceService extends IAbstractService<Device> {
    Device findById(Long id) throws NoResultException;
    Page<Device> findAll(Pageable pageable, String filter);
    Device deleteById(Long id) throws NoResultException;
}
