package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.device.model.DeviceCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeviceCategoryFilterController {
    ResponseEntity<List<DeviceCategory>> findAllUsingByDevice();
}
