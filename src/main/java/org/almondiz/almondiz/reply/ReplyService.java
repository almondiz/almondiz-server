package org.almondiz.almondiz.reply;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.ReplyNotFoundException;
import org.almondiz.almondiz.exception.exception.ReplyNotPermittedException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.replylike.ReplyLikeRepository;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final ReplyLikeRepository replyLikeRepository;

    @Transactional
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public Reply findById(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);
    }

    @Transactional
    public ReplyResponseDto findReplyDtoById(Long replyId, String uid) {
        Reply reply = this.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        boolean like = replyLikeRepository.findByReplyAndUser(reply, user).isPresent();

        return ReplyResponseDto.builder()
                               .commentId(reply.getComment().getCommentId())
                               .createdAt(reply.getCreatedAt())
                               .user(userService.getUserAsWriterResponseDto(reply.getUser().getUserId()))
                               .text(reply.getText())
                               .likedCount(reply.getLikedCount())
                               .like(like)
                               .build();
    }

    @Transactional
    public List<ReplyResponseDto> findAllReplyByComment(Long commentId, String uid) {
        Comment comment = this.findCommentById(commentId);

        return replyRepository.findAllByComment(comment)
                              .stream()
                              .map(reply -> this.findReplyDtoById(reply.getId(), uid))
                              .collect(Collectors.toList());
    }

    @Transactional
    public void create(String uid, Long commentId, ReplyRequestDto requestDto) {
        Comment comment = this.findCommentById(commentId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        replyRepository.save(Reply.builder()
                                  .text(requestDto.getText())
                                  .status(Status.ALIVE)
                                  .comment(comment)
                                  .user(user)
                                  .likedCount(0L)
                                  .build());
    }

    @Transactional
    public void update(String uid, Long replyId, ReplyRequestDto requestDto) {
        Reply reply = this.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        if (!reply.getUser().equals(user)) {
            throw new ReplyNotPermittedException();
        }

        if (!reply.getStatus().equals(Status.ALIVE)) {
            throw new ReplyNotFoundException();
        }

        reply.setText(requestDto.getText());
        replyRepository.save(reply);
    }

    @Transactional
    public void delete(String uid, Long replyId) {
        Reply reply = this.findById(replyId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        if (!reply.getUser().equals(user)) {
            throw new ReplyNotPermittedException();
        }

        if (!reply.getStatus().equals(Status.ALIVE)) {
            throw new ReplyNotFoundException();
        }

        reply.setStatus(Status.DELETED);
        replyRepository.save(reply);
    }
}
