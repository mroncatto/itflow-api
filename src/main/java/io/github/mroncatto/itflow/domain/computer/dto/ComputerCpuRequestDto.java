package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCpu;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComputerCpuRequestDto {

    public interface ComputerCpuView {
        interface ComputerCpuPost {
        }

        interface ComputerCpuPut {
        }
    }

    @JsonView(ComputerCpuView.ComputerCpuPut.class)
    private Long id;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "[{field.brand_name}] {validation.required}")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 45, message = "[{field.brand_name}] {validation.max}")
    private String brandName;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "[{field.model}] {validation.required}")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 45, message = "[{field.model}] {validation.max}")
    private String model;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.generation}] {validation.max}")
    private String generation;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "[{field.socket}] {validation.required}")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.socket}] {validation.max}")
    private String socket;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.core}] {validation.max}")
    private String core;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.thread}] {validation.max}")
    private String thread;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.frequency}] {validation.max}")
    private String frequency;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "[{field.fsb}] {validation.max}")
    private String fsb;

    @JsonView({ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class})
    private boolean active;

    public ComputerCpu convert() {
        var computerCpu = new ComputerCpu();
        BeanUtils.copyProperties(this, computerCpu);
        return computerCpu;
    }
}
