package io.github.mroncatto.itflow.domain.device.repository;

import io.github.mroncatto.itflow.domain.device.model.DeviceCategory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDeviceCategoryRepository extends IAbstractDeviceRepository<DeviceCategory, Long> {

    @Query(value = "SELECT DISTINCT c FROM DeviceCategory c JOIN Device d ON c.id = d.deviceCategory.id")
    List<DeviceCategory> findByDeviceIsNotNull();
}
