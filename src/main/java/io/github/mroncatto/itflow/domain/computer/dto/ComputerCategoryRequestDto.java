package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.dto.CompanyRequestDto;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerCategory;
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
public class ComputerCategoryRequestDto {

    public interface ComputerCategoryView {
        interface ComputerCategoryPost {}
        interface ComputerCategoryPut {}
    }

    @JsonView(ComputerCategoryView.ComputerCategoryPut.class)
    private Long id;

    @Size(groups = {ComputerCategoryView.ComputerCategoryPut.class, ComputerCategoryView.ComputerCategoryPost.class},
            max = 45, message = "[{field.name}] {validation.max}")
    private String name;

    @JsonView(CompanyRequestDto.CompanyView.CompanyPost.class)
    private boolean active;

    public ComputerCategory convert() {
        var computerCategory = new ComputerCategory();
        BeanUtils.copyProperties(this, computerCategory);
        return computerCategory;
    }
}
