package com.arch.soft.database.model.person;

import com.arch.soft.database.model.BaseEntity;
import com.arch.soft.database.model.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "person")
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity implements UserDetails {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Admin admin;

    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Client client;

    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Master master;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

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
        return isActive();
    }
}
