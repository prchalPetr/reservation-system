package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import Application.entity.repository.ReservationRepository;
import Application.service.exceptations.DuplicateDateTimeEception;
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

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        try {
            ReservationEntity entity = reservationMapper.reservationToEntity(reservationDTO);
            entity.setUser((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            entity = reservationRepository.save(entity);
            return reservationMapper.reservationToDTO(entity);
        } catch (DataIntegrityViolationException e){
            throw new DuplicateDateTimeEception();
        }
    }

    @Override
    public List<ReservationDTO> getAllReservation() {
        List<ReservationEntity> entities = reservationRepository.findAll();
        List<ReservationDTO> reservationDTOS = entities.stream().map(o -> reservationMapper.reservationToDTO(o)).toList();
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
    public ReservationDTO editReservation(ReservationDTO reservationDTO,Long id) {
        try {
            reservationDTO.setId(id);
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            reservationMapper.updateReservationEntity(reservationDTO, entity);
            ReservationEntity savedEntity = reservationRepository.save(entity);
            return reservationMapper.reservationToDTO(savedEntity);
        }catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }




}
