package org.almondiz.almondiz.postscrap;

import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {

    Optional<PostScrap> findByUserAndPost(User user, Post post);

    List<PostScrap> findAllByUser(User user);

    long countByPost(Post post);

    long countByUser(User user);
}
