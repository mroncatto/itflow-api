package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyRequestDto {

    public interface CompanyView {
        interface CompanyPost {
        }

        interface CompanyPut {
        }
    }

    @NotBlank(groups = CompanyView.CompanyPut.class, message = "[{field.id}] {validation.required}")
    @JsonView(CompanyView.CompanyPut.class)
    private Long id;

    @NotBlank(groups = {CompanyView.CompanyPut.class, CompanyView.CompanyPost.class},
            message = "[{field.name}] {validation.required}")
    @Size(groups = {CompanyView.CompanyPut.class, CompanyView.CompanyPost.class},
            max = 45, message = "[{field.name}] {validation.max}")
    @JsonView({CompanyView.CompanyPut.class, CompanyView.CompanyPost.class})
    private String name;

    @Size(groups = {CompanyView.CompanyPut.class, CompanyView.CompanyPost.class}, max = 45,
            message = "[{field.document}] {validation.max}")
    @JsonView({CompanyView.CompanyPut.class, CompanyView.CompanyPost.class})
    private String document;

    public Company convert() {
        var company = new Company();
        BeanUtils.copyProperties(this, company);
        return company;
    }
}
