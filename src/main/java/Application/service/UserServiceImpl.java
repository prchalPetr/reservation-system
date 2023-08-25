package Application.service;

import Application.DTO.ReservationDTO;
import Application.DTO.UserDTO;
import Application.DTO.mapper.ReservationMapper;
import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import Application.entity.repository.UserRepository;
import Application.service.exceptations.DuplicateEmailException;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationMapper reservationMapper;

    /**
     * Metoda na registraci nových uživatelů, v případě obsazeného emailu se vyhodí chyba
     * @param userDTO - uživatel, který se má zaregistrovat
     * @return - zaregistrovaný uživatel bez hesla typu UserDTO
     */
    @Override
    public UserDTO registration(UserDTO userDTO) {
        try {
            UserEntity entity = new UserEntity();
            entity.setName(userDTO.getName());
            entity.setEmail(userDTO.getEmail());
            entity.setPhoneNumber(userDTO.getPhoneNumber());
            entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            entity = userRepository.save(entity);

            UserDTO user = new UserDTO();
            user.setName(entity.getName());
            user.setEmail(entity.getEmail());
            user.setPhoneNumber(entity.getPhoneNumber());
            user.setId(entity.getId());
            return user;
        } catch (DataIntegrityViolationException e){
            throw new DuplicateEmailException();
        }
    }

    /**
     * Metoda na získání příhlášeného uživatele
     * @return - přihlášený uživatel bez hesla typu UserDTO
     * @throws ServletException - chyba v případě, že se nepodaří získat přihlášeného uživatele
     */
    @Override
    public UserDTO getCurrentUser() throws ServletException {
        try {
            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDTO model = new UserDTO();
            model.setId(userEntity.getId());
            model.setEmail(userEntity.getEmail());
            model.setName(userEntity.getName());
            model.setAdmin(userEntity.isAdmin());
            return model;
        } catch (ClassCastException e){
            throw new ServletException();
        }
    }

    /**
     * Metoda na získání základních informací o uživateli pomocí emailu
     * @param username - email hledaného uživatele
     * @return - základní informace o uživateli
     * @throws UsernameNotFoundException - uživatel nebyl nalezen v databázi
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Email: " + username + " not found"));
    }
}
