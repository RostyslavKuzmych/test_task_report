package application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Size(min = 6)
    private String password;
}
