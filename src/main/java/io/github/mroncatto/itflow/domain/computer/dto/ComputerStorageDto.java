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
public class ComputerStorageDto {

    public interface ComputerStorageView {
        interface ComputerStoragePost {}
        interface ComputerStoragePut {}
    }

    @JsonView(ComputerStorageView.ComputerStoragePut.class)
    private Long id;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "The brand name field is required")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 45, message = "The brand name field must contain max 45 characters")
    private String brandName;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "The transfer rate field is required")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 25, message = "The transfer rate field must contain max 25 characters")
    private String transferRate;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "The type field is required")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 25, message = "The type field must contain max 25 characters")
    private String type;

    @JsonView(ComputerStorageView.ComputerStoragePost.class)
    private boolean active;
}
