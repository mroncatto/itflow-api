package io.github.mroncatto.itflow.domain.device.repository;

import io.github.mroncatto.itflow.domain.device.model.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IDeviceRepository extends IAbstractDeviceRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    List<Device> findAllByTag(String tag);
    List<Device> findAllByCode(String code);
}
