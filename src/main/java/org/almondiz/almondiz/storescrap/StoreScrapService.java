package org.almondiz.almondiz.storescrap;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
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
    public StoreScrapResponseDto getStoreScrapByUserAndStore(String email, Long storeId) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        StoreScrap storeScrap = storeScrapRepository.findByUserAndStore(user, store).orElseThrow(StoreScrapNotFoundException::new);

        return new StoreScrapResponseDto(storeScrap);
    }

    @Transactional
    public boolean isScrapByUserAndStore(String email, Long storeId) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        Optional<StoreScrap> storeScrap = storeScrapRepository.findByUserAndStore(user, store);

        return storeScrap.isPresent();
    }

    @Transactional
    public List<StoreScrapResponseDto> getAllStoreScrapByUser(String email) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

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
    public StoreScrapResponseDto create(String email, Long storeId) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        if(storeScrapRepository.findByUserAndStore(user, store).isPresent()){
            throw new StoreScrapExistedException();
        }

        StoreScrap storeScrap = storeScrapRepository.save(StoreScrap.builder()
                                                                    .user(user)
                                                                    .store(store)
                                                                    .build());
        return getStoreScrapDto(storeScrap);
    }

    @Transactional
    public void deleteById(String email, Long scrapId) {
        StoreScrap storeScrap = storeScrapRepository.findById(scrapId).orElseThrow(StoreScrapNotFoundException::new);

        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        if(!storeScrap.getUser().equals(user)) {
            throw new StoreScrapNotPermittedException();
        }

        storeScrapRepository.delete(storeScrap);
    }

    @Transactional
    public void deleteByUserAndStore(String email, Long storeId) {
        User user = userService.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        Store store = storeService.getStoreById(storeId);

        StoreScrap storeScrap = storeScrapRepository.findByUserAndStore(user, store).orElseThrow(StoreScrapNotFoundException::new);

        storeScrapRepository.delete(storeScrap);
    }
}
