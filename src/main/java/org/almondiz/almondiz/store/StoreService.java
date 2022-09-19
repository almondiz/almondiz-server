package org.almondiz.almondiz.store;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.StoreExistedException;
import org.almondiz.almondiz.exception.exception.StoreNotFoundException;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.store.entity.StoreRepository;
import org.almondiz.almondiz.store.entity.StoreRequestDto;
import org.almondiz.almondiz.store.entity.StoreResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store getStoreById(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        return store;
    }

    public StoreResponseDto getStoreDto(Store store) {
        return new StoreResponseDto(store);
    }

    @Transactional
    public List<StoreResponseDto> getAllStores() {
        return storeRepository.findAll()
                              .stream()
                              .map(this::getStoreDto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public List<StoreResponseDto> getAllStoreByStoreName(String storeName) {
        return storeRepository.findAllByStoreName(storeName)
                              .stream()
                              .map(this::getStoreDto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public StoreResponseDto create(StoreRequestDto requestDto) {
        if (storeRepository.findByStoreNameAndLatiAndLongi(requestDto.getStoreName(), requestDto.getLati(), requestDto.getLongi()).isPresent()) {
            throw new StoreExistedException();
        }

        Store store = storeRepository.save(Store.builder()
                                                .storeName(requestDto.getStoreName())
                                                .category(requestDto.getCategory())
                                                .lati(requestDto.getLati())
                                                .longi(requestDto.getLongi())
                                                .address(requestDto.getAddress())
                                                .contact(requestDto.getContact())
                                                .build());

        return getStoreDto(store);
    }

    @Transactional
    public void deleteByStoreId(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        storeRepository.delete(store);
    }
}
