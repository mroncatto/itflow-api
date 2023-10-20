package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.device.dto.DeviceRequestDto;
import io.github.mroncatto.itflow.domain.device.entity.Device;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IDeviceService extends IAbstractService<Device, DeviceRequestDto> {
    Device findById(Long id) throws NoResultException;
    Page<Device> findAll(Specification<Device> spec, Pageable pageable);
    Device deleteById(Long id) throws NoResultException;
}
