package org.almondiz.almondiz.tagpost.entity;

import java.util.List;
import java.util.Optional;

import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostRepository  extends JpaRepository<TagPost, Long> {
    List<TagPost> findByPost(Post post);

    Optional<TagPost> findByPostAndTag(Post post, Tag tag);
}
