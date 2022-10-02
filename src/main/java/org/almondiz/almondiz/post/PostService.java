package org.almondiz.almondiz.post;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotPermittedException;
import org.almondiz.almondiz.post.dto.PostInFeedResponseDto;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.postFile.PostFileService;
import org.almondiz.almondiz.store.StoreService;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.store.entity.StoreResponseDto;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.tagpost.TagPostService;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.dto.UserAsWriterResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final StoreService storeService;

    private final PostFileService postFileService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final CommentRepository commentRepository;

    @Transactional
    public PostResponseDto createPost(String email, PostRequestDto postRequestDto) {
        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Store store = storeService.getStoreById(postRequestDto.getStoreId());

        Post post = Post.builder()
                        .user(user)
                        .store(store)
                        .content(postRequestDto.getContent())
                        .status(Status.ALIVE)
                        .build();

        return getPostDtoById(postRepository.save(post).getPostId());
    }

    @Transactional
    public List<PostInFeedResponseDto> getAllPosts() {
        return postRepository.findAll()
                             .stream()
                             .map(post -> this.getPostInFeedResponseDtoByPostId(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public Post findPostByPostId(Long postId) {
        return postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostInFeedResponseDto getPostInFeedResponseDtoByPostId(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        UserAsWriterResponseDto user = userService.getUserAsWriterResponseDto(post.getUser().getUserId());

        StoreResponseDto store = storeService.getStoreDto(post.getStore());

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        CommentResponseDto bestComment = this.getBestCommentByPostId(postId);

        return new PostInFeedResponseDto(post, postFileImgUrls, user, store, tagList, bestComment, post.getCreatedAt(), post.getModifiedAt());
    }

    @Transactional
    public PostResponseDto getPostDtoById(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        UserAsWriterResponseDto user = userService.getUserAsWriterResponseDto(post.getUser().getUserId());

        StoreResponseDto store = storeService.getStoreDto(post.getStore());

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        List<CommentResponseDto> commentList = this.findCommentsByPostId(postId);

        return new PostResponseDto(post, postFileImgUrls, user, store, tagList, commentList, post.getCreatedAt(), post.getModifiedAt());
    }

    @Transactional
    public List<PostResponseDto> getPostsByUserId(Long userId) {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);

        return postRepository.findByUser(user)
                             .stream()
                             .map(post -> this.getPostDtoById(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponseDto> getPostsByStoreId(Long storeId) {
        Store store = storeService.getStoreById(storeId);

        return postRepository.findByStore(store)
                             .stream()
                             .map(post -> this.getPostDtoById(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto modifyPost(String uid, Long postId, PostRequestDto postRequestDto) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        if(!post.getUser().equals(user)){
            throw new PostNotPermittedException();
        }

        post.update(postRequestDto);
        postRepository.save(post);

        return this.getPostDtoById(post.getPostId());
    }

    @Transactional
    public PostResponseDto deletePost(String uid, Long postId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        if(!post.getUser().equals(user)){
            throw new PostNotPermittedException();
        }

        post.setStatus(Status.DELETED);
        postRepository.save(post);

        return this.getPostDtoById(post.getPostId());
    }

    @Transactional
    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
        Post post = this.findPostByPostId(postId);

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
    public CommentResponseDto getBestCommentByPostId(Long postId) {
        Post post = this.findPostByPostId(postId);
        List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedAtDesc(post);

        if (commentList.size() == 0) {
            return null;
        }

        return this.getCommentResponseDto(commentList.get(0).getCommentId());
    }
}
