package com.mjc.school.security;

import com.mjc.school.repository.security.impl.UserRepository;
import com.mjc.school.repository.security.model.Role;
import com.mjc.school.repository.security.model.User;
import com.mjc.school.service.security.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private TypedQuery<Role> query;
    @Mock
    private EntityManager entityManager;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .username("TestUsername")
                .password("TestPassword")
                .roles(Set.of(Role.builder().authority("USER").build()))
                .build();
    }

    @Test
    void shouldFindUserByUsername() {
        final String username = "TestUsername";
        final String password = "TestPassword";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(entityManager.createQuery("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId ", Role.class))
                .willReturn(query);
        given(query.getResultList()).willReturn(List.of(Role.builder().authority("USER").build()));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).allMatch(r -> r.equals("USER")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        final String username = "TestUsername";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}
