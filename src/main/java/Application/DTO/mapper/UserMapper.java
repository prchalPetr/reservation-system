package Application.DTO.mapper;

import Application.DTO.UserDTO;
import Application.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "reservations", ignore = true)
    UserEntity userDTOtoEntity(UserDTO source);
}
