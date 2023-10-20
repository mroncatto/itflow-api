package io.github.mroncatto.itflow.domain.computer.service;

import io.github.mroncatto.itflow.application.model.AbstractService;
import io.github.mroncatto.itflow.application.service.MessageService;
import io.github.mroncatto.itflow.domain.commons.exception.BadRequestException;
import io.github.mroncatto.itflow.domain.computer.dto.ComputerCpuRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import io.github.mroncatto.itflow.domain.computer.model.IComputerCpuService;
import io.github.mroncatto.itflow.infrastructure.persistence.IComputerCpuRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ComputerCpuService extends AbstractService implements IComputerCpuService {
    private final IComputerCpuRepository repository;
    private final MessageService messageService;

    @Override
    public List<ComputerCpu> findAll() {
        return this.repository.findAllByActiveTrue();
    }

    @Override
    public ComputerCpu save(ComputerCpuRequestDto computerCpuRequestDto, BindingResult result) throws BadRequestException {
        validateResult(result);
        log.debug(">>>CREATING COMPUTER CPU: {}", computerCpuRequestDto.toString());
        return this.repository.save(computerCpuRequestDto.convert());
    }

    @Override
    public ComputerCpu update(ComputerCpuRequestDto computerCpuRequestDto, BindingResult result) throws BadRequestException, NoResultException {
        validateResult(result);
        ComputerCpu cpu = this.findById(computerCpuRequestDto.getId());
        cpu.setBrandName(computerCpuRequestDto.getBrandName());
        cpu.setModel(computerCpuRequestDto.getModel());
        cpu.setGeneration(computerCpuRequestDto.getGeneration());
        cpu.setSocket(computerCpuRequestDto.getSocket());
        cpu.setFrequency(computerCpuRequestDto.getFrequency());
        cpu.setFsb(computerCpuRequestDto.getFsb());
        cpu.setCore(computerCpuRequestDto.getCore());
        log.debug(">>>UPDATING COMPUTER CPU: {}", computerCpuRequestDto.toString());
        return this.repository.save(cpu);
    }

    @Override
    public ComputerCpu findById(Long id) throws NoResultException {
        return this.repository.findById(id).orElseThrow(()
                -> new NoResultException(messageService.getMessageNotFound("computer_cpu")));
    }

    @Override
    public Page<ComputerCpu> findAll(Pageable pageable) {
        return this.repository.findAllByActiveTrue(pageable);
    }

    @Override
    public List<ComputerCpu> findAll(Specification<ComputerCpu> spec) {
        return this.repository.findAll(spec);
    }

    @Override
    public ComputerCpu deleteById(Long id) throws NoResultException {
        ComputerCpu cpu = this.findById(id);
        cpu.setActive(false);
        log.debug(">>>DELETING COMPUTER CPU BY: {}", id);
        return this.repository.save(cpu);
    }
}
