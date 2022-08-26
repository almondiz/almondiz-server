package org.almondiz.almondiz.user;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.CAccountExistedException;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
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

    private final UserRepository userRepository;

    public User signup(UserRegisterDto userRegisterDto){
         Optional<User> exUser = userRepository.findById(userRegisterDto.getUserId());
         if(exUser.isPresent()){
             throw new CAccountExistedException();
         }
         User user = new User(userRegisterDto.getUserId(), userRegisterDto.getProfileId(), userRegisterDto.getTagId(), userRegisterDto.getNutId());
         return userRepository.save(user);
    }

    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public UserResponseDto getUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    public UserResponseDto modifyUser(Long userId, UserRequestDto userRequestDto){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        user.update(userRequestDto.getProfileId(), userRequestDto.getTagId(), userRequestDto.getNutId());
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public UserResponseDto deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        user.setStatus(Status.DELETED);
        userRepository.save(user);
        return new UserResponseDto(user);
    }



}
