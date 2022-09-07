package org.almondiz.almondiz.response.user.entity;

import java.time.LocalDateTime;
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
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.entity.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "User_Table")
public class User extends TimeStamped {

    @Id
    // 상의할 필요
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String token;

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

    @ManyToOne(targetEntity = Tag.class)
    @JoinColumn(name = "tagId")
    private Tag tag;

    @ManyToOne(targetEntity = Nut.class)
    @JoinColumn(name = "nutId")
    private Nut nut;

    public User(String email, ProfileFile profileFile, Tag tag, Nut nut){
        this.email = email;
        this.profileFile = profileFile;
        this.tag = tag;
        this.nut = nut;
    }

    public void update(ProfileFile profileFile, Tag tag, Nut nut){
        // tagId, nutId 참조 무결성 방어 코드 필요
        this.profileFile = profileFile;
        this.tag = tag;
        this.nut = nut;
    }


}
