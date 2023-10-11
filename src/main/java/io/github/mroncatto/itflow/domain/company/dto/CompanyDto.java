package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    public interface CompanyView {
        interface CompanyPost {
        }

        interface CompanyPut {
        }
    }

    @NotBlank(groups = CompanyView.CompanyPut.class, message = "The id field is required")
    @JsonView(CompanyView.CompanyPut.class)
    private Long id;

    @NotBlank(groups = {CompanyView.CompanyPut.class, CompanyView.CompanyPost.class},
            message = "The name field is required")
    @Size(max = 45, message = "The name field must contain max 45 characters")
    @JsonView({CompanyView.CompanyPut.class, CompanyView.CompanyPost.class})
    private String name;

    @Size(groups = {CompanyView.CompanyPut.class, CompanyView.CompanyPost.class},
            min = 5, max = 45,
            message = "The document field must contain between 5 and 45 characters")
    @JsonView({CompanyView.CompanyPut.class, CompanyView.CompanyPost.class})
    private String document;
}
