package io.github.mroncatto.itflow.domain.staff.service;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import io.github.mroncatto.itflow.domain.abstracts.AbstractService;
import io.github.mroncatto.itflow.domain.staff.interfaces.IStaffService;
import io.github.mroncatto.itflow.domain.staff.model.Staff;
import io.github.mroncatto.itflow.domain.staff.repository.IStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.UUID;

import static io.github.mroncatto.itflow.domain.commons.helper.ValidationHelper.nonNull;

@Service
@RequiredArgsConstructor
public class StaffService extends AbstractService implements IStaffService {
    private final IStaffRepository entityRepository;

    @Override
    public List<Staff> findAll() {
        return this.entityRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Staff> findAll(Pageable pageable, String filter, List<String> departments, List<String> occupations) {
        return this.entityRepository.findAll((Specification<Staff>) (root, query, builder) -> {
            Predicate predicateActiveStaff = filterEquals(builder, root, "active", true);
            Predicate filterPredicate = null;
            Predicate departmentPredicate = null;
            Predicate occupationPredicate = null;
            if (nonNull(filter)) {
                Predicate predicateFullname = filterLike(builder, root, "fullName", filter);
                Predicate predicateEmail = filterLike(builder, root, "email", filter);
                filterPredicate = builder.and(predicateFullname, predicateEmail);
            }
            if (nonNull(departments)) departmentPredicate = filterInWhereID(root, "department", departments);
            if (nonNull(occupations)) occupationPredicate = filterInWhereID(root, "occupation", occupations);

            return builder.and(removeNullPredicates(predicateActiveStaff, filterPredicate, departmentPredicate, occupationPredicate));

        }, pageable);
    }

    @Override
    public Staff save(Staff entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.entityRepository.save(entity);
    }

    @Override
    public Staff update(Staff entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        Staff updatedStaff = this.findById(entity.getId().toString());
        updatedStaff.setEmail(entity.getEmail()); //TODO: validar unique email
        updatedStaff.setFullName(entity.getFullName());
        updatedStaff.setDepartment(entity.getDepartment());
        updatedStaff.setOccupation(entity.getOccupation());
        return this.entityRepository.save(updatedStaff);
    }

    @Override
    public Staff findById(String uuid) throws NoResultException {
        UUID id = UUID.fromString(uuid);
        return this.entityRepository.findById(id).orElseThrow(() -> new NoResultException("ENTITY NOT FOUND"));
    }

    @Override
    public Staff deleteById(String uuid) throws NoResultException {
        Staff staff = this.findById(uuid);
        staff.setActive(false);
        return this.entityRepository.save(staff);
    }
}
