package org.almondiz.almondiz.report;

import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByUserAndPost(User user, Post post);

    List<Report> findAllByUser(User user);;

    List<Report> findAllByPost(Post post);

    long countByUser(User user);

    long countByPost(Post post);
}
