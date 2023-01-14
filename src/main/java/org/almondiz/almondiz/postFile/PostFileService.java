package org.almondiz.almondiz.postFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.almondiz.almondiz.common.S3Upload;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.FileInvalidException;
import org.almondiz.almondiz.exception.exception.PostFileNotFoundException;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.postFile.entity.PostFile;
import org.almondiz.almondiz.postFile.entity.PostFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;

    private final S3Upload s3Upload;

    @Transactional
    public List<String> getFileUrlsByPost(Post post) {
        List<PostFile> postFiles = postFileRepository.findByPost(post);

        return postFiles.stream()
                        .map(postFile -> postFile.getFileUrl())
                        .collect(Collectors.toList());
    }

    @Transactional
    public void create(List<MultipartFile> files, Post post) {

        List<String> fileUrls = new ArrayList<>();

        files.forEach(file -> {
            if(file.isEmpty()) {
                throw new FileInvalidException();
            }
            try {
                String fileUrl = s3Upload.uploadFiles(file, "/postFile");
                fileUrls.add(fileUrl);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileInvalidException();
            }
        });

        fileUrls.forEach(fileUrl -> {
            PostFile postFile = PostFile.builder()
                                        .fileUrl(fileUrl)
                                        .post(post)
                                        // .status(Status.ALIVE)
                                        .build();

            postFileRepository.save(postFile);
        });
    }

}
