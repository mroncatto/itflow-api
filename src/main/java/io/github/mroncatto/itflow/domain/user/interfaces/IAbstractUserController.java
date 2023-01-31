package io.github.mroncatto.itflow.domain.user.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAbstractUserController<U> {
    public abstract ResponseEntity<List<U>> findAll();
}
