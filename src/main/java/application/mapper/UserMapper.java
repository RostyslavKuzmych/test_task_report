package application.mapper;

import application.config.MapperConfig;
import application.dto.user.UserRegistrationRequestDto;
import application.dto.user.UserRegistrationResponseDto;
import application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(UserRegistrationRequestDto userRegistrationRequestDto);

    UserRegistrationResponseDto toDto(User user);
}
