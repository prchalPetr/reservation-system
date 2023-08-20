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

    @Override
    public UserDTO getCurrentUser() throws ServletException {
        try {
            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDTO model = new UserDTO();
            model.setId(userEntity.getId());
            model.setEmail(userEntity.getEmail());
            model.setName(userEntity.getName());
            model.setAdmin(userEntity.isAdmin());
            model.setReservations(userEntity.getReservations().stream().map(res -> reservationMapper.reservationToDTO(res)).toList());
            return model;
        } catch (ClassCastException e){
            throw new ServletException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Email: " + username + " not found"));
    }
}
