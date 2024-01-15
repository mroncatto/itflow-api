package io.github.mroncatto.itflow.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.mroncatto.itflow.domain.company.entity.Branch;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Branch branch;

    private boolean active;

}
