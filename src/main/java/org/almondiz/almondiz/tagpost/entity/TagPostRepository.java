package org.almondiz.almondiz.tagpost.entity;

import java.util.List;
import org.almondiz.almondiz.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostRepository  extends JpaRepository<TagPost, Long> {

    List<TagPost> findByPost(Post post);

}
