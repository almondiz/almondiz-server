package org.almondiz.almondiz.shopscrap;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.exception.exception.ShopScrapExistedException;
import org.almondiz.almondiz.exception.exception.ShopScrapNotFoundException;
import org.almondiz.almondiz.exception.exception.ShopScrapNotPermittedException;
import org.almondiz.almondiz.shop.ShopService;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopScrapService {

    private final ShopScrapRepository shopScrapRepository;

    private final UserService userService;

    private final ShopService shopService;

    @Transactional
    public ShopScrapResponseDto getShopScrapById(Long scrapId) {
        ShopScrap shopScrap = shopScrapRepository.findById(scrapId).orElseThrow(ShopScrapNotFoundException::new);
        return getShopScrapDto(shopScrap);
    }

    public ShopScrapResponseDto getShopScrapDto(ShopScrap shopScrap) {
        return new ShopScrapResponseDto(shopScrap);
    }

    @Transactional
    public ShopScrapResponseDto getShopScrapByUserAndShop(String uid, Long shopId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Shop shop = shopService.getShopById(shopId);

        ShopScrap shopScrap = shopScrapRepository.findByUserAndShop(user, shop).orElseThrow(ShopScrapNotFoundException::new);

        return new ShopScrapResponseDto(shopScrap);
    }

    @Transactional
    public boolean isScrapByUserAndShop(String uid, Long shopId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Shop shop = shopService.getShopById(shopId);

        Optional<ShopScrap> shopScrap = shopScrapRepository.findByUserAndShop(user, shop);

        return shopScrap.isPresent();
    }

    @Transactional
    public List<ShopScrapResponseDto> getAllShopScrapByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return shopScrapRepository.findAllByUser(user)
                                  .stream()
                                  .map(this::getShopScrapDto)
                                  .collect(Collectors.toList());
    }

    @Transactional
    public List<ShopScrapResponseDto> getAllShopScrapByShop(Long shopId) {
        Shop shop = shopService.getShopById(shopId);

        return shopScrapRepository.findAllByShop(shop)
                                  .stream()
                                  .map(this::getShopScrapDto)
                                  .collect(Collectors.toList());
    }

    @Transactional
    public ShopScrapResponseDto create(String uid, Long shopId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Shop shop = shopService.getShopById(shopId);

        Optional<ShopScrap> shopScrap = shopScrapRepository.findByUserAndShop(user, shop);

        if (shopScrap.isPresent()) {
            throw new ShopScrapExistedException();

        } else {
            shopScrap = Optional.of(shopScrapRepository.save(ShopScrap.builder()
                                                                      .user(user)
                                                                      .shop(shop)
                                                                      .build()));
        }

        return getShopScrapDto(shopScrapRepository.save(shopScrap.get()));
    }

    @Transactional
    public void deleteById(String uid, Long scrapId) {
        ShopScrap shopScrap = shopScrapRepository.findById(scrapId).orElseThrow(ShopScrapNotFoundException::new);

        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        if (!shopScrap.getUser().equals(user)) {
            throw new ShopScrapNotPermittedException();
        }

        shopScrapRepository.delete(shopScrap);
    }

    @Transactional
    public void deleteByUserAndShop(String uid, Long shopId) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Shop shop = shopService.getShopById(shopId);

        ShopScrap shopScrap = shopScrapRepository.findByUserAndShop(user, shop).orElseThrow(ShopScrapNotFoundException::new);

        shopScrapRepository.delete(shopScrap);
    }
}
