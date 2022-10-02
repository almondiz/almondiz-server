package org.almondiz.almondiz.shop.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByShopId(Long shopId);

    Optional<Shop> findByShopNameAndLatiAndLongi(String shopName, double lati, double longi);

    List<Shop> findAllByShopName(String shopName);
}
