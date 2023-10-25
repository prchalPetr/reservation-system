package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.UserDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.DTO.mapper.UserMapper;
import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import Application.entity.repository.ReservationRepository;
import Application.service.exceptations.WrongDateTimeReservationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;





import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;



public class ReservationServiceImplTest {

    private ReservationDTO testingReservationDTO;

    private ReservationEntity testingReservationEntity;
    private UserDTO testingUserDTO;

    private List<ReservationDTO> allReservationDTO;
    private List<ReservationEntity> allReservation;
    private UserEntity testingUserEntity;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserService userService;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        allReservationDTO = new ArrayList<>();
        allReservation = new ArrayList<>();
        testingUserDTO = new UserDTO();
        testingUserDTO.setEmail("petr@petr.cz");
        testingUserDTO.setName("Petr Prchal");
        testingUserDTO.setPhoneNumber("4587556");
        testingUserDTO.setId(1L);
        testingUserDTO.setAdmin(false);

        testingUserEntity = new UserEntity();
        testingUserEntity.setEmail("petr@petr.cz");
        testingUserEntity.setName("Petr Prchal");
        testingUserEntity.setPhoneNumber("4587556");
        testingUserEntity.setId(1L);
        testingUserEntity.setAdmin(false);

        testingReservationDTO = createReservationDTO(1,"2023-10-25T13:52:39","2023-10-25T14:52:39",testingUserDTO);
        testingReservationEntity = createReservationEntity(1,"2023-10-25T13:52:39","2023-10-25T14:52:39",testingUserEntity);

        allReservationDTO.add(createReservationDTO(2,"2023-10-25T15:52:39","2023-10-25T17:52:39",testingUserDTO));
        allReservationDTO.add(createReservationDTO(3,"2023-10-25T18:52:39","2023-10-25T19:52:39",testingUserDTO));
        allReservation.add(createReservationEntity(2,"2023-10-25T15:52:39","2023-10-25T17:52:39",testingUserEntity));
        allReservation.add(createReservationEntity(3,"2023-10-25T18:52:39","2023-10-25T19:52:39",testingUserEntity));

        when(reservationMapper.reservationToEntity(any(ReservationDTO.class))).thenReturn(testingReservationEntity);
        when(reservationMapper.reservationToDTO(testingReservationEntity)).thenReturn(testingReservationDTO);
        when(reservationMapper.reservationToDTO(allReservation.get(0))).thenReturn(allReservationDTO.get(0));
        when(reservationMapper.reservationToDTO(allReservation.get(1))).thenReturn(allReservationDTO.get(1));


        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(testingReservationEntity);
        when(reservationRepository.findAll()).thenReturn(allReservation);

        when(userMapper.userDTOtoEntity(any(UserDTO.class))).thenReturn(testingUserEntity);
        when(userService.getCurrentUser()).thenReturn(testingUserDTO);


    }

    @Test
    public void createReservationTest() throws Exception{
        assertEquals(testingReservationDTO,reservationService.createReservation(testingReservationDTO));
    }
    @Test(expected = Exception.class)
    public void createReservationTestWithWrongEndTime() throws Exception{
        testingReservationDTO.setEndReservation(LocalDateTime.parse("2023-10-25T10:52:39"));
        assertEquals(testingReservationDTO,reservationService.createReservation(testingReservationDTO));
    }
    @Test(expected = Exception.class)
    public void createReservationTestWithBusyDate() throws Exception{
        allReservationDTO.add(createReservationDTO(4,"2023-10-25T14:00:39","2023-10-25T14:30:39",testingUserDTO));
        allReservation.add(createReservationEntity(4,"2023-10-25T14:00:39","2023-10-25T14:30:39",testingUserEntity));
        assertEquals(testingReservationDTO,reservationService.createReservation(testingReservationDTO));
    }

    private ReservationEntity createReservationEntity(long id, String start, String end, UserEntity user){
        ReservationEntity entity = new ReservationEntity();
        entity.setId(id);
        entity.setStartReservation(LocalDateTime.parse(start));
        entity.setEndReservation(LocalDateTime.parse(end));
        entity.setUser(user);
        return entity;
    }
    private ReservationDTO createReservationDTO(long id, String start, String end, UserDTO user){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(id);
        reservationDTO.setStartReservation(LocalDateTime.parse(start));
        reservationDTO.setEndReservation(LocalDateTime.parse(end));
        reservationDTO.setUser(user);
        return reservationDTO;
    }
}