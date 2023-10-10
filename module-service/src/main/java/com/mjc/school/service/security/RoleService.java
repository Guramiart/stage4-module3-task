package com.mjc.school.service.security;

import com.mjc.school.repository.security.impl.RoleRepository;
import com.mjc.school.repository.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRoleName(String role) {
        Optional<Role> roleOptional = roleRepository.findByName(role);
        Role userRole = roleOptional.orElseGet(() ->
                roleRepository.save(Role.builder().name(role).build())
        );
        return roleRepository.save(userRole);
    }

}
