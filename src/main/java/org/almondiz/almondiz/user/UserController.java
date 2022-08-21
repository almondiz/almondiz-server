package org.almondiz.almondiz.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api")
public class UserController {
    private final UserRepository userRepository;

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

}

// INSERT INTO USER_TABLE VALUES (1, NOW(), NOW(), NOW(), 'aaa@aaa.com', 1, 1, 'GOOGLE', 'USER', 'ALIVE', 1, 1)