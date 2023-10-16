package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import io.github.mroncatto.itflow.domain.company.entity.Department;
import jakarta.validation.constraints.NotBlank;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class DepartmentRequestDto {

    public interface DepartmentView {
        interface DepartmentPost {}
        interface DepartmentPut {}
    }

    @NotNull(groups = DepartmentRequestDto.DepartmentView.DepartmentPut.class, message = "[{field.id}] {validation.required}")
    private Long id;

    @NotBlank(groups = {DepartmentRequestDto.DepartmentView.DepartmentPut.class, DepartmentRequestDto.DepartmentView.DepartmentPost.class},
            message = "{field.name} {validation.required}")
    @Size(groups = {DepartmentRequestDto.DepartmentView.DepartmentPut.class, DepartmentRequestDto.DepartmentView.DepartmentPost.class},
            max = 45, message = "[{field.name}] {validation.max}")
    private String name;

    @NotNull(groups = {DepartmentRequestDto.DepartmentView.DepartmentPut.class, DepartmentRequestDto.DepartmentView.DepartmentPost.class},
            message = "{field.branch} {validation.required}")
    private Branch branch;

    @JsonView({DepartmentRequestDto.DepartmentView.DepartmentPut.class, DepartmentRequestDto.DepartmentView.DepartmentPost.class})
    private boolean active;

    public Department convert() {
        var department = new Department();
        BeanUtils.copyProperties(this, department);
        return department;
    }
}
