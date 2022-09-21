package org.almondiz.almondiz.comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentRequestDto;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotPermittedException;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.dto.UserAsWriterResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final UserService userService;

    @Transactional
    public Optional<Comment> findById(Long commentId){
        return commentRepository.findById(commentId);
    }

    @Transactional
    public CommentResponseDto create(String email, Long postId, CommentRequestDto commentRequestDto){

        Post post = postService.findPostByPostId(postId);

        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.save(Comment.builder()
                                                        .text(commentRequestDto.getText())
                                                        .status(Status.ALIVE)
                                                        .post(post)
                                                        .user(user)
                                                        .build());

        return this.getCommentResponseDto(comment.getCommentId());
    }

    @Transactional
    public List<CommentResponseDto> findAllByPostId(Long postId){
        Post post = postService.findPostByPostId(postId);
        return commentRepository.findByPost(post)
                                .stream().map(comment -> this.getCommentResponseDto(comment.getCommentId()))
                                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto getCommentResponseDto(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            CommentNotFoundException::new);
        UserAsWriterResponseDto user = userService.getUserAsWriterResponseDto(comment.getUser().getUserId());
        return new CommentResponseDto(comment, user);
    }

    @Transactional
    public CommentResponseDto update(String email, Long commentId, CommentRequestDto commentRequestDto){
        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().equals(user)){
            throw new CommentNotPermittedException();
        }

        comment.update(commentRequestDto);
        commentRepository.save(comment);
        return this.getCommentResponseDto(commentId);
    }

    @Transactional
    public void delete(String email, Long commentId){
        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().equals(user)){
            throw new CommentNotPermittedException();
        }

        comment.setStatus(Status.DELETED);
        commentRepository.save(comment);
    }
}
