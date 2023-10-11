package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import io.github.mroncatto.itflow.domain.company.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchDto {

    public interface BranchView {
        interface BranchPost {}
        interface BranchPut {}
    }

    @NotNull(groups = BranchView.BranchPut.class, message = "The id field is required")
    private Long id;

    @NotBlank(groups = {BranchView.BranchPut.class, BranchView.BranchPost.class},
            message = "The name field is required")
    @Size(groups = {BranchView.BranchPut.class, BranchView.BranchPost.class},
            max = 45, message = "The name field must contain max 45 characters")
    private String name;

    @JsonView({BranchView.BranchPut.class, BranchView.BranchPost.class})
    private Company company;

    @JsonView({BranchView.BranchPut.class, BranchView.BranchPost.class})
    private boolean active;

    public Branch convert() {
        var branch = new Branch();
        BeanUtils.copyProperties(this, branch);
        return branch;
    }

}
