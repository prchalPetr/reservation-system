package Application.controller;

import Application.DTO.ReservationDTO;
import Application.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("server/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping({"/create", "/create/"})
    public ReservationDTO createReservation(@RequestBody @Valid ReservationDTO reservationDTO){
        return reservationService.createReservation(reservationDTO);
    }
}
