package org.almondiz.almondiz.follow;

import lombok.*;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.user.entity.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Follow_Table")
public class Follow extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "followerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User follower;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "followeeId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followee;

    @Setter
    private String alias;

    public void updateAlias(String alias) {
        this.alias = alias;
    }
}
