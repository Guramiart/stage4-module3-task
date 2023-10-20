package com.mjc.school.repository.security.model;

import com.mjc.school.repository.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")

@Entity
@Table(name = "users")
public class User implements BaseEntity<Long>, UserDetails {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username", unique = true)
    private String username;

    @Column(name = "Password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "User_Id") },
            inverseJoinColumns = { @JoinColumn(name = "Role_Id") }
    )
    private final Set<Role> roles = new HashSet<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
