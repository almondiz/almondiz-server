package org.almondiz.almondiz.replylike;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.ReplyLikeExistedException;
import org.almondiz.almondiz.exception.exception.ReplyLikeNotFoundException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.reply.Reply;
import org.almondiz.almondiz.reply.ReplyService;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {

    private final ReplyLikeRepository replyLikeRepository;

    private final UserService userService;

    private final ReplyService replyService;

    @Transactional
    public ReplyLike findByReplyAndUser(Reply reply, User user) {
        return replyLikeRepository.findByReplyAndUser(reply, user).orElseThrow(ReplyLikeNotFoundException::new);
    }

    @Transactional
    public void create(Long replyId, String uid) {
        Reply reply = replyService.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Optional<ReplyLike> replyLike = replyLikeRepository.findByReplyAndUser(reply, user);

        ReplyLike newLike;
        if (replyLike.isPresent()) {
            if (replyLike.get().getStatus().equals(Status.ALIVE)) {
                throw new ReplyLikeExistedException();
            } else {
                replyLike.get().setStatus(Status.ALIVE);
                newLike = replyLike.get();
            }
        } else {
            newLike = ReplyLike.builder()
                               .reply(reply)
                               .user(user)
                               .status(Status.ALIVE)
                               .build();
        }

        replyLikeRepository.save(newLike);
    }

    @Transactional
    public void delete(Long replyId, String uid) {
        Reply reply = replyService.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        ReplyLike replyLike = this.findByReplyAndUser(reply, user);

        replyLike.setStatus(Status.DELETED);

        replyLikeRepository.save(replyLike);
    }

    @Transactional
    public boolean isLike(Long replyId, String uid) {
        Reply reply = replyService.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return replyLikeRepository.findByReplyAndUser(reply, user).isPresent();
    }
}

