package org.almondiz.almondiz.follow;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.FollowExistedException;
import org.almondiz.almondiz.exception.exception.FollowNotFoundException;
import org.almondiz.almondiz.exception.exception.FollowNotPermittedException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
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
    public Follow create(String uid, FollowRequestDto followRequestDto) {
        User follower = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        User followee = userService.findById(followRequestDto.getFolloweeId()).orElseThrow(UserNotFoundException::new);

        if (followRepository.findByFollowerAndFollowee(follower, followee).isPresent()) {
            throw new FollowExistedException();
        }

        return followRepository.save(Follow.builder()
                                           .follower(follower)
                                           .followee(followee)
                                           .alias(followRequestDto.getAlias())
                                           .build());
    }

    @Transactional
    public void delete(String uid, Long followId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
        Follow follow = followRepository.findById(followId).orElseThrow(FollowNotFoundException::new);
        if (!follow.getFollower().equals(user)) {
            throw new FollowNotPermittedException();
        }
        followRepository.deleteById(followId);
    }

    @Transactional
    public List<FollowerResponseDto> findAllFollowers(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
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
    public List<FollowingResponseDto> findAllFollowings(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
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
    public void setAlias(String uid, Long followId, String alias) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);
        Follow follow = followRepository.findById(followId).orElseThrow(FollowNotFoundException::new);
        if (!follow.getFollower().equals(user)) {
            throw new FollowNotPermittedException();
        }
        follow.updateAlias(alias);
        followRepository.save(follow);
    }
}
