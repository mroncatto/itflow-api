package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {

    public interface DepartmentView {
        interface DepartmentPost {}
        interface DepartmentPut {}
    }

    @NotNull(groups = DepartmentDto.DepartmentView.DepartmentPut.class, message = "The id field is required")
    private Long id;

    @NotBlank(groups = {DepartmentDto.DepartmentView.DepartmentPut.class, DepartmentDto.DepartmentView.DepartmentPost.class},
            message = "The name field is required")
    @Size(max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @JsonView({DepartmentDto.DepartmentView.DepartmentPut.class, DepartmentDto.DepartmentView.DepartmentPost.class})
    private Branch branch;

    @JsonView({DepartmentDto.DepartmentView.DepartmentPut.class, DepartmentDto.DepartmentView.DepartmentPost.class})
    private boolean active;
}
