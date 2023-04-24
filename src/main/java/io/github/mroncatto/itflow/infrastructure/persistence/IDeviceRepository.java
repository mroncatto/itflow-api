package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.device.entity.Device;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractDeviceRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IDeviceRepository extends IAbstractDeviceRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    List<Device> findAllByTag(String tag);
    List<Device> findAllByCode(String code);
}
