package Application.controller;

import Application.DTO.ReservationDTO;
import Application.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("server/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping({"/create", "/create/"})
    public ReservationDTO createReservation(@RequestBody @Valid ReservationDTO reservationDTO){
        return reservationService.createReservation(reservationDTO);
    }
    @GetMapping({"/reservations","/reservations/"})
    public List<ReservationDTO> getAllReservations(){
        return reservationService.getAllReservation();
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
    public ReservationDTO editReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long id){
        return reservationService.editReservation(reservationDTO,id);
    }
}
