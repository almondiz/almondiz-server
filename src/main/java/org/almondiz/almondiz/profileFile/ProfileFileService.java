package org.almondiz.almondiz.profileFile;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.ProfileFileNotFoundException;
import org.almondiz.almondiz.profileFile.entity.ProfileFileRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class ProfileFileService {

    private final ProfileFileRepository profileFileRepository;

    @Transactional
    public String getFileUrlById(Long fileId){
        String fileUrl = profileFileRepository.findById(fileId).orElseThrow(
            ProfileFileNotFoundException::new).getFileUrl();
        return fileUrl;
    }

}
