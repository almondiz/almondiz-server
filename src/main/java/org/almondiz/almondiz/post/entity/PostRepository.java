package org.almondiz.almondiz.post.entity;

import java.util.List;
import java.util.Optional;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
    Optional<Post> findByPostId(Long postId);
    List<Post> findByUser(User user);
    List<Post> findByShop(Shop shop);

}
