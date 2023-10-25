package Application.service;

import Application.DTO.UserDTO;
import Application.configuration.SecurityConfiguration;
import Application.entity.UserEntity;
import Application.entity.repository.UserRepository;
import jakarta.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity entityUSer;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        entityUSer = new UserEntity();
        entityUSer.setEmail("petr@petr.cz");
        entityUSer.setName("Petr Prchal");
        entityUSer.setPhoneNumber("4587556");
        entityUSer.setId(1);
        entityUSer.setAdmin(false);

        when(userRepository.save(any(UserEntity.class))).thenReturn(entityUSer);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("12345678");

    }

    @Test
    public void registration() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Petr Prchal");
        userDTO.setEmail("petr@petr.cz");
        userDTO.setPhoneNumber("4587556");
        userDTO.setPassword("12345678");
        UserDTO testDTO = userService.registration(userDTO);
        assertTrue(userDTO.getName().equals(testDTO.getName()));
        assertTrue(userDTO.getEmail().equals(testDTO.getEmail()));
        assertTrue(userDTO.getPhoneNumber().equals(testDTO.getPhoneNumber()));
        assertEquals(1,testDTO.getId().longValue());
    }


}