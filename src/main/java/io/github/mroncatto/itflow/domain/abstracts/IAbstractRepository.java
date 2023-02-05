package io.github.mroncatto.itflow.domain.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IAbstractRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}
