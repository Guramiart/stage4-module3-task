package com.mjc.school.security;

import com.mjc.school.repository.security.model.User;
import com.mjc.school.service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    private final static String SECRET = "2ffa1ad0df4bc392140f463d0b4120dc51288abebbf3a1cff3523fa4b217643b";
    private final JwtService jwtService = new JwtService();

    private final User user = User.builder()
            .username("John Doe")
            .password("TestPass")
            .build();

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", "120000");
    }

    @Test
    void shouldGenerateJWTToken() {
        assertThat(jwtService.generateToken(user)).isNotNull();
    }

    @Test
    void shouldExtractClaimFromToken() {
        String token = jwtService.generateToken(user);
        String name = jwtService.extractUsername(token);
        assertEquals(user.getUsername(), name);
    }
}
