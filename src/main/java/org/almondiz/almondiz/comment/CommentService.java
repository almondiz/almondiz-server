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
import org.almondiz.almondiz.commentlike.CommentLike;
import org.almondiz.almondiz.commentlike.CommentLikeRepository;
import org.almondiz.almondiz.common.Relation;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.*;
import org.almondiz.almondiz.follow.FollowService;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.reply.dto.ReplyRequestDto;
import org.almondiz.almondiz.reply.dto.ReplyResponseDto;
import org.almondiz.almondiz.reply.entity.Reply;
import org.almondiz.almondiz.reply.entity.ReplyRepository;
import org.almondiz.almondiz.reply.replylike.ReplyLike;
import org.almondiz.almondiz.reply.replylike.ReplyLikeRepository;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.dto.UserResponseDto;
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

    private final FollowService followService;

    private final ReplyRepository replyRepository;

    private final ReplyLikeRepository replyLikeRepository;

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
                                                        // .status(Status.ALIVE)
                                                        .post(post)
                                                        .user(user)
                                                        // .likedCount(0L)
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

        User writer = comment.getUser();

        UserSimpleResponseDto writerDto = userService.getUserAsWriterResponseDto(writer.getUserId());

        Relation relation = Relation.OTHER;

        if (writer.equals(user)) {
            relation = Relation.ME;
        }

        if (followService.isFollow(user, writer)) {
            relation = Relation.FOLLOWEE;
        }

        Long likedCount = commentLikeRepository.countByComment(comment);

        List<ReplyResponseDto> responses = findAllReplyByComment(comment, user);

        return new CommentResponseDto(comment, writerDto, relation, likedCount, commentLike.isPresent(), responses);
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


        commentRepository.delete(comment);
    }

    @Transactional
    public Long getCommentCountByPost(Post post) {
        return commentRepository.countByPost(post);
    }

    @Transactional
    public List<ReplyResponseDto> findAllReplyByComment(Comment comment, User user) {
        return replyRepository.findAllByComment(comment)
                              .stream().map(reply -> this.getReplyResponseDto(reply, user))
                              .collect(Collectors.toList());
    }


    @Transactional
    public ReplyResponseDto getReplyResponseDto(Reply reply, User user) {
        User writer = reply.getUser();

        UserSimpleResponseDto writerDto = userService.getUserAsWriterResponseDto(writer.getUserId());

        Relation relation = Relation.OTHER;

        if (writer.equals(user)) {
            relation = Relation.ME;
        }

        if (followService.isFollow(user, writer)) {
            relation = Relation.FOLLOWEE;
        }

        Long likedCount = replyLikeRepository.countByReply(reply);

        boolean like = this.isReplyLike(reply.getReplyId(), user.getUid());

        return new ReplyResponseDto(reply, writerDto, relation, likedCount, like);
    }

    @Transactional
    public Reply createReply(String uid, Long commentId, ReplyRequestDto requestDto) {

        Comment comment = this.findById(commentId);

        Post post = comment.getPost();

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Reply reply = replyRepository.save(Reply.builder()
                                                .text(requestDto.getText())
                                                .post(post)
                                                .comment(comment)
                                                .user(user)
                                                .build());

        return reply;
    }

    @Transactional
    public Reply updateReply(String uid, Long replyId, ReplyRequestDto requestDto) {

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Reply reply = replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);

        if (!reply.getUser().equals(user)) {
            throw new ReplyNotPermittedException();
        }

        reply.update(requestDto);

        return replyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(String uid, Long replyId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Reply reply = replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);

        if (!reply.getUser().equals(user)) {
            throw new ReplyNotPermittedException();
        }

        replyRepository.delete(reply);
    }

    @Transactional
    public ReplyLike findReplyLikeByReplyAndUser(Reply reply, User user) {
        return replyLikeRepository.findByReplyAndUser(reply, user).orElseThrow(ReplyLikeNotFoundException::new);
    }

    @Transactional
    public ReplyLike createReplyLike(Long replyId, String uid) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Optional<ReplyLike> replyLike = replyLikeRepository.findByReplyAndUser(reply, user);

        ReplyLike newLike;
        if (replyLike.isPresent()) {
            throw new ReplyLikeExistedException();
        } else {
            newLike = ReplyLike.builder()
                               .reply(reply)
                               .user(user)
                               .build();
        }

        return replyLikeRepository.save(newLike);
    }

    @Transactional
    public void deleteReplyLike(Long replyId, String uid) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        ReplyLike replyLike = this.findReplyLikeByReplyAndUser(reply, user);

        replyLikeRepository.delete(replyLike);
    }

    @Transactional
    public boolean isReplyLike(Long replyId, String uid) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return replyLikeRepository.findByReplyAndUser(reply, user).isPresent();
    }
}
