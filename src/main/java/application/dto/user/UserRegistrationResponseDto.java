package application.dto.user;

import lombok.Data;

@Data
public class UserRegistrationResponseDto {
    private String email;
    private String password;
}
