package io.github.mroncatto.itflow.domain.device.model;

import io.github.mroncatto.itflow.application.model.IAbstractController;
import io.github.mroncatto.itflow.domain.device.entity.DeviceCategory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.NoResultException;

public interface IDeviceCategoryController extends IAbstractController<DeviceCategory> {
    ResponseEntity<DeviceCategory> findById(Long id) throws NoResultException;
    ResponseEntity<Page<DeviceCategory>> findAll(int page, String filter);
    ResponseEntity<DeviceCategory> deleteById(Long id) throws NoResultException;
}
