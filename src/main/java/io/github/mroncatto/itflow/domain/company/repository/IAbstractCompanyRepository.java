package io.github.mroncatto.itflow.domain.company.repository;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface IAbstractCompanyRepository<T, ID extends Serializable> extends IAbstractRepository<T, ID> {
    List<T> findAllByActiveTrue();
    Page<T> findAllByActiveTrue(Pageable pageable);
}
