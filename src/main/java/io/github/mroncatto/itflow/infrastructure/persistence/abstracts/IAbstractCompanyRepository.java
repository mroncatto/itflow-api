package io.github.mroncatto.itflow.infrastructure.persistence.abstracts;

import io.github.mroncatto.itflow.application.model.IAbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface IAbstractCompanyRepository<T, U extends Serializable> extends IAbstractRepository<T, U> {
    List<T> findAllByActiveTrue();
    List<T> findAllByActiveTrue(Sort sort);
    Page<T> findAllByActiveTrue(Pageable pageable);
}
