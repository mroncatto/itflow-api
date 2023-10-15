package io.github.mroncatto.itflow.domain.computer.model;

import io.github.mroncatto.itflow.application.model.IAbstractService;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.NoResultException;

import java.util.List;

public interface IComputerCpuService extends IAbstractService<ComputerCpu, ComputerCpuRequestDto> {
    ComputerCpu findById(Long id) throws NoResultException;
    Page<ComputerCpu> findAll(Pageable pageable, String filter);
    ComputerCpu deleteById(Long id) throws NoResultException;

    List<ComputerCpu> findAll(String filter);
}
