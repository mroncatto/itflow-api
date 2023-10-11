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
public class ComputerCpuDto {

    public interface ComputerCpuView {
        interface ComputerCpuPost {
        }

        interface ComputerCpuPut {
        }
    }

    @JsonView(ComputerCpuView.ComputerCpuPut.class)
    private Long id;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "The brand name field is required")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 45, message = "The brand name field must contain max 45 characters")
    private String brandName;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "The model field is required")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 45, message = "The model field must contain max 45 characters")
    private String model;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "The generation field must contain max 25 characters")
    private String generation;

    @NotNull(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            message = "The socket field is required")
    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "The socket field must contain max 25 characters")
    private String socket;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "The core field must contain max 25 characters")
    private String core;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "The frequency field must contain max 25 characters")
    private String frequency;

    @Size(groups = {ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class},
            max = 25, message = "The fsb field must contain max 25 characters")
    private String fsb;

    @JsonView({ComputerCpuView.ComputerCpuPut.class, ComputerCpuView.ComputerCpuPost.class})
    private boolean active;
}
