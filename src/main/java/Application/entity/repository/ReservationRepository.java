package Application.entity.repository;

import Application.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<ReservationEntity> findByDateTime(LocalDateTime dateTime);
}
