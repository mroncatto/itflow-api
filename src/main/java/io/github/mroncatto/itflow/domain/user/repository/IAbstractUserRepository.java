package io.github.mroncatto.itflow.domain.user.repository;

import io.github.mroncatto.itflow.domain.abstracts.IAbstractRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IAbstractUserRepository<T, ID extends Serializable> extends IAbstractRepository<T, ID> {
}
