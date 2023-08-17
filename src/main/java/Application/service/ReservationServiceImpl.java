package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.entity.ReservationEntity;
import Application.entity.repository.ReservationRepository;
import Application.service.exceptations.DuplicateDateTimeEception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
            entity = reservationRepository.save(entity);
            return reservationMapper.reservationToDTO(entity);
        } catch (DataIntegrityViolationException e){
            throw new DuplicateDateTimeEception();
        }
    }
}
