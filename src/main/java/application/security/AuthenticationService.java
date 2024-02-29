package application.security;

import application.dto.user.UserLoginRequestDto;
import application.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword()));
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
