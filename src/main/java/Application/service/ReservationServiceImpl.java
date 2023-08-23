package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.DTO.mapper.UserMapper;
import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import Application.entity.repository.ReservationRepository;
import Application.service.exceptations.DuplicateDateTimeEception;
import Application.service.exceptations.WrongDateTimeReservationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) throws Exception {
        try {
            if (reservationDTO.getEndReservation().compareTo(reservationDTO.getStartReservation()) <= 0)
                throw new WrongDateTimeReservationException();
            final ReservationEntity entity = reservationMapper.reservationToEntity(reservationDTO);
            entity.setUser((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if (checkOccupancy(reservationDTO))
            {
                throw new WrongDateTimeReservationException();
            }
            else
                return reservationMapper.reservationToDTO(reservationRepository.save(entity));
        } catch (DataIntegrityViolationException e){
            throw new DuplicateDateTimeEception();
        }
    }

    @Override
    public List<ReservationDTO> getAllReservation() {
        List<ReservationEntity> entities = reservationRepository.findAll();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        entities.stream().forEach(o -> reservationDTOS.add(reservationMapper.reservationToDTO(o)));

        return reservationDTOS;
    }

    @Override
    public ReservationDTO getReservation(Long id) {
        try {
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            return reservationMapper.reservationToDTO(entity);
        } catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }

    @Override
    public String deleteReservation(Long id) {
        try {
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            reservationRepository.delete(entity);
            return "Rezervace byla odstraněna";
        } catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }

    @Override
    public ReservationDTO editReservation(ReservationDTO reservationDTO,Long id) throws WrongDateTimeReservationException {
        try {
            reservationDTO.setId(id);
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            if (checkOccupancy(reservationDTO))
                throw new WrongDateTimeReservationException();
            reservationMapper.updateReservationEntity(reservationDTO, entity);
            ReservationEntity savedEntity = reservationRepository.save(entity);
            return reservationMapper.reservationToDTO(savedEntity);
        }catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }
    private Boolean checkOccupancy(ReservationDTO reservationDTO){
     return getAllReservation().stream().anyMatch(databaze -> reservationDTO.getStartReservation().compareTo(databaze.getStartReservation()) >= 0 && reservationDTO.getStartReservation().compareTo(databaze.getEndReservation()) < 0 || reservationDTO.getEndReservation().compareTo(databaze.getStartReservation()) > 0 && reservationDTO.getEndReservation().compareTo(databaze.getEndReservation()) <= 0 || databaze.getStartReservation().compareTo(reservationDTO.getStartReservation()) >= 0 && databaze.getStartReservation().compareTo(reservationDTO.getEndReservation()) < 0);
    }



}
