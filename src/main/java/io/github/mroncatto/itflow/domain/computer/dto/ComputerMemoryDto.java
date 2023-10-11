package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComputerMemoryDto {

    public interface ComputerMemoryView {
        interface ComputerMemoryPost {
        }

        interface ComputerMemoryPut {
        }
    }

    @JsonView(ComputerMemoryView.ComputerMemoryPut.class)
    private Long id;

    @NotNull(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            message = "The brand name field is required")
    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 45, message = "The brand name field must contain max 45 characters")
    private String brandName;

    @NotNull(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            message = "The type field is required")
    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "The type field must contain max 25 characters")
    private String type;

    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "The size field must contain max 25 characters")
    private String size;

    @Size(groups = {ComputerMemoryView.ComputerMemoryPut.class, ComputerMemoryView.ComputerMemoryPost.class},
            max = 25, message = "The frequency field must contain max 25 characters")
    private String frequency;

    @JsonView(ComputerMemoryView.ComputerMemoryPost.class)
    private boolean active;
}
