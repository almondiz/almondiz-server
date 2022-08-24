package org.almondiz.almondiz.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.response.CommonResult;
import org.almondiz.almondiz.response.ResponseService;
import org.almondiz.almondiz.response.SingleResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
public class UserController {
    private final UserRepository userRepository;
    private final ResponseService responseService;

    // 모든 회원 조회
    @GetMapping(value="/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // 특정 회원 정보 조회
    @GetMapping(value="/user/{userId}")
    public Optional<User> findUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    // 회원 탈퇴
    @DeleteMapping(value="/user/{userId}")
    public Optional<User> deleteUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(selectUser -> {
            selectUser.setStatus(Status.DELETED);
            userRepository.save(selectUser);
        });
        return user;
    }

    // 회원 가입
    @PostMapping(value="/user")
    public SingleResult<User> createUser(@RequestBody User user){
        User createdUser = userRepository.save(user);
        return responseService.getSingleResult(createdUser);
    }

    // 회원 정보 수정
    @PatchMapping(value="/user/{userId}")
    public Optional<User> updateUser(@PathVariable Long userId, @RequestBody User newUser) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(selectUser -> {
            selectUser.setEmail(newUser.getEmail());
            selectUser.setProfileId(newUser.getProfileId());
            selectUser.setTagId(newUser.getTagId());
            selectUser.setNutId(newUser.getNutId());
            userRepository.save(selectUser);
        });
        return user;
    }

}

// INSERT INTO USER_TABLE VALUES (1, NOW(), NOW(), NOW(), 'aaa@aaa.com', 1, 1, 'GOOGLE', 'USER', 'ALIVE', 1, 1)