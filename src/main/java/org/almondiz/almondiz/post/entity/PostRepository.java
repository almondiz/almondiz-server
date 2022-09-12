package org.almondiz.almondiz.post.entity;

import java.util.List;
import java.util.Optional;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.response.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
    Optional<Post> findByPostId(Long postId);
    List<Post> findByUser(User user);
    List<Post> findByStore(Store store);

}
