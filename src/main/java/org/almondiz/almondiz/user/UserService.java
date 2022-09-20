package org.almondiz.almondiz.user;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.nut.NutService;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.user.dto.UserAsWriterResponseDto;
import org.almondiz.almondiz.user.dto.UserRequestDto;
import org.almondiz.almondiz.user.dto.UserResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.almondiz.almondiz.user.entity.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileFileService profileFileService;
    private final UserRepository userRepository;
    private final NutService nutService;
    private final TagService tagService;

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public List<UserResponseDto> getAllUsers(){
      return userRepository.findAll()
          .stream()
          .map(user -> this.getUser(user.getUserId()))
          .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto getUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        String profileImgUrl = user.getProfileFile().getFileUrl();
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        return new UserResponseDto(user, profileImgUrl, nickName);
    }

    @Transactional
    public UserAsWriterResponseDto getUserAsWriterResponseDto(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        String profileImgUrl = user.getProfileFile().getFileUrl();
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        return new UserAsWriterResponseDto(user, profileImgUrl, nickName);
    }

    @Transactional
    public UserResponseDto getUserByEmail(String email){
        User user = findByEmail(email).orElseThrow(CUserNotFoundException::new);
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        return new UserResponseDto(user, user.getProfileFile().getFileUrl(), nickName);
    }
	
	@Transactional
    public UserResponseDto modifyUser(String email, UserRequestDto userRequestDto){
        User user = userRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        ProfileFile profileFile = profileFileService.getProfileFileById(userRequestDto.getProfileId());
        Tag tag = tagService.getTagById(userRequestDto.getTagId());
        Nut nut = nutService.getNutById(userRequestDto.getNutId());
        user.update(profileFile, tag, nut);
        userRepository.save(user);
        return this.getUser(user.getUserId());
    }

    @Transactional
    public UserResponseDto deleteUserByEmail(String email){
        User user = findByEmail(email).orElseThrow(CUserNotFoundException::new);
        user.setStatus(Status.DELETED);
        userRepository.save(user);
        return getUser(user.getUserId());
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public String getNickName(User user) {
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        return tagName + " " + nutName;
    }

    @Transactional
    public String getNickName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return this.getNickName(user);
    }
}
