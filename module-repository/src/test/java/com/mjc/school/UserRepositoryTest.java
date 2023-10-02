package com.mjc.school;

import com.mjc.school.repository.config.JpaRepositoryConfig;
import com.mjc.school.repository.security.impl.UserRepository;
import com.mjc.school.repository.security.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableAutoConfiguration
@AutoConfigureTestDatabase
@ContextConfiguration(classes = { JpaRepositoryConfig.class })
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = User.builder()
            .username("Test Username")
            .password("Test password")
            .build();

    @Test
    void shouldReturnUserByUsername() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertTrue(savedUser.isAccountNonExpired());
        assertTrue(savedUser.isEnabled());
        assertTrue(savedUser.isAccountNonLocked());
        assertTrue(savedUser.isCredentialsNonExpired());
    }
}
