package Application.DTO.mapper;

import Application.DTO.ReservationDTO;
import Application.DTO.UserDTO;
import Application.entity.ReservationEntity;
import Application.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "user", expression = "java(getUser(source))")
    ReservationDTO reservationToDTO(ReservationEntity source);
    @Mapping(target = "user", ignore = true)
    ReservationEntity reservationToEntity(ReservationDTO source);
    @Mapping(target = "user", expression = "java(updateUser(source))")
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
    default UserDTO getUser(ReservationEntity source){
        UserDTO user = new UserDTO();
        user.setName(source.getUser().getName());
        user.setEmail(source.getUser().getEmail());
        user.setId(source.getUser().getId());
        user.setPhoneNumber(source.getUser().getPhoneNumber());

        return user;
    }
    default UserEntity updateUser(ReservationDTO source){
        UserEntity user = new UserEntity();
        user.setName(source.getUser().getName());
        user.setEmail(source.getUser().getEmail());
        user.setId(source.getUser().getId());
        user.setPhoneNumber(source.getUser().getPhoneNumber());
        return user;
    }
}
