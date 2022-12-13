package org.almondiz.almondiz.user;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.nut.NutService;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;
import org.almondiz.almondiz.user.dto.UserRequestDto;
import org.almondiz.almondiz.user.dto.UserResponseDto;
import org.almondiz.almondiz.user.entity.Thumb;
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
    public Optional<User> findByUid(String uid) {return userRepository.findByUid(uid);}

    @Transactional
    public Optional<User> findByUserId(Long userId) {return userRepository.findById(userId);}

    @Transactional
    public List<UserResponseDto> getAllUsers(){
      return userRepository.findAll()
          .stream()
          .map(user -> this.getUser(user.getUserId()))
          .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto getUser(Long userId){
        User user = findByUserId(userId).orElseThrow(UserNotFoundException::new);
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        Thumb thumb = new Thumb(user.getEmoji(), user.getColor());
        return new UserResponseDto(user, thumb, nickName);
    }

    @Transactional
    public UserSimpleResponseDto getUserAsWriterResponseDto(Long userId){
        return getUserSimpleResponseDto(userId);
    }

    @Transactional
    public UserSimpleResponseDto getUserSimpleDtoByUid(String uid) {
        User user = findByUid(uid).orElseThrow(UserNotFoundException::new);

        return getUserSimpleResponseDto(user.getUserId());
    }

    @Transactional
    public UserSimpleResponseDto getUserSimpleResponseDto(Long userId) {
        User user = findByUserId(userId).orElseThrow(UserNotFoundException::new);
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        Thumb thumb = new Thumb(user.getEmoji(), user.getColor());
        return new UserSimpleResponseDto(user, thumb, nickName);
    }

    @Transactional
    public UserResponseDto getUserByUid(String uid){
        User user = findByUid(uid).orElseThrow(UserNotFoundException::new);
        String nutName = user.getNut().getNutName();
        String tagName = user.getTag().getTagName();
        String nickName = tagName + " " + nutName;
        Thumb thumb = new Thumb(user.getEmoji(), user.getColor());
        return new UserResponseDto(user, thumb, nickName);
    }
	
	@Transactional
    public UserResponseDto modifyUser(String uid, UserRequestDto userRequestDto){
        User user = userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
        ProfileFile profileFile = profileFileService.getProfileFileById(userRequestDto.getProfileId());
        Tag tag = tagService.getTagById(userRequestDto.getTagId());
        Nut nut = nutService.getNutById(userRequestDto.getNutId());
        user.update(profileFile, tag, nut, userRequestDto.getThumb());
        userRepository.save(user);
        return this.getUser(user.getUserId());
    }

    @Transactional
    public void deleteUserByUid(String uid){
        User user = findByUid(uid).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
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
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return this.getNickName(user);
    }
}
