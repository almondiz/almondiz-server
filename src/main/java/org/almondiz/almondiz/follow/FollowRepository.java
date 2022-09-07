package org.almondiz.almondiz.follow;

import org.almondiz.almondiz.response.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
    Optional<Follow> findById(Long id);
    List<Follow> findAllByFollowee(User followee);
    List<Follow> findAllByFollower(User follower);
}
