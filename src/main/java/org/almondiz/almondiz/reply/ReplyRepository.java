package org.almondiz.almondiz.reply;

import org.almondiz.almondiz.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByComment(Comment comment);
}
