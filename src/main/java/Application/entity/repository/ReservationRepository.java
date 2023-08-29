package Application.entity.repository;

import Application.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = "SELECT * FROM reservation WHERE user_id = (SELECT id FROM user WHERE email = :#{#email});", nativeQuery = true)
    List<ReservationEntity> getAllReservationFromUser(@Param("email") String email);

}
