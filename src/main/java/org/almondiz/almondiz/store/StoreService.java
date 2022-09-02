package org.almondiz.almondiz.store;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.StoreNotFoundException;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.store.entity.StoreRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store getStoreById(Long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        return store;
    }



}
