package org.almondiz.almondiz.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "User_Table")
public class User extends TimeStamped implements UserDetails {

    private static final String Role_PREFIX = "ROLE_";

    @Id
    // 상의할 필요
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private LocalDateTime deletedAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Long profileId;

    private Long tagId;

    private Long nutId;

    public User(String email, Long profileId, Long tagId, Long nutId, ProviderType providerType, Role role){
        this.email = email;
        this.profileId = profileId;
        this.tagId = tagId;
        this.nutId = nutId;
        this.providerType = providerType;
        this.status = Status.ALIVE;
        this.role = role;
    }

    public void update(Long profileId, Long tagId, Long nutId){
        this.profileId = profileId;
        this.tagId = tagId;
        this.nutId = nutId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role userRole = this.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role_PREFIX + userRole.toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
        return true;
    }
}
