package org.almondiz.almondiz.follow;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.CFollowExistedException;
import org.almondiz.almondiz.exception.exception.CFollowNotFoundException;
import org.almondiz.almondiz.exception.exception.CFollowNotPermittedException;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.response.user.UserService;
import org.almondiz.almondiz.response.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final UserService userService;

    private final FollowRepository followRepository;

    private final ProfileFileService profileFileService;

    @Transactional
    public Follow create(String email, FollowRequestDto followRequestDto) {
        User follower = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        User followee = userService.findById(followRequestDto.getFolloweeId()).orElseThrow(CUserNotFoundException::new);

        if (followRepository.findByFollowerAndFollowee(follower, followee).isPresent()) {
            throw new CFollowExistedException();
        }

        return followRepository.save(Follow.builder()
                                           .follower(follower)
                                           .followee(followee)
                                           .alias(followRequestDto.getAlias())
                                           .build());
    }

    @Transactional
    public void delete(String email, Long followId) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        Follow follow = followRepository.findById(followId).orElseThrow(CFollowNotFoundException::new);
        if (!follow.getFollower().equals(user)) {
            throw new CFollowNotPermittedException();
        }
        followRepository.deleteById(followId);
    }

    @Transactional
    public List<FollowerResponseDto> findAllFollowers(String email) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        return followRepository.findAllByFollowee(user)
                               .stream()
                               .map(follow -> FollowerResponseDto.builder()
                                                                 .followId(follow.getId())
                                                                 .followerId(follow.getFollower().getUserId())
                                                                 .followerNickName(userService.getNickName(follow.getFollower()))
                                                                 .followerProfileImg(profileFileService.getFileUrlById(follow.getFollower().getProfileFile().getFileId()))
                                                                 .createdAt(follow.getCreatedAt())
                                                                 .build()
                               ).collect(Collectors.toList());
    }

    @Transactional
    public List<FollowingResponseDto> findAllFollowings(String email) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        return followRepository.findAllByFollower(user)
                               .stream()
                               .map(follow -> FollowingResponseDto.builder()
                                                                  .followId(follow.getId())
                                                                  .followingId(follow.getFollowee().getUserId())
                                                                  .followingNickName(userService.getNickName(follow.getFollowee()))
                                                                  .followingProfileImg(profileFileService.getFileUrlById(follow.getFollowee().getProfileFile().getFileId()))
                                                                  .alias(follow.getAlias())
                                                                  .createdAt(follow.getCreatedAt())
                                                                  .build())
                               .collect(Collectors.toList());
    }

    @Transactional
    public void setAlias(String email, Long followId, String alias) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        Follow follow = followRepository.findById(followId).orElseThrow(CFollowNotFoundException::new);
        if (!follow.getFollower().equals(user)) {
            throw new CFollowNotPermittedException();
        }
        follow.updateAlias(alias);
        followRepository.save(follow);
    }
}
