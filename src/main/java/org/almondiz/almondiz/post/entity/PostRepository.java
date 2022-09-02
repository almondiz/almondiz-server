package org.almondiz.almondiz.post.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
    Optional<Post> findByPostId(Long postId);
    List<Post> findByUserId(Long userId);
    List<Post> findByStoreId(Long storeId);

}
