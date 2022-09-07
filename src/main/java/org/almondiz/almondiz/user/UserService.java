package org.almondiz.almondiz.user;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CAccountExistedException;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.nut.NutService;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.user.dto.UserRegisterDto;
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
    public User signup(UserRegisterDto userRegisterDto){
         Optional<User> exUser = userRepository.findByEmail(userRegisterDto.getEmail());

         if(exUser.isPresent()){
             throw new CAccountExistedException();
         }

         ProfileFile profileFile = profileFileService.getProfileFileById(userRegisterDto.getProfileId());
         Tag tag = tagService.getTagById(userRegisterDto.getTagId());
         Nut nut = nutService.getNutById(userRegisterDto.getNutId());
         User user = new User(userRegisterDto.getEmail(), profileFile, tag, nut);
         return userRepository.save(user);
    }

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
    public UserResponseDto modifyUser(Long userId, UserRequestDto userRequestDto){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        ProfileFile profileFile = profileFileService.getProfileFileById(userRequestDto.getProfileId());
        Tag tag = tagService.getTagById(userRequestDto.getTagId());
        Nut nut = nutService.getNutById(userRequestDto.getNutId());
        user.update(profileFile, tag, nut);
        userRepository.save(user);
        return this.getUser(userId);
    }

    @Transactional
    public UserResponseDto deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        user.setStatus(Status.DELETED);
        userRepository.save(user);
        return this.getUser(userId);
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
