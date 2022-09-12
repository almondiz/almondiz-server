package org.almondiz.almondiz.post;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotFoundException;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.postFile.PostFileService;
import org.almondiz.almondiz.store.StoreService;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.tagpost.TagPostService;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@Setter
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
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        // user id 임시값 - userId 가져오는 과정 추가 필요
        Long userId = Long.valueOf(1);
        User user = userService.findById(userId).orElseThrow(CUserNotFoundException::new);
        Store store = storeService.getStoreById(postRequestDto.getStoreId());
        Post post = Post.builder()
                        .user(user)
                        .store(store)
                        .title(postRequestDto.getTitle())
                        .content(postRequestDto.getContent())
                        .status(Status.ALIVE)
                        .build();

        return getPostByPostId(postRepository.save(post).getPostId());
    }

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                             .stream()
                             .map(post -> this.getPostByPostId(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public Post findPostByPostId(Long postId) {
        return postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostResponseDto getPostByPostId(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        User user = post.getUser();
        String nickName = userService.getNickName(user);
        Store store = post.getStore();
        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);
        String userProfileImgUrl = user.getProfileFile().getFileUrl();
        List<CommentResponseDto> commentList = this.findCommentsByPostId(postId);
        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        return new PostResponseDto(post, nickName, store, postFileImgUrls, userProfileImgUrl, commentList, tagList);
    }

    @Transactional
    public List<PostResponseDto> getPostsByUserId(Long userId) {
        User user = userService.findById(userId).orElseThrow(CUserNotFoundException::new);

        return postRepository.findByUser(user)
                             .stream()
                             .map(post -> this.getPostByPostId(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponseDto> getPostsByStoreId(Long storeId) {
        Store store = storeService.getStoreById(storeId);

        return postRepository.findByStore(store)
                             .stream()
                             .map(post -> this.getPostByPostId(post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        post.update(postRequestDto);
        postRepository.save(post);

        return this.getPostByPostId(post.getPostId());
    }

    @Transactional
    public PostResponseDto deletePost(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        post.setStatus(Status.DELETED);
        postRepository.save(post);

        return this.getPostByPostId(post.getPostId());
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
        String nickName = userService.getNickName(comment.getUser());
        return new CommentResponseDto(comment, nickName);
    }



}
