package Application.controller;

import Application.DTO.UserDTO;
import Application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping({"/registration", "/registration/"})
    public UserDTO registration(@RequestBody @Valid UserDTO userDTO){
        return userService.registration(userDTO);
    }
}
