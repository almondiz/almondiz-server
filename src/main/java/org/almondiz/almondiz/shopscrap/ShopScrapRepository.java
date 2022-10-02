package org.almondiz.almondiz.shopscrap;

import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopScrapRepository extends JpaRepository<ShopScrap, Long> {

    List<ShopScrap> findAllByUser(User user);

    List<ShopScrap> findAllByShop(Shop shop);

    Optional<ShopScrap> findByUserAndShop(User user, Shop shop);
}
