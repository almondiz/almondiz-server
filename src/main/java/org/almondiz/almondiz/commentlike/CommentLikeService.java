package org.almondiz.almondiz.commentlike;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public Optional<CommentLike> findByCommentAndUser(Comment comment, User user){
        return commentLikeRepository.findByCommentAndUser(comment, user);
    }

}
