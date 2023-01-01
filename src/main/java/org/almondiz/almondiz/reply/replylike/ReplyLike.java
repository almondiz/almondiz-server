package org.almondiz.almondiz.reply.replylike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.reply.entity.Reply;
import org.almondiz.almondiz.user.entity.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "ReplyLike_Table")
public class ReplyLike extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Reply.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "replyId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reply reply;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
