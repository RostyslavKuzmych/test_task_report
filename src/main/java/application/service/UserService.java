package application.service;

import application.dto.user.UserRegistrationRequestDto;
import application.dto.user.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);
}
