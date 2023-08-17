package Application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long Id;
    @NotNull
    @NotBlank
    private LocalDateTime dateTime;
    @NotNull
    @NotBlank
    @Min(value = 30)
    private Integer duration;
    private String note;
}
