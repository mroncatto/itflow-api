package io.github.mroncatto.itflow.domain.device.interfaces;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractController;
import io.github.mroncatto.itflow.domain.device.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;
import java.util.List;

public interface IDeviceController extends IAbstractController<Device> {
    ResponseEntity<Device> findById(Long id) throws NoResultException;
    ResponseEntity<Page<Device>> findAll(int page, String filter, List<String> departments, List<String> categories);
    ResponseEntity<Device> deleteById(Long id) throws NoResultException;
}
