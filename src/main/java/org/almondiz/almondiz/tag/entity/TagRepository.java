package org.almondiz.almondiz.tag.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findById(Long tagId);

    Optional<Tag> findByTagName(String tagName);

    List<Tag> findByTagNameLike(String tagName);
}
