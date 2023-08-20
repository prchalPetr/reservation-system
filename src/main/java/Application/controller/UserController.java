package Application.controller;

import Application.DTO.UserDTO;
import Application.entity.UserEntity;
import Application.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping({"/registration", "/registration/"})
    public UserDTO registration(@RequestBody @Valid UserDTO userDTO){
        return userService.registration(userDTO);
    }
    @PostMapping({"/login","/login/"})
    public UserDTO login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws ServletException{
        request.login(userDTO.getEmail(), userDTO.getPassword());
        return getCurrentUser();
    }
    @DeleteMapping({"/logout","/logout/"})
    public String logout(HttpServletRequest request) throws ServletException{
        request.logout();
        return "Uživatel byl odhlášen";
    }
    @GetMapping({"/user","/user/"})
    public UserDTO getCurrentUser() throws ServletException{
        return userService.getCurrentUser();
    }
    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleServletException(){

    }
}
