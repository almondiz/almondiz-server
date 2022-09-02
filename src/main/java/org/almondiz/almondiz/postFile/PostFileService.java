package org.almondiz.almondiz.postFile;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.PostFileNotFoundException;
import org.almondiz.almondiz.postFile.entity.PostFileRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class PostFileService {

    private final PostFileRepository postFileRepository;

    @Transactional
    public String getFileUrlById(Long fileId){
        String fileUrl = postFileRepository.findById(fileId).orElseThrow(
            PostFileNotFoundException::new).getFileUrl();
        return fileUrl;
    }

}
