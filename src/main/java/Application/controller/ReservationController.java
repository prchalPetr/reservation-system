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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    @GetMapping({"/all","/all/"})
    public List<ReservationDTO> getAllReservations(){
        return reservationService.getAllReservation();
    }
    @GetMapping({"/myReservations/{email}","/myReservations/{email}"})
    public List<ReservationDTO> getAllUserReservations(@PathVariable String email){
        return reservationService.getAllReservationFromUser(email);
    }
    @GetMapping({"/{id}","/{id}/"})
    public ReservationDTO getReservation(@PathVariable Long id){
        return reservationService.getReservation(id);
    }
    @DeleteMapping({"/{id}","/{id}/"})
    public String removeReservation(@PathVariable Long id){
        return reservationService.deleteReservation(id);
    }
    @PutMapping({"/{id}","/{id}/"})
    public ReservationDTO editReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long id) throws WrongDateTimeReservationException, ServletException {
        return reservationService.editReservation(reservationDTO,id,userService.getCurrentUser());
    }
    @GetMapping({"/atDay/{targetDate}", "/atDay/{targetDate}/"})
    public List<ReservationDTO> getAllReservationsAtDay(@PathVariable String targetDate){
        return reservationService.getAllAtDay(LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
