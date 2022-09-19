package org.almondiz.almondiz.profileFile;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.ProfileFileNotFoundException;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.profileFile.entity.ProfileFileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileFileService {

    private final ProfileFileRepository profileFileRepository;

    @Transactional
    public String getFileUrlById(Long fileId){
        return this.getProfileFileById(fileId).getFileUrl();
    }

    @Transactional
    public ProfileFile getProfileFileById(Long fileId){
        ProfileFile profileFile = profileFileRepository.findById((fileId)).orElseThrow(
            ProfileFileNotFoundException::new);
        return profileFile;
    }

}
