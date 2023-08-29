package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.UserDTO;
import Application.entity.UserEntity;
import Application.service.exceptations.WrongDateTimeReservationException;

import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO) throws Exception;
    List<ReservationDTO> getAllReservation();
    ReservationDTO getReservation(Long id);
    String deleteReservation(Long id);
    ReservationDTO editReservation(ReservationDTO reservationDTO, Long id, UserDTO userDTO) throws WrongDateTimeReservationException;
    List<ReservationDTO> getAllReservationFromUser(String email);

}
