package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.staff.dto.StaffRequestDto;
import io.github.mroncatto.itflow.domain.staff.model.IStaffService;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import io.github.mroncatto.itflow.infrastructure.persistence.IStaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.CompareHelper.distinct;
import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Service
@Log4j2
@RequiredArgsConstructor
public class StaffService extends AbstractService implements IStaffService {
    private final IStaffRepository repository;
    private final FilterService filterService;
    private final MessageService messageService;

    @Override
    public List<Staff> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public Page<Staff> findAll(Pageable pageable, String filter, List<String> departments, List<String> occupations) {
        log.debug(">>>FILTERING STAFF BY: {}", filter);
        log.debug(">>>AND DEPARTMENTS: {}", departments);
        log.debug(">>>AND OCCUPATIONS: {}", occupations);
        return this.repository.findAll((Specification<Staff>) (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(filterService.equalsFilter(builder, root, "active", true));

            if (nonNull(filter)) {
                Predicate predicateFullname = filterService.likeFilter(builder, root, "fullName", filter);
                Predicate predicateEmail = filterService.likeFilter(builder, root, "email", filter);
                predicates.add(builder.or(predicateFullname, predicateEmail));
            }

            if (nonNull(departments)) predicates.add(filterService.whereInFilter(root, "department", "id", departments));
            if (nonNull(occupations)) predicates.add(filterService.whereInFilter(root, "occupation", "id", occupations));

            return builder.and(predicates.toArray(Predicate[]::new));

        }, pageable);
    }

    @Override
    public Staff save(StaffRequestDto staffRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        validateUniqueEmail(staffRequestDto);
        var staff = new Staff();
        BeanUtils.copyProperties(staffRequestDto, staff);
        log.debug(">>>CREATING STAFF: {}", staffRequestDto);
        return this.repository.save(staff);
    }

    @Override
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

        if (nonNull(anystaff) && distinct(anystaff.getId(), staffRequestDto.getId()))
            throw new BadRequestException(messageService.getMessage("badRequest.employee_already_exists_by_email"));
    }
}
