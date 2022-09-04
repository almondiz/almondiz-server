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
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.user.dto.UserRequestDto;
import org.almondiz.almondiz.user.dto.UserResponseDto;
import org.almondiz.almondiz.user.entity.User;
import org.almondiz.almondiz.user.entity.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
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
          .map(user -> getUser(user.getUserId()))
          .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto getUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        String profileImgUrl = profileFileService.getFileUrlById(user.getProfileId());
        String nutName = nutService.getNutNameById(user.getNutId());
        String tagName = tagService.getTagNameById(user.getTagId());
        String nickName = tagName + " " + nutName;
        return new UserResponseDto(user, profileImgUrl, nickName);
    }

    @Transactional
    public UserResponseDto getUserByEmail(String email){
        User user = findByEmail(email).orElseThrow(CUserNotFoundException::new);
        String profileImgUrl = profileFileService.getFileUrlById(user.getProfileId());
        String nutName = nutService.getNutNameById(user.getNutId());
        String tagName = tagService.getTagNameById(user.getTagId());
        String nickName = tagName + " " + nutName;
        return new UserResponseDto(user, profileImgUrl, nickName);
    }

    @Transactional
    public UserResponseDto modifyUser(String email, UserRequestDto userRequestDto){
        User user = findByEmail(email).orElseThrow(CUserNotFoundException::new);
        user.update(userRequestDto.getProfileId(), userRequestDto.getTagId(), userRequestDto.getNutId());
        userRepository.save(user);
        return getUser(user.getUserId());
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
        String nutName = nutService.getNutNameById(user.getNutId());
        String tagName = tagService.getTagNameById(user.getTagId());
        return tagName + " " + nutName;
    }
}
