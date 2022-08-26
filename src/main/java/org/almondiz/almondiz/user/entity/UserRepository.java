package org.almondiz.almondiz.user.entity;

import java.util.List;
import java.util.Optional;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById(Long userId);

}
