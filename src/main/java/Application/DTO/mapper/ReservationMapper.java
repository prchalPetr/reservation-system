package Application.DTO.mapper;

import Application.DTO.ReservationDTO;
import Application.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO reservationToDTO(ReservationEntity source);
    ReservationEntity reservationToEntity(ReservationDTO source);
}
