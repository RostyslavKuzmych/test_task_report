package application.service.impl;

import application.dto.user.UserRegistrationRequestDto;
import application.dto.user.UserRegistrationResponseDto;
import application.exception.EntityNotFoundException;
import application.mapper.UserMapper;
import application.model.Role;
import application.model.User;
import application.repository.UserRepository;
import application.service.UserService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String EXCEPTION_MESSAGE = "You are already registered";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        if (!userRepository.findUserByEmail(requestDto.getEmail()).isEmpty()) {
            throw new EntityNotFoundException(EXCEPTION_MESSAGE);
        }
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role role = new Role().setRoleName(Role.RoleName.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userMapper.toDto(userRepository.save(user));
    }
}
