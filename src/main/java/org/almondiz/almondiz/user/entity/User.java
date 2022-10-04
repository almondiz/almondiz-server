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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.entity.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "User_Table")
public class User extends TimeStamped implements UserDetails {

    private static final String Role_PREFIX = "ROLE_";

    // 디비 uid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String providerUid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    // 토큰 사용자 식별 uid
    @Column(nullable = false)
    private String uid;

    private String email;

    private LocalDateTime deletedAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(targetEntity = ProfileFile.class)
    @JoinColumn(name = "profileId")
    private ProfileFile profileFile;

    private String emoji;

    private String color;

    @ManyToOne(targetEntity = Tag.class)
    @JoinColumn(name = "tagId")
    private Tag tag;

    @ManyToOne(targetEntity = Nut.class)
    @JoinColumn(name = "nutId")
    private Nut nut;

    public User(String uid, String providerUid, String email, ProfileFile profileFile, Tag tag, Nut nut, ProviderType providerType, Role role, Thumb thumb){
        this.uid =uid;
        this.providerUid = providerUid;
        this.email = email;
        this.profileFile = profileFile;
        this.tag = tag;
        this.nut = nut;
        this.providerType = providerType;
        this.status = Status.ALIVE;
        this.role = role;
        this.emoji = thumb.getEmoji();
        this.color = thumb.getColor();
    }

    public void update(ProfileFile profileFile, Tag tag, Nut nut, Thumb thumb){
        this.profileFile = profileFile;
        this.tag = tag;
        this.nut = nut;
        this.emoji = thumb.getEmoji();
        this.color = thumb.getColor();
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
        return this.getUid();
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
