package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.dto.CompanyDto;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComputerCategoryDto {

    public interface ComputerCategoryView {
        interface ComputerCategoryPost {}
        interface ComputerCategoryPut {}
    }

    @JsonView(ComputerCategoryView.ComputerCategoryPut.class)
    private Long id;

    @Size(groups = {ComputerCategoryView.ComputerCategoryPut.class, ComputerCategoryView.ComputerCategoryPost.class}, max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @JsonView(CompanyDto.CompanyView.CompanyPost.class)
    private boolean active;
}
