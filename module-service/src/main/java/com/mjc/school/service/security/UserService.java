package com.mjc.school.service.security;

import com.mjc.school.repository.security.impl.UserRepository;
import com.mjc.school.repository.security.model.Role;
import com.mjc.school.repository.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " not found!")
        );
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId ", Role.class);
        query.setParameter("userId", user.getId());
        user.setAuthorities(query.getResultList().stream().map(r ->
                new SimpleGrantedAuthority(r.getAuthority())).collect(Collectors.toList()));
        return user;
    }

}
