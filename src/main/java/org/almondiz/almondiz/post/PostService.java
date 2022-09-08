package org.almondiz.almondiz.post;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.exception.exception.PostNotFoundException;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.post.dto.PostResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.post.entity.PostRepository;
import org.almondiz.almondiz.postFile.PostFileService;
import org.almondiz.almondiz.store.StoreService;
import org.almondiz.almondiz.store.entity.Store;
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

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto){
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
    public List<PostResponseDto> getAllPosts(){
        return postRepository.findAll()
            .stream()
            .map(post -> getPostByPostId(post.getPostId()))
            .collect(Collectors.toList());
    }

    @Transactional
    public Post findPostByPostId(Long postId){
        return postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostResponseDto getPostByPostId(Long postId){
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        String nickName = userService.getNickName(post.getUser());
        Store store = post.getStore();
        List<String> postFileImgUrls = postFileService.getFileUrlsByPost(post);

        return new PostResponseDto(post, nickName, store, postFileImgUrls);
    }
    @Transactional
    public List<PostResponseDto> getPostsByUserId(Long userId){
        User user = userService.findById(userId).orElseThrow(CUserNotFoundException::new);

        return postRepository.findByUser(user)
            .stream()
            .map(post -> getPostByPostId(post.getPostId()))
            .collect(Collectors.toList());
    }
    @Transactional
    public List<PostResponseDto> getPostsByStoreId(Long storeId){
        Store store = storeService.getStoreById(storeId);

        return postRepository.findByStore(store)
            .stream()
            .map(post -> getPostByPostId(post.getPostId()))
            .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        post.update(postRequestDto);
        postRepository.save(post);

        return getPostByPostId(post.getPostId());
    }

    @Transactional
    public PostResponseDto deletePost(Long postId){
        Post post = postRepository.findByPostId(postId).orElseThrow(PostNotFoundException::new);
        post.setStatus(Status.DELETED);
        postRepository.save(post);

        return getPostByPostId(post.getPostId());
    }



}
