package org.almondiz.almondiz.comment.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.comment.dto.CommentRequestDto;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.entity.User;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Comment_Table")
public class Comment extends TimeStamped {

    /**
    inset 되기전 실행됨
    **/
    @PrePersist
    public void prePersist() {
        this.likedCount = this.likedCount == null ? 0 : this.likedCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Column(name = "like_count")
    private Long likedCount;

    public void update(CommentRequestDto commentRequestDto){
        this.text = commentRequestDto.getText();
    }

}
