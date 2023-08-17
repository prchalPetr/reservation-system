package Application.service;

import Application.DTO.ReservationDTO;

import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    List<ReservationDTO> getAllReservation();
    ReservationDTO getReservation(Long id);
    String deleteReservation(Long id);
    ReservationDTO editReservation(ReservationDTO reservationDTO,Long id);
}
