package io.github.mroncatto.itflow.domain.computer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.computer.entity.ComputerStorage;
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
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComputerStorageRequestDto {

    public interface ComputerStorageView {
        interface ComputerStoragePost {}
        interface ComputerStoragePut {}
    }

    @JsonView(ComputerStorageView.ComputerStoragePut.class)
    private Long id;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "[{field.brand_name}] {validation.required}")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 45, message = "[{field.brand_name}] {validation.max}")
    private String brandName;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "[{field.transfer_rate}] {validation.required}")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 25, message = "[{field.transfer_rate}] {validation.max}")
    private String transferRate;

    @NotNull(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            message = "[{field.type}] {validation.required}")
    @Size(groups = {ComputerStorageView.ComputerStoragePut.class, ComputerStorageView.ComputerStoragePost.class},
            max = 25, message = "[{field.type}] {validation.max}")
    private String type;

    @JsonView(ComputerStorageView.ComputerStoragePost.class)
    private boolean active;

    public ComputerStorage convert() {
        var computerStorage = new ComputerStorage();
        BeanUtils.copyProperties(this, computerStorage);
        return computerStorage;
    }
}
