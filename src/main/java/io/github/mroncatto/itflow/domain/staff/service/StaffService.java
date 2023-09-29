package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.commons.service.filter.FilterService;
import io.github.mroncatto.itflow.domain.staff.model.IStaffService;
import io.github.mroncatto.itflow.domain.staff.entity.Staff;
import io.github.mroncatto.itflow.infrastructure.persistence.IStaffRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StaffService extends AbstractService implements IStaffService {
    private final IStaffRepository repository;
    private final FilterService filterService;

    @Override
    public List<Staff> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public Page<Staff> findAll(Pageable pageable, String filter, List<String> departments, List<String> occupations) {
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
    public Staff save(Staff entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        validateUniqueEmail(entity);
        return this.repository.save(entity);
    }

    @Override
    public Staff update(Staff entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        validateUniqueEmail(entity);
        Staff updatedStaff = this.findById(entity.getId().toString());
        updatedStaff.setEmail(entity.getEmail());
        updatedStaff.setFullName(entity.getFullName());
        updatedStaff.setDepartment(entity.getDepartment());
        updatedStaff.setOccupation(entity.getOccupation());
        return this.repository.save(updatedStaff);
    }

    @Override
    public Staff findById(String uuid) throws NoResultException {
        UUID id = UUID.fromString(uuid);
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("ENTITY NOT FOUND"));
    }

    @Override
    public Staff deleteById(String uuid) throws NoResultException {
        Staff staff = this.findById(uuid);
        staff.setActive(false);
        return this.repository.save(staff);
    }

    private void validateUniqueEmail(Staff staff) throws BadRequestException {
        Staff anystaff = this.repository.findAllByEmail(staff.getEmail())
                .stream()
                .filter(Staff::isActive)
                .findFirst().orElse(null);

        if (nonNull(anystaff) && distinct(anystaff.getId(), staff.getId()))
            throw new BadRequestException("An employee with the provided email already exists!");
    }
}
