package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerMemory;
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
public class ComputerMemoryRequestDto {

    public interface ComputerMemoryView {
        interface ComputerMemoryPost {
        }

        interface ComputerMemoryPut {
        }
    }

    @JsonView(ComputerMemoryView.ComputerMemoryPut.class)
    private Long id;

    @NotNull(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            message = "[{field.brand_name}] {validation.required}")
    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 45, message = "[{field.brand_name}] {validation.max}")
    private String brandName;

    @NotNull(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            message = "[{field.type}] {validation.required}")
    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "[{field.type}] {validation.max}")
    private String type;

    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "[{field.size}] {validation.max}")
    private String size;

    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "[{field.frequency}] {validation.max}")
    private String frequency;

    @JsonView(ComputerMemoryView.ComputerMemoryPost.class)
    private boolean active;

    public ComputerMemory convert() {
        var computerMemory = new ComputerMemory();
        BeanUtils.copyProperties(this, computerMemory);
        return computerMemory;
    }
}
