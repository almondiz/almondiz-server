package org.almondiz.almondiz.postFile.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

    Optional<PostFile> findById(Long fileId);

}
