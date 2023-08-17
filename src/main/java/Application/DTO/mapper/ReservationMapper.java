package Application.DTO.mapper;

import Application.DTO.ReservationDTO;
import Application.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO reservationToDTO(ReservationEntity source);
    ReservationEntity reservationToEntity(ReservationDTO source);
    void updateReservationEntity(ReservationDTO source, @MappingTarget ReservationEntity target);
}
