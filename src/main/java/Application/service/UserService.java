package Application.service;

import Application.DTO.UserDTO;
import jakarta.servlet.ServletException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
        UserDTO registration(UserDTO userDTO);
        UserDTO getCurrentUser() throws ServletException;
}
