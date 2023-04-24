package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.domain.computer.model.IComputerCpuService;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerCpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.persistence.NoResultException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerCpuService extends AbstractService implements IComputerCpuService {

    private final IComputerCpuRepository repository;

    @Override
    public List<ComputerCpu> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerCpu save(ComputerCpu entity, BindingResult result) throws BadRequestException {
        validateResult(result);
        return this.repository.save(entity);
    }

    @Override
    public ComputerCpu update(ComputerCpu entity, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerCpu cpu = this.findById(entity.getId());
        cpu.setBrandName(entity.getBrandName());
        cpu.setModel(entity.getModel());
        cpu.setGeneration(entity.getGeneration());
        cpu.setSocket(entity.getSocket());
        cpu.setFrequency(entity.getFrequency());
        cpu.setFsb(entity.getFsb());
        cpu.setCore(entity.getCore());
        return this.repository.save(cpu);
    }

    @Override
    public ComputerCpu findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(() -> new NoResultException("COMPUTER CPU NOT FOUND"));
    }

    @Override
    public Page<ComputerCpu> findAll(Pageable pageable, String filter) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public ComputerCpu deleteById(Long id) throws NoResultException {
        ComputerCpu cpu = this.findById(id);
        cpu.setActive(false);
        return this.repository.save(cpu);
    }
}
