package io.github.mroncatto.itflow.domain.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.software.entity.SoftwareLicense;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicenseKeyDto {

    public interface LicenseKeyView {
        interface LicenseKeyPost {}
        interface LicenseKeyPut {}
    }

    @JsonView(LicenseKeyView.LicenseKeyPut.class)
    private Long id;

    @Size(groups = {LicenseKeyView.LicenseKeyPut.class, LicenseKeyView.LicenseKeyPost.class},
            max = 45, message = "The code field must contain max 45 characters")
    private String key;

    @JsonView({LicenseKeyView.LicenseKeyPut.class, LicenseKeyView.LicenseKeyPost.class})
    private SoftwareLicense softwareLicense;

    @NotNull(groups = {LicenseKeyView.LicenseKeyPut.class, LicenseKeyView.LicenseKeyPost.class},
            message = "The volume field is required")
    private int volume;
}
