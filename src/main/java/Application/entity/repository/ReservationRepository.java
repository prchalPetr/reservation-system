package Application.entity.repository;

import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


}
