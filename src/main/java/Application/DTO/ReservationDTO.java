package Application.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long Id;
    @NotNull
    private LocalDateTime startReservation;
    @NotNull
    private LocalDateTime endReservation;
    private String note;

    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDateTime getStartReservation() {
        return startReservation;
    }

    public void setStartReservation(LocalDateTime startReservation) {
        this.startReservation = startReservation;
    }

    public LocalDateTime getEndReservation() {
        return endReservation;
    }

    public void setEndReservation(LocalDateTime endReservation) {
        this.endReservation = endReservation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
