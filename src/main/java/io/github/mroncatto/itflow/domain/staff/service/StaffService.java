package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.staff.dto.StaffRequestDto;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import io.github.mroncatto.itflow.domain.staff.model.IStaffService;
import io.github.mroncatto.itflow.infrastructure.persistence.IStaffRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;

@Service
@Log4j2
@RequiredArgsConstructor
public class StaffService extends AbstractService implements IStaffService {
    private final IStaffRepository repository;
    private final MessageService messageService;

    @Override
    @Cacheable(value = "Staff", key = "#root.method.name")
    public List<Staff> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public Page<Staff> findAll(Specification<Staff> spec, Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }

    @Override
    @CacheEvict(value = "Staff", allEntries = true)
    public Staff save(StaffRequestDto staffRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        validateUniqueEmail(staffRequestDto);
        var staff = new Staff();
        BeanUtils.copyProperties(staffRequestDto, staff);
        log.debug(">>>CREATING STAFF: {}", staffRequestDto);
        return this.repository.save(staff);
    }

    @Override
    @CacheEvict(value = "Staff", allEntries = true)
    public Staff update(StaffRequestDto staffRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        validateUniqueEmail(staffRequestDto);
        Staff updatedStaff = this.findById(staffRequestDto.getId().toString());
        updatedStaff.setEmail(staffRequestDto.getEmail());
        updatedStaff.setFullName(staffRequestDto.getFullName());
        updatedStaff.setDepartment(staffRequestDto.getDepartment());
        updatedStaff.setOccupation(staffRequestDto.getOccupation());
        log.debug(">>>UPDATING STAFF: {}", staffRequestDto);
        return this.repository.save(updatedStaff);
    }

    @Override
    public Staff findById(String uuid) throws NoResultException {
        UUID id = UUID.fromString(uuid);
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("staff")));
    }

    @Override
    @CacheEvict(value = "Staff", allEntries = true)
    public Staff deleteById(String uuid) throws NoResultException {
        Staff staff = this.findById(uuid);
        staff.setActive(false);
        log.debug(">>>DELETING STAFF BY: {}", uuid);
        return this.repository.save(staff);
    }

    private void validateUniqueEmail(StaffRequestDto staffRequestDto) throws BadRequestException {
        Staff anystaff = this.repository.findAllByEmail(staffRequestDto.getEmail())
                .stream()
                .filter(Staff::isActive)
                .findFirst().orElse(null);

        if (Objects.nonNull(anystaff) && distinct(anystaff.getId(), staffRequestDto.getId()))
            throw new BadRequestException(messageService.getMessage("badRequest.employee_already_exists_by_email"));
    }
}
