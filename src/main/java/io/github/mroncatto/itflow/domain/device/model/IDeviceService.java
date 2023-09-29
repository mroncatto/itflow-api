package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;
import java.util.List;

public interface IDeviceService extends IAbstractService<Device> {
    Device findById(Long id) throws NoResultException;
    Page<Device> findAll(Pageable pageable, String filter, List<String> departments, List<String> categories);
    Device deleteById(Long id) throws NoResultException;
}
