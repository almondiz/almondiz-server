package org.almondiz.almondiz.postFile;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.PostFileNotFoundException;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.postFile.entity.PostFile;
import org.almondiz.almondiz.postFile.entity.PostFileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;

    @Transactional
    public List<String> getFileUrlsByPost(Post post) {
        List<PostFile> postFiles = postFileRepository.findByPost(post);

        return postFiles.stream()
                        .map(postFile -> postFile.getFileUrl())
                        .collect(Collectors.toList());
    }

    @Transactional
    public void create(String url, String type, Post post) {
        PostFile postFile = PostFile.builder()
                                    .fileUrl(url)
                                    .type(type)
                                    .post(post)
                                    .status(Status.ALIVE)
                                    .build();

        postFileRepository.save(postFile);
    }

}
