package org.almondiz.almondiz.commentlike;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.CommentService;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CommentLikeExistedException;
import org.almondiz.almondiz.exception.exception.CommentLikeNotFoundException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    private final UserService userService;

    private final CommentService commentService;

    @Transactional
    public CommentLike findByCommentAndUser(Comment comment, User user) {
        return commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(CommentLikeNotFoundException::new);
    }

    @Transactional
    public void create(Long commentId, String uid) {
        Comment comment = commentService.findById(commentId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndUser(comment, user);

        CommentLike newLike;
        if (commentLike.isPresent()) {
            if(commentLike.get().getStatus().equals(Status.ALIVE)) {
                throw new CommentLikeExistedException();
            }else{
                commentLike.get().setStatus(Status.ALIVE);
                newLike = commentLike.get();
            }
        } else {
            newLike = CommentLike.builder()
                                 .comment(comment)
                                 .user(user)
                                 .status(Status.ALIVE)
                                 .build();
        }
        commentLikeRepository.save(newLike);
    }

    @Transactional
    public void delete(Long commentId, String uid) {
        Comment comment = commentService.findById(commentId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        CommentLike commentLike = this.findByCommentAndUser(comment, user);

        commentLike.setStatus(Status.DELETED);

        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public boolean isLike(Long commentId, String uid) {
        Comment comment = commentService.findById(commentId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return commentLikeRepository.findByCommentAndUser(comment, user).isPresent();
    }

}
