package com.mjc.school.service.security;

import com.mjc.school.repository.security.impl.UserRepository;
import com.mjc.school.repository.security.model.Role;
import com.mjc.school.repository.security.model.User;
import com.mjc.school.service.security.request.AuthenticateRequest;
import com.mjc.school.service.security.request.RegisterRequest;
import com.mjc.school.service.security.response.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .password(encoder.encode(request.password()))
                .roles(Set.of(Role.builder().name("USER").build()))
                .build();
        String jwt = jwtService.generateToken(userService.save(user));
        return new AuthenticateResponse(jwt);
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return new AuthenticateResponse(jwt);
    }
}
