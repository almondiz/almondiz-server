package org.almondiz.almondiz.comment.entity;

import java.util.List;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.response.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);

}
