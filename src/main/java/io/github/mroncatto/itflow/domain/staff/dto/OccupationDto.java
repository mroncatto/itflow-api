package io.github.mroncatto.itflow.domain.staff.dto;

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
public class OccupationDto {

    public interface OccupationView {
        interface OccupationPost {}
        interface OccupationPut {}
    }

    @JsonView(OccupationView.OccupationPut.class)
    private Long id;

    @NotNull(groups = {OccupationView.OccupationPost.class, OccupationView.OccupationPut.class}, message = "The name field is required")
    @Size(groups = {OccupationView.OccupationPost.class, OccupationView.OccupationPut.class}, min = 5, max = 45, message = "The name field must contain between 5 and 45 characters")
    private String name;

    @JsonView({OccupationView.OccupationPost.class, OccupationView.OccupationPut.class})
    private boolean active;
}
