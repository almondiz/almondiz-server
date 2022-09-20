package org.almondiz.almondiz.storescrap;

import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreScrapRepository extends JpaRepository<StoreScrap, Long> {

    List<StoreScrap> findAllByUser(User user);

    List<StoreScrap> findAllByStore(Store store);

    Optional<StoreScrap> findByUserAndStore(User user, Store store);
}
