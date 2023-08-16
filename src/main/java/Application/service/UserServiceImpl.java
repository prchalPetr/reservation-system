package Application.service;

import Application.DTO.UserDTO;
import Application.entity.UserEntity;
import Application.entity.repository.UserRepository;
import Application.service.exceptations.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
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
}
