package org.almondiz.almondiz.store.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreId(Long storeId);

    Optional<Store> findByStoreNameAndLatiAndLongi(String storeName, double lati, double longi);

    List<Store> findAllByStoreName(String storeName);
}
