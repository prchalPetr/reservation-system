package Application.controller;

import Application.DTO.ReservationDTO;
import Application.entity.UserEntity;
import Application.service.ReservationService;
import Application.service.UserService;
import Application.service.exceptations.WrongDateTimeReservationException;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("server/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    @PostMapping({"/create", "/create/"})
    public ReservationDTO createReservation(@RequestBody @Valid ReservationDTO reservationDTO) throws Exception {
        return reservationService.createReservation(reservationDTO);
    }
    @GetMapping({"/reservations","/reservations/"})
    public List<ReservationDTO> getAllReservations(){
        return reservationService.getAllReservation();
    }
    @GetMapping({"/MyReservations{email}","/MyReservations/{email}"})
    public List<ReservationDTO> getAllUserReservations(@PathVariable String email){
        return reservationService.getAllReservationFromUser(email);
    }
    @GetMapping({"/reservations/{id}","/reservations/{id}/"})
    public ReservationDTO getReservation(@PathVariable Long id){
        return reservationService.getReservation(id);
    }
    @DeleteMapping({"/reservations/{id}","/reservations/{id}/"})
    public String removeReservation(@PathVariable Long id){
        return reservationService.deleteReservation(id);
    }
    @PutMapping({"/reservations/{id}","/reservations/{id}/"})
    public ReservationDTO editReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long id) throws WrongDateTimeReservationException, ServletException {
        return reservationService.editReservation(reservationDTO,id,userService.getCurrentUser());
    }
}
