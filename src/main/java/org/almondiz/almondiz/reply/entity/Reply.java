package org.almondiz.almondiz.reply.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentRequestDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.reply.dto.ReplyRequestDto;
import org.almondiz.almondiz.user.entity.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Reply_Table")
public class Reply extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(nullable = false)
    private String text;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    public void update(ReplyRequestDto requestDto){
        this.text = requestDto.getText();
    }

    // post 조회시 댓글 갯수 수정 -> 완료
    // comment 조회시 reply 같이 response dto 수정 -> 완료
}
