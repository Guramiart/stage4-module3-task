package com.mjc.school.security;

import com.mjc.school.repository.security.impl.RoleRepository;
import com.mjc.school.repository.security.impl.UserRepository;
import com.mjc.school.repository.security.model.Role;
import com.mjc.school.repository.security.model.User;
import com.mjc.school.service.security.AuthenticateService;
import com.mjc.school.service.security.JwtService;
import com.mjc.school.service.security.RoleService;
import com.mjc.school.service.security.UserService;
import com.mjc.school.service.security.request.AuthenticateRequest;
import com.mjc.school.service.security.request.RegisterRequest;
import com.mjc.school.service.security.response.AuthenticateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticateService authenticateService;

    private RegisterRequest registerRequest;
    private AuthenticateRequest authenticateRequest;

    private Role role;

    @BeforeEach
    public void init() {
        registerRequest = new RegisterRequest("TestUsername", "TestPassword");
        authenticateRequest = new AuthenticateRequest("TestUsername", "TestPassword");

        role = Role.builder()
                .name("USER")
                .build();
        User user = User.builder()
                .username(registerRequest.username())
                .password(encoder.encode(registerRequest.password()))
                .build();
        role.addUser(user);
    }

    @Test
    void shouldRegisterUser() {
        given(roleService.findByRoleName(any())).willReturn(role);
        given(jwtService.generateToken(any())).willReturn("TestToken");

        AuthenticateResponse response = authenticateService.register(registerRequest);
        assertThat(response.token()).isNotNull();
    }

    @Test
    void shouldAuthenticateUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        given(authenticationManager.authenticate(isA(Authentication.class))).willReturn(authentication);
        given(jwtService.generateToken(any())).willReturn("TestToken");

        AuthenticateResponse response = authenticateService.authenticate(authenticateRequest);
        assertThat(response.token()).isNotNull();
    }

}
