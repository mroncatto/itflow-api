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
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoftwareLicenseRequestDto {

    public interface SoftwareLicenseView {
        interface SoftwareLicensePost {}
        interface SoftwareLicensePut {}
        interface SoftwareLicenseAddLicense {}
    }

    @JsonView(SoftwareLicenseView.SoftwareLicensePut.class)
    private Long id;

    @NotEmpty(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            message = "[{field.description}] {validation.required}")
    @Size(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class,
            SoftwareLicenseView.SoftwareLicenseAddLicense.class},
            max = 75, message = "[{field.description}] {validation.max}")
    private String description;

    @Size(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            max = 45, message = "[{field.code}] {validation.max}")
    private String code;

    @JsonView({SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class})
    private Date expireAt;

    @NotNull(groups = {SoftwareLicenseView.SoftwareLicensePut.class, SoftwareLicenseView.SoftwareLicensePost.class},
            message = "[{field.software}] {validation.required}")
    private Software software;

    @JsonView(SoftwareLicenseView.SoftwareLicensePost.class)
    private boolean active;
}
