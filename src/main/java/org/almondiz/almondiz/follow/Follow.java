package org.almondiz.almondiz.follow;

import lombok.*;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.response.user.entity.User;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Follow_Table")
public class Follow extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followeeId")
    private User followee;

    @Setter
    private String alias;

    public void updateAlias(String alias) {
        this.alias = alias;
    }
}
