package org.almondiz.almondiz.post;

import java.util.ArrayList;
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
import org.almondiz.almondiz.common.Relation;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.CommentNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotPermittedException;
import org.almondiz.almondiz.follow.Follow;
import org.almondiz.almondiz.follow.FollowService;
import org.almondiz.almondiz.post.dto.PostSimpleResponseDto;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.postFile.PostFileService;
import org.almondiz.almondiz.postscrap.PostScrapService;
import org.almondiz.almondiz.reply.entity.ReplyRepository;
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
import org.springframework.web.multipart.MultipartFile;

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

    private final FollowService followService;

    private final ReplyRepository replyRepository;

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
                        // .status(Status.ALIVE)
                        .build();

        Post newPost = postRepository.save(post);

        postRequestDto.getTags().forEach(tagId -> this.createTagPost(tagId, newPost));

        createPostFile(postRequestDto.getImages(), post);

        return getPostSimpleDtoById(newPost.getPostId());
    }

    private void createTagPost(Long tagId, Post post) {
        Tag tag = tagService.getTagById(tagId);

        tagPostService.create(post, tag);
    }

    private void createPostFile(List<MultipartFile> files, Post post) {
        postFileService.create(files, post);
    }

    @Transactional
    public List<PostResponseDto> getAllPosts(String uid) {
        return postRepository.findAll()
                             .stream()
                             .map(post -> this.getPostResponseDto(uid, post.getPostId()))
                             .collect(Collectors.toList());
    }

    // 5km 이하 post 목록 조회
    @Transactional
    public List<PostResponseDto> getNearPosts(String uid, Double userLat, Double userLongi) {
        List<PostResponseDto> posts = new ArrayList<>();

        postRepository.findAll()
                      .stream()
                      .filter(post -> (this.getDistance(userLat, userLongi, post.getLati(), post.getLongi()) <= 5))
                      .forEach(post -> {
                          posts.add(this.getPostResponseDto(uid, post.getPostId()));
                      });

        return posts;
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

        User writer = post.getUser();

        UserSimpleResponseDto writerSimpleResponseDto = userService.getUserSimpleDtoByUid(writer.getUid());

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Relation relation = Relation.OTHER;

        if (writer.equals(user)) {
            relation = Relation.ME;
        }

        boolean isFollow = followService.isFollow(user, writer);

        if (isFollow) {
            relation = Relation.FOLLOWEE;
        }

        ShopSimpleDto shopSimpleDto = new ShopSimpleDto(post.getShop());

        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        List<TagResponseDto> tagList = tagPostService.getTagsByPost(post);

        Long scrappedCount = postScrapService.findPostScrapCountByPost(postId);

        boolean scrap = postScrapService.isScrap(uid, postId);

        Long commentCount = commentRepository.countByPost(post) + replyRepository.countByPost(post);

        return new PostResponseDto(post, postFileImgUrls, writerSimpleResponseDto, relation, shopSimpleDto, tagList, scrappedCount, scrap, commentCount);
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

        postRepository.delete(post);
    }

    // km 가준
    private Double getDistance(Double lat, Double lnt, Double lat2, Double lnt2) {
        double theta = lnt - lnt2;
        double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        return dist / 1000;
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    // @Transactional
    // public CommentResponseDto getCommentResponseDto(Long commentId, String uid) {
    //     Comment comment = commentRepository.findById(commentId).orElseThrow(
    //         CommentNotFoundException::new);
    //
    //     User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
    //
    //     Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndUser(comment, user);
    //
    //     UserSimpleResponseDto writer = userService.getUserAsWriterResponseDto(comment.getUser().getUserId());
    //
    //     Long likedCount = commentLikeRepository.countByComment(comment);
    //
    //     return new CommentResponseDto(comment, writer, likedCount, commentLike.isPresent());
    // }

    // @Transactional
    // public CommentResponseDto getBestCommentByPostId(Long postId, String uid) {
    //     Post post = this.findPostByPostId(postId);
    //     List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    //
    //     if (commentList.size() == 0) {
    //         return null;
    //     }
    //
    //     return this.getCommentResponseDto(commentList.get(0).getCommentId(), uid);
    // }
}
