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
import org.almondiz.almondiz.post.dto.PostSimpleResponseDto;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.postFile.PostFileService;
import org.almondiz.almondiz.shop.ShopService;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.shop.entity.ShopSimpleDto;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.tagpost.TagPostService;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final ShopService shopService;

    private final PostFileService postFileService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final CommentRepository commentRepository;

    @Transactional
    public PostSimpleResponseDto createPost(String email, PostRequestDto postRequestDto) {
        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Shop shop = shopService.getShopById(postRequestDto.getShopId());

        Post post = Post.builder()
                        .user(user)
                        .shop(shop)
                        .content(postRequestDto.getContent())
                        .lati(postRequestDto.getLati())
                        .longi(postRequestDto.getLongi())
                        .status(Status.ALIVE)
                        .build();

        Post newPost = postRepository.save(post);

        // 태그 등록
        // postRequestDto.getTags().stream()
        //               .map(tagId -> this.createTagPost(tagId, newPost));

        // 이미지 등록

        return getPostSimpleDtoById(newPost.getPostId());
    }

    // 태그 등록
    private void createTagPost(Long tagId, Post post) {
        Tag tag = tagService.getTagById(tagId);

        tagPostService.create(post, tag);
        //
        // return TagResponseDto.builder()
        //                      .tagId(tag.getTagId())
        //                      .tagName(tag.getTagName())
        //                      .build();
    }

    @Transactional
    public List<PostResponseDto> getAllPosts(String uid) {
        return postRepository.findAll()
                             .stream()
                             .map(post -> this.getPostResponseDto(uid, post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public Post findPostByPostId(Long postId) {
        return postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostSimpleResponseDto getPostInFeedResponseDtoByPostId(Long postId) {
        return getPostSimpleDtoById(postId);
    }

    @Transactional
    public PostSimpleResponseDto getPostSimpleDtoById(Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        return new PostSimpleResponseDto(post, postFileImgUrls, tagList);
    }

    @Transactional
    public PostResponseDto getPostResponseDto(String uid, Long postId) {
        UserSimpleResponseDto userSimpleResponseDto = userService.getUserSimpleDtoByUid(uid);

        Post post = findPostByPostId(postId);

        ShopSimpleDto shopSimpleDto = new ShopSimpleDto(post.getShop());

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        // scrap 구현전
        Long scrappedCount = 0L;

        boolean scrap = true;

        Long commentCount = commentRepository.countByPost(post);

        return new PostResponseDto(post, postFileImgUrls, userSimpleResponseDto, shopSimpleDto, tagList, scrappedCount, scrap, commentCount);
    }

    @Transactional
    public List<PostResponseDto> getPostsByUserId(String uid, Long userId) {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);

        return postRepository.findByUser(user)
                             .stream()
                             .map(post -> this.getPostResponseDto(uid, post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponseDto> getPostsByShopId(String uid, Long shopId) {
        Shop shop = shopService.getShopById(shopId);

        return postRepository.findByShop(shop)
                             .stream()
                             .map(post -> this.getPostResponseDto(uid, post.getPostId()))
                             .collect(Collectors.toList());
    }

    @Transactional
    public PostSimpleResponseDto modifyPost(String uid, Long postId, PostRequestDto postRequestDto) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        if (!post.getUser().equals(user)) {
            throw new PostNotPermittedException();
        }

        post.update(postRequestDto);
        postRepository.save(post);

        return this.getPostSimpleDtoById(post.getPostId());
    }

    @Transactional
    public void deletePost(String uid, Long postId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);

        if (!post.getUser().equals(user)) {
            throw new PostNotPermittedException();
        }

        post.setStatus(Status.DELETED);
        postRepository.save(post);
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
        UserSimpleResponseDto user = userService.getUserAsWriterResponseDto(comment.getUser().getUserId());

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
