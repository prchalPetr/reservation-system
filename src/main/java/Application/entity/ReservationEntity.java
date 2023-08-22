package Application.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private LocalDateTime startReservation;
    @Column(nullable = false)
    private LocalDateTime endReservation;

    @ManyToOne
    private UserEntity user;
    private String note;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
