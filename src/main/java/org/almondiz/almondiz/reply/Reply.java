package org.almondiz.almondiz.reply;

import lombok.*;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.user.entity.User;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Reply_Table")
public class Reply extends TimeStamped {

    /**
     inset 되기전 실행됨
     **/
    @PrePersist
    public void prePersist() {
        this.likedCount = this.likedCount == null ? 0 : this.likedCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String text;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Column(name = "like_count")
    private Long likedCount;
}
