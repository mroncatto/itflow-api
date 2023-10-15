package io.github.mroncatto.itflow.domain.staff.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OccupationRequestDto {

    public interface OccupationView {
        interface OccupationPost {}
        interface OccupationPut {}
    }

    @JsonView(OccupationView.OccupationPut.class)
    private Long id;

    @NotEmpty(groups = {OccupationView.OccupationPost.class, OccupationView.OccupationPut.class},
            message = "[{field.name}] {validation.required}")
    @Size(groups = {OccupationView.OccupationPost.class, OccupationView.OccupationPut.class},
            min = 5, max = 45, message = "[{field.name}] {validation.between}")
    private String name;

    @JsonView({OccupationView.OccupationPost.class, OccupationView.OccupationPut.class})
    private boolean active;
}
