package io.github.mroncatto.itflow.domain.software.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.mroncatto.itflow.domain.software.entity.Software;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoftwareLicenseDto {

    public interface SoftwareLicenseView {
        interface SoftwareLicensePost {}
        interface SoftwareLicensePut {}
        interface SoftwareLicenseAddLicense {}
    }

    @JsonView(SoftwareLicenseView.SoftwareLicensePut.class)
    private Long id;

    @NotEmpty(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            message = "The description field is required")
    @Size(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class,
            SoftwareLicenseView.SoftwareLicenseAddLicense.class},
            max = 75, message = "The description field must contain max 75 characters")
    private String description;

    @Size(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            max = 45, message = "The code field must contain max 45 characters")
    private String code;

    @JsonView({SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class})
    private Date expireAt;

    @NotNull(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            message = "The software field is required")
    private Software software;

    @JsonView(SoftwareLicenseView.SoftwareLicensePost.class)
    private boolean active;
}
