package application.controller;

import application.dto.user.UserLoginRequestDto;
import application.dto.user.UserLoginResponseDto;
import application.dto.user.UserRegistrationRequestDto;
import application.dto.user.UserRegistrationResponseDto;
import application.security.AuthenticationService;
import application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User management", description = "Endpoints for users login and registration")
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Endpoint for user login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Endpoint for user registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
