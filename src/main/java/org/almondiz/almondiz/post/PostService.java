package org.almondiz.almondiz.post;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.comment.entity.Comment;
import org.almondiz.almondiz.comment.entity.CommentRepository;
import org.almondiz.almondiz.commentlike.CommentLike;
import org.almondiz.almondiz.commentlike.CommentLikeRepository;
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
import org.almondiz.almondiz.postscrap.PostScrapService;
import org.almondiz.almondiz.reply.ReplyResponseDto;
import org.almondiz.almondiz.reply.ReplyService;
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

    private final PostScrapService postScrapService;

    private final CommentLikeRepository commentLikeRepository;

    private final ReplyService replyService;

    @Transactional
    public PostSimpleResponseDto createPost(String uid, PostRequestDto postRequestDto) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
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

        postRequestDto.getTags().forEach(tagId -> this.createTagPost(tagId, newPost));

        postRequestDto.getImages().forEach(url -> this.createPostFile(url, post));

        return getPostSimpleDtoById(newPost.getPostId());
    }

    private void createTagPost(Long tagId, Post post) {
        Tag tag = tagService.getTagById(tagId);

        tagPostService.create(post, tag);
    }

    private void createPostFile(String imageUrl, Post post) {
        postFileService.create(imageUrl, "url", post);
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
        Post post = findPostByPostId(postId);

        UserSimpleResponseDto userSimpleResponseDto = userService.getUserSimpleDtoByUid(post.getUser().getUid());

        ShopSimpleDto shopSimpleDto = new ShopSimpleDto(post.getShop());

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        Long scrappedCount = postScrapService.findPostScrapCountByPost(postId);

        boolean scrap = postScrapService.isScrap(uid, postId);

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
    public CommentResponseDto getCommentResponseDto(Long commentId, String uid) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            CommentNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndUser(comment, user);

        UserSimpleResponseDto writer = userService.getUserAsWriterResponseDto(comment.getUser().getUserId());

        List<ReplyResponseDto> reply = replyService.findAllReplyByComment(comment.getCommentId(), uid);

        return new CommentResponseDto(comment, writer, reply, commentLike.isPresent());
    }

    @Transactional
    public CommentResponseDto getBestCommentByPostId(Long postId, String uid) {
        Post post = this.findPostByPostId(postId);
        List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedAtDesc(post);

        if (commentList.size() == 0) {
            return null;
        }

        return this.getCommentResponseDto(commentList.get(0).getCommentId(), uid);
    }
}
