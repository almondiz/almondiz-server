package org.almondiz.almondiz.commentlike;

import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
