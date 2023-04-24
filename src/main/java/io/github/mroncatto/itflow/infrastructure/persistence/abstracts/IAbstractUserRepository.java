package io.github.mroncatto.itflow.infrastructure.persistence.abstracts;

import io.github.mroncatto.itflow.application.model.IAbstractRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IAbstractUserRepository<T, ID extends Serializable> extends IAbstractRepository<T, ID> {
}
