package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeviceCategoryFilterController {
    ResponseEntity<List<DeviceCategory>> findAllUsingByDevice();
}
