package org.almondiz.almondiz.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentRequestDto;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.commentlike.CommentLike;
import org.almondiz.almondiz.commentlike.CommentLikeRepository;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotPermittedException;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final UserService userService;

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public void create(String uid, Long postId, CommentRequestDto commentRequestDto) {

        Post post = postService.findPostByPostId(postId);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.save(Comment.builder()
                                                        .text(commentRequestDto.getText())
                                                        .status(Status.ALIVE)
                                                        .post(post)
                                                        .user(user)
                                                        .likedCount(0L)
                                                        .build());
    }

    @Transactional
    public List<CommentResponseDto> findAllByPostId(Long postId, String uid) {
        Post post = postService.findPostByPostId(postId);
        return commentRepository.findByPost(post)
                                .stream().map(comment -> this.getCommentResponseDto(comment.getCommentId(), uid))
                                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto getCommentResponseDto(Long commentId, String uid) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            CommentNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndUser(comment, user);

        UserSimpleResponseDto writer = userService.getUserAsWriterResponseDto(comment.getUser().getUserId());

        List<String> reply = new ArrayList<>();

        return new CommentResponseDto(comment, writer, reply, commentLike.isPresent());
    }

    @Transactional
    public void update(String uid, Long commentId, CommentRequestDto commentRequestDto) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().equals(user)) {
            throw new CommentNotPermittedException();
        }

        comment.update(commentRequestDto);
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(String uid, Long commentId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().equals(user)) {
            throw new CommentNotPermittedException();
        }

        comment.setStatus(Status.DELETED);
        commentRepository.save(comment);
    }

    @Transactional
    public Long getCommentCountByPost(Post post) {
        return commentRepository.countByPost(post);
    }
}
