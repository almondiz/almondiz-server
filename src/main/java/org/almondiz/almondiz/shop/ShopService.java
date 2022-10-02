package org.almondiz.almondiz.shop;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.ShopExistedException;
import org.almondiz.almondiz.exception.exception.ShopNotFoundException;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.shop.entity.ShopRepository;
import org.almondiz.almondiz.shop.entity.ShopRequestDto;
import org.almondiz.almondiz.shop.entity.ShopResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public Shop getShopById(Long shopId) {
        return shopRepository.findByShopId(shopId).orElseThrow(ShopNotFoundException::new);
    }

    public ShopResponseDto getShopDto(Shop shop) {
        return new ShopResponseDto(shop);
    }

    @Transactional
    public List<ShopResponseDto> getAllShops() {
        return shopRepository.findAll()
                              .stream()
                              .map(this::getShopDto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public List<ShopResponseDto> getAllShopByShopName(String shopName) {
        return shopRepository.findAllByShopName(shopName)
                              .stream()
                              .map(this::getShopDto)
                              .collect(Collectors.toList());
    }

    @Transactional
    public ShopResponseDto create(ShopRequestDto requestDto) {
        if (shopRepository.findByShopNameAndLatiAndLongi(requestDto.getShopName(), requestDto.getLati(), requestDto.getLongi()).isPresent()) {
            throw new ShopExistedException();
        }

        Shop shop = shopRepository.save(Shop.builder()
                                              .shopName(requestDto.getShopName())
                                              .category(requestDto.getCategory())
                                              .lati(requestDto.getLati())
                                              .longi(requestDto.getLongi())
                                              .address(requestDto.getAddress())
                                              .contact(requestDto.getContact())
                                              .build());

        return getShopDto(shop);
    }

    @Transactional
    public void deleteByShopId(Long shopId) {
        Shop store = shopRepository.findByShopId(shopId).orElseThrow(ShopNotFoundException::new);
        shopRepository.delete(store);
    }
}
