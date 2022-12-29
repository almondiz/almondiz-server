package org.almondiz.almondiz.reply.replylike;

import org.almondiz.almondiz.reply.entity.Reply;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    Optional<ReplyLike> findByReplyAndUser(Reply reply, User user);

    Long countByReply(Reply reply);
}
