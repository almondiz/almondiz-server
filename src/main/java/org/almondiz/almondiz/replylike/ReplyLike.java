package org.almondiz.almondiz.replylike;

import lombok.*;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.reply.Reply;
import org.almondiz.almondiz.user.entity.User;

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

    @ManyToOne(targetEntity = Reply.class)
    @JoinColumn(name = "replyId")
    private Reply reply;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

}
