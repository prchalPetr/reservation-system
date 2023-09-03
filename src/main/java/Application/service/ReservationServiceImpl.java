package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.UserDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.DTO.mapper.UserMapper;
import Application.entity.ReservationEntity;
import Application.entity.repository.ReservationRepository;
import Application.service.exceptations.DuplicateDateTimeEception;
import Application.service.exceptations.WrongDateTimeReservationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    /**
     * Metoda na vytvoření rezervace
     * @param reservationDTO - rezervace k vytvoření
     * @return  - vyvtořená rezervace typu ReservationDTO
     * @throws Exception - ošetření obsazenosti a nesmyslných hodnot
     */
    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) throws Exception {
        try {
            if (reservationDTO.getEndReservation().compareTo(reservationDTO.getStartReservation()) <= 0)
                throw new WrongDateTimeReservationException();
            final ReservationEntity entity = reservationMapper.reservationToEntity(reservationDTO);
            entity.setUser(userMapper.userDTOtoEntity(userService.getCurrentUser()));
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

    /**
     * Metoda na získání všech vyvtvořených rezervacích
     * @return - list typu ReservationDTO, který obsahuje všechny vytvořené rezervace
     */
    @Override
    public List<ReservationDTO> getAllReservation() {
        List<ReservationEntity> entities = reservationRepository.findAll();
        return convertListReservationEntityToListDTO(entities);
    }

    /**
     * Metoda na vyhledání konkrétní rezervace
     * @param id - Id rezervace, o které chceme detailní informace
     * @return - hledaná rezervace typu ReservationDTO
     */
    @Override
    public ReservationDTO getReservation(Long id) {
        try {
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            return reservationMapper.reservationToDTO(entity);
        } catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }


    /**
     * Metoda na smazání konkrétní rezervace
     * @param id - Id rezervace, o které chceme smazat
     * @return - informaci o smazání
     */
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

    /**
     * Metoda na editaci rezervace
     * @param reservationDTO - nové hodnoty rezervace, kterou chceme změnit
     * @param id - Id rezervace, o které chceme změnit
     * @param userDTO - aktuálně přihlášený user
     * @return  - uložená a změněná rezervace
     * @throws WrongDateTimeReservationException - ošetření obsazenosti a nesmyslných hodnot
     */
    @Override
    public ReservationDTO editReservation(ReservationDTO reservationDTO, Long id, UserDTO userDTO) throws WrongDateTimeReservationException {
        try {
            reservationDTO.setId(id);
            ReservationEntity entity = reservationRepository.getReferenceById(id);
            if (entity.getUser().getEmail().equals(userDTO.getEmail()) || userDTO.isAdmin()){
                if (checkOccupancy(reservationDTO))
                    throw new WrongDateTimeReservationException();
                reservationMapper.updateReservationEntity(reservationDTO, entity);
                ReservationEntity savedEntity = reservationRepository.save(entity);
                return reservationMapper.reservationToDTO(savedEntity);
            }
           throw new RuntimeException();
        }catch (RuntimeException e){
            throw new EntityNotFoundException();
        }
    }

    /**
     * Metoda na vyhledání rezervací vazající se na konkrétního uživatele
     * @param email - email uživatele
     * @return - list všech rezervací, které má uživatel
     */
    @Override
    public List<ReservationDTO> getAllReservationFromUser(String email) {
        List<ReservationEntity> entities = reservationRepository.getAllReservationFromUser(email);
        return convertListReservationEntityToListDTO(entities);
    }

    /**
     * Metoda na nalezení rezervací v daný den
     * @param tagetDay - den, kde chceme vědět, jaké jsou rezervace
     * @return - list rezervací v daný den
     */
    @Override
    public List<ReservationDTO> getAllAtDay(LocalDate tagetDay) {
        List<ReservationEntity> entities = reservationRepository.findAll().stream().filter(entita -> entita.getStartReservation().getDayOfYear() == tagetDay.getDayOfYear()).toList();
        return convertListReservationEntityToListDTO(entities);
    }

    /**
     * Metoda na ošetření obsazenosti a nesmyslných hodnot
     * @param reservationDTO - rezervace, která se má otestovat, zda splňuje podmínky
     * @return - Boolean, zda podmínky jsou splněny, či nikoliv
     */
    private Boolean checkOccupancy(ReservationDTO reservationDTO){
     return getAllReservation().stream().anyMatch(databaze -> reservationDTO.getStartReservation().compareTo(databaze.getStartReservation()) >= 0 && reservationDTO.getStartReservation().compareTo(databaze.getEndReservation()) < 0 || reservationDTO.getEndReservation().compareTo(databaze.getStartReservation()) > 0 && reservationDTO.getEndReservation().compareTo(databaze.getEndReservation()) <= 0 || databaze.getStartReservation().compareTo(reservationDTO.getStartReservation()) >= 0 && databaze.getStartReservation().compareTo(reservationDTO.getEndReservation()) < 0);
    }

    /**
     * Pomocná metoda na převed listu typu ReservationEntity na list typu ReservationDTO
     * @param entities - list typu ReservationEntity
     * @return - list typu ReservationDTO
     */
    private List<ReservationDTO> convertListReservationEntityToListDTO(List<ReservationEntity> entities){
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        entities.forEach(o -> reservationDTOS.add(reservationMapper.reservationToDTO(o)));
        return reservationDTOS;
    }


}
