package io.github.mroncatto.itflow.infrastructure.persistence;

import io.github.mroncatto.itflow.domain.staff.entity.Occupation;
import io.github.mroncatto.itflow.infrastructure.persistence.abstracts.IAbstractStaffRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOccupationRepository extends IAbstractStaffRepository<Occupation, Long> {

    @Query(value = "SELECT DISTINCT o FROM Occupation o JOIN Staff s ON o.id = s.occupation.id WHERE s.active = true")
    List<Occupation> findByStaffIsNotNull();
}
