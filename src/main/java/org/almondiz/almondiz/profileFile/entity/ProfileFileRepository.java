package org.almondiz.almondiz.profileFile.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileFileRepository extends JpaRepository<ProfileFile, Long> {

    Optional<ProfileFile> findById(Long fileId);

}
