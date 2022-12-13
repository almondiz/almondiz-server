package org.almondiz.almondiz.postscrap;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.*;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostScrapService {

    private final PostScrapRepository postScrapRepository;

    private final UserService userService;

    private final PostRepository postRepository;

    @Transactional
    public PostScrap findPostScrapById(Long id) {
        return postScrapRepository.findById(id).orElseThrow(PostScrapNotFoundException::new);
    }

    public PostScrapResponseDto getPostScarpResponseDto(PostScrap postScrap) {
        return new PostScrapResponseDto(postScrap);
    }

    @Transactional
    public Post findPostByPostId(Long postId) {
        return postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostScrapResponseDto create(String uid, Long postId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = this.findPostByPostId(postId);

        Optional<PostScrap> postScrap = postScrapRepository.findByUserAndPost(user, post);

        if (postScrap.isPresent()) {
            throw new PostScrapExistedException();
        } else {
            postScrap = Optional.of(postScrapRepository.save(PostScrap.builder()
                                                                      .user(user)
                                                                      .post(post)
                                                                      // .status(Status.ALIVE)
                                                                      .build()));
        }

        return getPostScarpResponseDto(postScrap.get());
    }

    @Transactional
    public List<PostScrapResponseDto> findAllPostScrapByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return postScrapRepository.findAllByUser(user)
                                  .stream()
                                  .map(this::getPostScarpResponseDto)
                                  .collect(Collectors.toList());
    }

    @Transactional
    public long findPostScrapCountByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return postScrapRepository.countByUser(user);
    }

    @Transactional
    public long findPostScrapCountByPost(Long postId) {
        Post post = this.findPostByPostId(postId);

        return postScrapRepository.countByPost(post);
    }

    @Transactional
    public boolean isScrap(String uid, Long postId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = this.findPostByPostId(postId);

        Optional<PostScrap> postScrap = postScrapRepository.findByUserAndPost(user, post);

        return postScrap.isPresent();
    }

    @Transactional
    public void deletePostScrapById(String uid, Long scrapId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        PostScrap postScrap = this.findPostScrapById(scrapId);

        if (!postScrap.getUser().equals(user)) {
            throw new PostScrapNotPermittedException();
        }

        postScrapRepository.delete(postScrap);
    }

    @Transactional
    public void deletePostScrapByUserAndPost(String uid, Long postId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = this.findPostByPostId(postId);

        PostScrap postScrap = postScrapRepository.findByUserAndPost(user, post).get();

        postScrapRepository.delete(postScrap);
    }
}
