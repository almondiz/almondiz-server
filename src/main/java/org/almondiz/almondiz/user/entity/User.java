package org.almondiz.almondiz.user.entity;

import java.time.LocalDateTime;
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

    private Long profileId;

    private Long tagId;

    private Long nutId;

    public User(Long userId, Long profileId, Long tagId, Long nutId){
        this.userId = userId;
        this.profileId = profileId;
        this.tagId = tagId;
        this.nutId = nutId;
    }

    public void update(Long profileId, Long tagId, Long nutId){
        // tagId, nutId 참조 무결성 방어 코드 필요
        this.profileId = profileId;
        this.tagId = tagId;
        this.nutId = nutId;
    }


}
