package org.almondiz.almondiz.storescrap;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.StoreScrapExistedException;
import org.almondiz.almondiz.exception.exception.StoreScrapNotFoundException;
import org.almondiz.almondiz.exception.exception.StoreScrapNotPermittedException;
import org.almondiz.almondiz.store.StoreService;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreScrapService {

    private final StoreScrapRepository storeScrapRepository;

    private final UserService userService;

    private final StoreService storeService;

    @Transactional
    public StoreScrapResponseDto getStoreScrapById(Long scrapId) {
        StoreScrap storeScrap = storeScrapRepository.findById(scrapId).orElseThrow(StoreScrapNotFoundException::new);
        return getStoreScrapDto(storeScrap);
    }

    public StoreScrapResponseDto getStoreScrapDto(StoreScrap storeScrap) {
        return new StoreScrapResponseDto(storeScrap);
    }

    @Transactional
    public StoreScrapResponseDto getStoreScrapByUserAndStore(String uid, Long storeId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        StoreScrap storeScrap = storeScrapRepository.findByUserAndStore(user, store).orElseThrow(StoreScrapNotFoundException::new);

        return new StoreScrapResponseDto(storeScrap);
    }

    @Transactional
    public boolean isScrapByUserAndStore(String uid, Long storeId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        Optional<StoreScrap> storeScrap = storeScrapRepository.findByUserAndStore(user, store);

        if (storeScrap.isPresent() && !storeScrap.get().getStatus().equals(Status.DELETED)) {
            return true;
        }
        return false;
    }

    @Transactional
    public List<StoreScrapResponseDto> getAllStoreScrapByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return storeScrapRepository.findAllByUser(user)
                                   .stream()
                                   .map(this::getStoreScrapDto)
                                   .collect(Collectors.toList());
    }

    @Transactional
    public List<StoreScrapResponseDto> getAllStoreScrapByStore(Long storeId) {
        Store store = storeService.getStoreById(storeId);

        return storeScrapRepository.findAllByStore(store)
                                   .stream()
                                   .map(this::getStoreScrapDto)
                                   .collect(Collectors.toList());
    }

    @Transactional
    public StoreScrapResponseDto create(String uid, Long storeId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        Optional<StoreScrap> storeScrap = storeScrapRepository.findByUserAndStore(user, store);

        if (storeScrap.isPresent()) {
            if (storeScrap.get().getStatus().equals(Status.DELETED)) {
                storeScrap.get().setStatus(Status.ALIVE);
            } else {
                throw new StoreScrapExistedException();
            }
        } else {
            storeScrap = Optional.of(storeScrapRepository.save(StoreScrap.builder()
                                                                         .user(user)
                                                                         .store(store)
                                                                         .status(Status.ALIVE)
                                                                         .build()));
        }

        return getStoreScrapDto(storeScrapRepository.save(storeScrap.get()));
    }

    @Transactional
    public void deleteById(String uid, Long scrapId) {
        StoreScrap storeScrap = storeScrapRepository.findById(scrapId).orElseThrow(StoreScrapNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        if (!storeScrap.getUser().equals(user)) {
            throw new StoreScrapNotPermittedException();
        }

        storeScrap.setStatus(Status.DELETED);
        storeScrapRepository.save(storeScrap);
    }

    @Transactional
    public void deleteByUserAndStore(String uid, Long storeId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        StoreScrap storeScrap = storeScrapRepository.findByUserAndStore(user, store).orElseThrow(StoreScrapNotFoundException::new);

        storeScrap.setStatus(Status.DELETED);
        storeScrapRepository.save(storeScrap);
    }
}
